package com.flz.port.adapter;

import com.flz.model.Department;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.mapper.DepartmentEntityToDomainMapper;
import com.flz.model.mapper.DepartmentToEntityMapper;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
import com.flz.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class DepartmentAdapter implements DepartmentReadPort, DepartmentSavePort {

    private final DepartmentRepository departmentRepository;

    private final DepartmentToEntityMapper departmentToEntityMapper = DepartmentToEntityMapper.INSTANCE;
    private final DepartmentEntityToDomainMapper departmentEntityToDomainMapper = DepartmentEntityToDomainMapper.INSTANCE;


    @Override
    public Optional<Department> findById(final Long id) {
        final Optional<DepartmentEntity> departmentEntity = departmentRepository
                .findById(id);
        return departmentEntity.map(departmentEntityToDomainMapper::map);
    }

    @Override
    public void save(final Department department) {
        final DepartmentEntity departmentEntity = departmentToEntityMapper.map(department);
        departmentRepository.save(departmentEntity);
        departmentEntityToDomainMapper.map(departmentEntity);
    }
}
