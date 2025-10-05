package com.flz.port.adapter;

import com.flz.model.Employee;
import com.flz.model.mapper.EmployeeEntityToDomainMapper;
import com.flz.port.EmployeeTestPort;
import com.flz.repository.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeTestAdapter implements EmployeeTestPort {

    private final EmployeeRepository employeeRepository;

    private final EmployeeEntityToDomainMapper
            employeeEntityToDomainMapper = EmployeeEntityToDomainMapper.INSTANCE;


    public EmployeeTestAdapter(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }


    @Override
    public Optional<Employee> findByIdentityNumber(final String identityNumber) {

        return employeeRepository.findByIdentityNumber(identityNumber)
                .map(employeeEntityToDomainMapper::map);
    }

}
