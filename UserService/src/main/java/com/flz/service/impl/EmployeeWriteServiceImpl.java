package com.flz.service.impl;

import com.flz.exception.EmployeeAlreadyExistsException;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.mapper.EmployeeCreateRequestToDomainMapper;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.EmployeeUpdateRequest;
import com.flz.port.EmployeeDeletePort;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.port.EmployeeReadPort;
import com.flz.port.EmployeeSavePort;
import com.flz.port.PositionReadPort;
import com.flz.service.EmployeeWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EmployeeWriteServiceImpl implements EmployeeWriteService {

    private final EmployeeReadPort employeeReadPort;
    private final EmployeeSavePort employeeSavePort;
    private final EmployeeDeletePort employeeDeletePort;
    private final PositionReadPort positionReadPort;
    private final EmployeeExperienceSavePort employeeExperienceSavePort;
    private final EmployeeExperienceCreateServiceImpl employeeExperienceCreateServiceImpl;

    private final EmployeeCreateRequestToDomainMapper
            employeeCreateRequestToDomainMapper = EmployeeCreateRequestToDomainMapper.INSTANCE;

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
        String phoneNumber = employeeUpdateRequest.getPhoneNumber();

        if (!(employee.getIdentityNumber()
                .equals(identityNumber))
                || !(employee.getPhoneNumber()
                .equals(phoneNumber))) {
            checkIfEmployeeExists(employeeUpdateRequest);
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

    void checkIfEmployeeExists(EmployeeUpdateRequest employeeUpdateRequest) {

        boolean existsByIdentity = employeeReadPort
                .existsByIdentity(employeeUpdateRequest.getIdentityNumber());
        if (existsByIdentity) {
            throw new EmployeeAlreadyExistsException(employeeUpdateRequest.getIdentityNumber());
        }

        boolean existsByPhoneNumber = employeeReadPort
                .existsByPhoneNumber(employeeUpdateRequest.getPhoneNumber());
        if (existsByPhoneNumber) {
            throw new EmployeeAlreadyExistsException(employeeUpdateRequest.getPhoneNumber());
        }
    }


    @Override
    public void delete(Long id) {

        employeeReadPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeDeletePort.delete(id);
    }


}
