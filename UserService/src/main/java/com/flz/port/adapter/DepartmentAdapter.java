package com.flz.port.adapter;

import com.flz.model.Department;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.mapper.DepartmentEntityToDomainMapper;
import com.flz.model.mapper.DepartmentToEntityMapper;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
import com.flz.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class DepartmentAdapter implements DepartmentReadPort, DepartmentSavePort {

    private final DepartmentRepository departmentRepository;

    private final DepartmentToEntityMapper departmentToEntityMapper;
    private final DepartmentEntityToDomainMapper departmentEntityToDomainMapper;


    @Override
    public List<Department> findAll(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll(pageable).getContent();
        return departmentEntityToDomainMapper.map(departmentEntities);
    }

    @Override
    public List<Department> findSummaryAll() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntityToDomainMapper.map(departmentEntities);
    }


    @Override
    public Optional<Department> findById(Long id) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(id);
        return departmentEntity.map(departmentEntityToDomainMapper::map);
    }

    @Override
    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    @Override
    public void save(final Department department) {
        final DepartmentEntity departmentEntity = departmentToEntityMapper.map(department);
        departmentRepository.save(departmentEntity);
    }

}
