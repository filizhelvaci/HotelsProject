package com.flz.port.adapter;

import com.flz.model.Department;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.mapper.DepartmentEntityToDomainMapper;
import com.flz.model.mapper.DepartmentToEntityMapper;
import com.flz.port.DepartmentTestPort;
import com.flz.repository.DepartmentRepository;
import org.springframework.stereotype.Component;

@Component
public class DepartmentTestAdapter implements DepartmentTestPort {

    private final DepartmentRepository departmentRepository;

    private final DepartmentToEntityMapper departmentToEntityMapper = DepartmentToEntityMapper.INSTANCE;
    private final DepartmentEntityToDomainMapper departmentEntityToDomainMapper = DepartmentEntityToDomainMapper.INSTANCE;

    public DepartmentTestAdapter(DepartmentRepository departmentRepository) {

        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department save(Department department) {
        DepartmentEntity departmentEntity = departmentRepository.save(departmentToEntityMapper.map(department));
        return departmentEntityToDomainMapper.map(departmentEntity);
    }

    public Department findByName(String name) {
        return departmentEntityToDomainMapper.map(departmentRepository.findByName(name));
    }
}
