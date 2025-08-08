package com.flz.port.adapter;

import com.flz.model.Employee;
import com.flz.model.entity.EmployeeEntity;
import com.flz.model.mapper.EmployeeEntityToDomainMapper;
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

@Component
@RequiredArgsConstructor
class EmployeeAdapter implements EmployeeReadPort, EmployeeSavePort, EmployeeDeletePort {

    private final EmployeeRepository employeeRepository;

    private static final EmployeeEntityToDomainMapper
            employeeEntityToDomainMapper = EmployeeEntityToDomainMapper.INSTANCE;
    private static final EmployeeToEntityMapper
            employeeToEntityMapper = EmployeeToEntityMapper.INSTANCE;


    @Override
    public Optional<Employee> findById(Long id) {

        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        return employeeEntity.map(employeeEntityToDomainMapper::map);
    }


    @Override
    public List<Employee> findAll(Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<EmployeeEntity> employeeEntities = employeeRepository
                .findAll(pageable)
                .getContent();
        return employeeEntities.stream()
                .map(employeeEntityToDomainMapper::map)
                .toList();
    }


    @Override
    public List<Employee> findSummaryAll() {

        return employeeRepository.findEmployeeSummaries();
    }


    @Override
    public boolean existsByIdentity(String identity) {

        return employeeRepository.existsByIdentityNumber(identity);
    }


    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {

        return employeeRepository.existsByPhoneNumber(phoneNumber);
    }


    @Override
    public Employee save(final Employee employee) {

        final EmployeeEntity employeeEntity = employeeToEntityMapper.map(employee);
        return employeeEntityToDomainMapper
                .map(employeeRepository.save(employeeEntity));
    }


    @Override
    public void delete(Long id) {

        employeeRepository.deleteById(id);
    }

}
