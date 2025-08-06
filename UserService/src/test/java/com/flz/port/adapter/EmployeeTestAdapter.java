package com.flz.port.adapter;

import com.flz.model.Employee;
import com.flz.model.mapper.EmployeeEntityToDomainMapper;
import com.flz.port.EmployeeTestPort;
import com.flz.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class EmployeeTestAdapter implements EmployeeTestPort {

    private final EmployeeRepository employeeRepository;

    private final EmployeeEntityToDomainMapper
            employeeEntityToDomainMapper = EmployeeEntityToDomainMapper.INSTANCE;

    public EmployeeTestAdapter(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findByIdentityNumber(final String identityNumber) {
        return employeeEntityToDomainMapper.map(employeeRepository.findByIdentityNumber(identityNumber));
    }

}
