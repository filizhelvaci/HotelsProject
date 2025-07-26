package com.flz.service.impl;

import com.flz.exception.EmployeeAlreadyExistsException;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.*;
import com.flz.model.mapper.EmployeeCreateRequestToDomainMapper;
import com.flz.model.mapper.EmployeeExperienceToEmployeeOldExperienceMapper;
import com.flz.model.mapper.EmployeeToEmployeeOldMapper;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.EmployeeUpdateRequest;
import com.flz.port.*;
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
    private final EmployeeExperienceSavePort employeeExperienceSavePort;
    private final EmployeeExperienceReadPort employeeExperienceReadPort;
    private final EmployeeExperienceDeletePort employeeExperienceDeletePort;
    private final EmployeeOldSavePort employeeOldSavePort;
    private final EmployeeOldExperienceSavePort employeeOldExperienceSavePort;

    private final EmployeeCreateRequestToDomainMapper
            employeeCreateRequestToDomainMapper = EmployeeCreateRequestToDomainMapper.INSTANCE;
    private final EmployeeToEmployeeOldMapper
            employeeToEmployeeOldMapper = EmployeeToEmployeeOldMapper.INSTANCE;
    private final EmployeeExperienceToEmployeeOldExperienceMapper
            employeeExperienceToEmployeeOldExperienceMapper = EmployeeExperienceToEmployeeOldExperienceMapper.INSTANCE;

    @Override
    public void create(EmployeeCreateRequest createRequest) {

        boolean existsByIdentity = employeeReadPort
                .existsByIdentity(createRequest.getIdentityNumber());
        if (existsByIdentity) {
            throw new EmployeeAlreadyExistsException(createRequest.getIdentityNumber());
        }

        Employee employee = employeeCreateRequestToDomainMapper.map(createRequest);

        Employee savedEmployee = employeeSavePort.save(employee)
                .orElseThrow(() -> new RuntimeException("Employee could not be saved"));

        if (createRequest.getPositionId() != null && createRequest.getStartDate() != null) {
            Position position = positionReadPort.findById(createRequest.getPositionId())
                    .orElseThrow(() -> new PositionNotFoundException(createRequest.getPositionId()));

            Employee supervisor = employeeReadPort.findById(createRequest.getSupervisorId())
                    .orElseThrow(() -> new EmployeeNotFoundException(createRequest.getSupervisorId()));

            EmployeeExperience experience = EmployeeExperience.builder()
                    .salary(createRequest.getSalary())
                    .startDate(createRequest.getStartDate())
                    .position(position)
                    .supervisor(supervisor)
                    .employee(savedEmployee)
                    .build();
            employeeExperienceSavePort.save(experience);
        }

    }

    @Override
    public void update(Long id, EmployeeUpdateRequest employeeUpdateRequest) {

        Employee employee = employeeReadPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        String identityNumber = employeeUpdateRequest.getIdentityNumber();
        if (!employee.getIdentityNumber()
                .equals(identityNumber)) {
            checkIfExistsByIdentity(employeeUpdateRequest);
        }

        String phoneNumber = employeeUpdateRequest.getPhoneNumber();
        if (!(employee.getPhoneNumber()
                .equals(phoneNumber))) {
            checkIfExistsByPhoneNumber(employeeUpdateRequest);
        }

        employee.update(employeeUpdateRequest.getFirstName(),
                employeeUpdateRequest.getLastName(),
                employeeUpdateRequest.getIdentityNumber(),
                employeeUpdateRequest.getEmail(),
                employeeUpdateRequest.getPhoneNumber(),
                employeeUpdateRequest.getAddress(),
                employeeUpdateRequest.getBirthDate(),
                employeeUpdateRequest.getGender(),
                employeeUpdateRequest.getNationality());
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

        EmployeeOld employeeOld = employeeOldSavePort.save(employeeToEmployeeOldMapper.map(employee))
                .orElseThrow(() -> new RuntimeException("Employee kaydedilemedi"));

        List<EmployeeExperience> experiences = Optional.ofNullable(
                        employeeExperienceReadPort.findAllByEmployeeId(id)
                )
                .orElse(Collections.emptyList());

        if (!experiences.isEmpty()) {

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
        }

        employeeDeletePort.delete(id);

    }

}
