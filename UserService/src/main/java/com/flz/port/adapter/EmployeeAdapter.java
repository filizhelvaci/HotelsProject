package com.flz.port.adapter;

import com.flz.model.Employee;
import com.flz.model.entity.EmployeeEntity;
import com.flz.model.mapper.EmployeeEntityToDomainMapper;
import com.flz.port.EmployeeReadPort;
import com.flz.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeAdapter implements EmployeeReadPort {

    private final EmployeeRepository employeeRepository;
    private final EmployeeEntityToDomainMapper employeeEntityToDomainMapper;

    @Override
    public Optional<Employee> findById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntityToDomainMapper::map);
    }
}
