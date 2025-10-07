package com.flz.service.impl;

import com.flz.exception.DepartmentNotFoundException;
import com.flz.exception.EmployeeAlreadyExistsException;
import com.flz.exception.EmployeeAlreadyManagerException;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.EmployeeOld;
import com.flz.model.EmployeeOldExperience;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.mapper.EmployeeCreateRequestToDomainMapper;
import com.flz.model.mapper.EmployeeExperienceToEmployeeOldExperienceMapper;
import com.flz.model.mapper.EmployeeToEmployeeOldMapper;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.EmployeeUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.EmployeeDeletePort;
import com.flz.port.EmployeeExperienceDeletePort;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.port.EmployeeOldExperienceSavePort;
import com.flz.port.EmployeeOldSavePort;
import com.flz.port.EmployeeReadPort;
import com.flz.port.EmployeeSavePort;
import com.flz.port.PositionReadPort;
import com.flz.service.EmployeeWriteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class EmployeeWriteServiceImpl implements EmployeeWriteService {

    private final EmployeeReadPort employeeReadPort;
    private final EmployeeSavePort employeeSavePort;
    private final EmployeeDeletePort employeeDeletePort;
    private final PositionReadPort positionReadPort;
    private final DepartmentReadPort departmentReadPort;
    private final EmployeeExperienceSavePort employeeExperienceSavePort;
    private final EmployeeExperienceReadPort employeeExperienceReadPort;
    private final EmployeeExperienceDeletePort employeeExperienceDeletePort;
    private final EmployeeOldSavePort employeeOldSavePort;
    private final EmployeeOldExperienceSavePort employeeOldExperienceSavePort;

    private static final EmployeeCreateRequestToDomainMapper
            employeeCreateRequestToDomainMapper = EmployeeCreateRequestToDomainMapper.INSTANCE;
    private static final EmployeeToEmployeeOldMapper
            employeeToEmployeeOldMapper = EmployeeToEmployeeOldMapper.INSTANCE;
    private static final EmployeeExperienceToEmployeeOldExperienceMapper
            employeeExperienceToEmployeeOldExperienceMapper = EmployeeExperienceToEmployeeOldExperienceMapper.INSTANCE;


    @Override
    public void create(EmployeeCreateRequest createRequest) {

        boolean existsByIdentity = employeeReadPort
                .existsByIdentity(createRequest.getIdentityNumber());
        if (existsByIdentity) {
            throw new EmployeeAlreadyExistsException(createRequest.getIdentityNumber());
        }

        Employee employee = employeeCreateRequestToDomainMapper.map(createRequest);

        Employee savedEmployee = employeeSavePort.save(employee);

        if (createRequest.getPositionId() != null && createRequest.getStartDate() != null) {
            Position position = positionReadPort.findById(createRequest.getPositionId())
                    .orElseThrow(() -> new PositionNotFoundException(createRequest.getPositionId()));

            EmployeeExperience experience = EmployeeExperience.builder()
                    .salary(createRequest.getSalary())
                    .startDate(createRequest.getStartDate())
                    .position(position)
                    .employee(savedEmployee)
                    .build();
            employeeExperienceSavePort.save(experience);
        }

    }


    @Override
    public void update(Long id, EmployeeUpdateRequest request) {

        Employee employee = employeeReadPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        String identityNumber = request.getIdentityNumber();
        boolean identityNumberChanged = !employee.getIdentityNumber().equals(identityNumber);

        if (identityNumberChanged) {
            checkIfExistsByIdentity(request);
        }

        String phoneNumber = request.getPhoneNumber();
        boolean phoneNumberChanged = !employee.getPhoneNumber().equals(phoneNumber);

        if (phoneNumberChanged) {
            checkIfExistsByPhoneNumber(request);
        }

        employee.update(request.getFirstName(),
                request.getLastName(),
                request.getIdentityNumber(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getAddress(),
                request.getBirthDate(),
                request.getGender(),
                request.getNationality());
        employeeSavePort.save(employee);
    }


    private void checkIfExistsByIdentity(EmployeeUpdateRequest employeeUpdateRequest) {

        boolean existsByIdentity = employeeReadPort
                .existsByIdentity(employeeUpdateRequest.getIdentityNumber());
        if (existsByIdentity) {
            throw new EmployeeAlreadyExistsException(employeeUpdateRequest.getIdentityNumber());
        }
    }


    private void checkIfExistsByPhoneNumber(EmployeeUpdateRequest employeeUpdateRequest) {

        boolean existsByPhoneNumber = employeeReadPort
                .existsByPhoneNumber(employeeUpdateRequest.getPhoneNumber());
        if (existsByPhoneNumber) {
            throw new EmployeeAlreadyExistsException(employeeUpdateRequest.getPhoneNumber());
        }
    }


    @Override
    @Transactional
    public void delete(Long id) {

        Employee employee = employeeReadPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        Optional<Department> departmentOfManager = departmentReadPort.findByManagerIdAndStatus(id,DepartmentStatus.ACTIVE);

        if (departmentOfManager.isPresent()) {
            throw new EmployeeAlreadyManagerException(departmentOfManager.get().getName());
        }

        List<EmployeeExperience> experiences = Optional.ofNullable(
                        employeeExperienceReadPort.findAllByEmployeeId(id))
                .orElse(Collections.emptyList());

        EmployeeOld employeeOld = employeeOldSavePort.save(employeeToEmployeeOldMapper.map(employee));

        if (experiences.isEmpty()) {
            employeeDeletePort.delete(id);
            return;
        }

        for (EmployeeExperience experience : experiences) {
            if (experience.getEndDate() == null) {
                experience.setEndDate(LocalDate.now());
            }
        }

        List<EmployeeOldExperience> oldExperiences = employeeExperienceToEmployeeOldExperienceMapper
                .map(experiences);

        for (EmployeeOldExperience oldExperience : oldExperiences) {
            oldExperience.setEmployeeOld(employeeOld);
        }

        employeeOldExperienceSavePort.saveAll(oldExperiences);
        employeeExperienceDeletePort.deleteAllByEmployeeId(id);
        employeeDeletePort.delete(id);
    }

}
