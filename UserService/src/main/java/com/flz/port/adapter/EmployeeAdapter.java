package com.flz.port.adapter;

import com.flz.model.Employee;
import com.flz.model.entity.EmployeeEntity;
import com.flz.model.mapper.EmployeeEntityToDomainMapper;
import com.flz.model.mapper.EmployeeExperienceToEntityMapper;
import com.flz.model.mapper.EmployeeToEntityMapper;
import com.flz.port.EmployeeDeletePort;
import com.flz.port.EmployeeReadPort;
import com.flz.port.EmployeeSavePort;
import com.flz.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeAdapter implements EmployeeReadPort, EmployeeSavePort, EmployeeDeletePort {

    private final EmployeeRepository employeeRepository;
    private final EmployeeEntityToDomainMapper employeeEntityToDomainMapper;
    private final EmployeeToEntityMapper employeeToEntityMapper;
    private final EmployeeExperienceToEntityMapper employeeExperienceToEntityMapper;


    @Override
    public Optional<Employee> findById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntityToDomainMapper::map);
    }

    @Override
    public List<Employee> findAll(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll(pageable).getContent();
        return employeeEntities.stream().map(employeeEntityToDomainMapper::map).collect(Collectors.toList());
    }

    @Override
    public List<Employee> findSummaryAll() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntityToDomainMapper.map(employeeEntities);
    }

    @Override
    public boolean existsByIdentity(String identity) {
        return employeeRepository.existsByIdentityNumber(identity);
    }

    @Override
    public void save(final Employee employee) {
        final EmployeeEntity employeeEntity = employeeToEntityMapper.map(employee);
        employeeRepository.save(employeeEntity);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }
}
