package com.flz.service.impl;

import com.flz.exception.EmployeeAlreadyExistsException;
import com.flz.exception.EmployeeNotFoundException;
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
import com.flz.service.EmployeeCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EmployeeCreateServiceImpl implements EmployeeCreateService {

    private final EmployeeReadPort employeeReadPort;
    private final EmployeeSavePort employeeSavePort;
    private final EmployeeDeletePort employeeDeletePort;
    private final EmployeeExperienceSavePort employeeExperienceSavePort;

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

        Employee supervisor = null;
        if (createRequest.getSupervisorId() != null) {
            supervisor = Employee.builder().id(createRequest.getSupervisorId()).build();
        }

        EmployeeExperience employeeExperience = EmployeeExperience.builder()
                .salary(createRequest.getSalary())
                .startDate(createRequest.getStartDate())
                .position(Position.builder().id(createRequest.getPositionId()).build())
                .supervisor(supervisor)
                .build();

        employee.addExperience(employeeExperience);
        employeeSavePort.save(employee);
    }


    @Override
    public void update(Long id, EmployeeUpdateRequest employeeUpdateRequest) {

        Employee employee = employeeReadPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

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


    @Override
    public void delete(Long id) {

        employeeReadPort.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeDeletePort.delete(id);
    }


}
