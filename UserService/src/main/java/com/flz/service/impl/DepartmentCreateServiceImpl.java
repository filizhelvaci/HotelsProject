package com.flz.service.impl;

import com.flz.exception.DepartmentAlreadyExistsException;
import com.flz.exception.DepartmentNotFoundException;
import com.flz.model.Department;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.mapper.DepartmentCreateRequestToDomainMapper;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
import com.flz.service.DepartmentCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DepartmentCreateServiceImpl implements DepartmentCreateService {

    private final DepartmentSavePort departmentSavePort;
    private final DepartmentReadPort departmentReadPort;

    private final DepartmentCreateRequestToDomainMapper departmentCreateRequestToDomainMapper = DepartmentCreateRequestToDomainMapper.INSTANCE;

    @Override
    public void create(DepartmentCreateRequest createRequest) {
        boolean existsByName = departmentReadPort.existsByName(createRequest.getName());
        if (existsByName) {
            throw new DepartmentAlreadyExistsException(createRequest.getName());
        }

        Department department = departmentCreateRequestToDomainMapper.map(createRequest);
        departmentSavePort.save(department);

    }

    @Override
    public void update(Long id, DepartmentUpdateRequest departmentUpdateRequest) {
        Department department = departmentReadPort.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        boolean existsByName = departmentReadPort.existsByName(departmentUpdateRequest.getName());
        if (existsByName) {
            throw new DepartmentAlreadyExistsException(departmentUpdateRequest.getName());
        }

        department.setName(departmentUpdateRequest.getName());
        departmentSavePort.save(department);
    }

    @Override
    public void delete(Long id) {
        Department department = departmentReadPort.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        department.setStatus(DepartmentStatus.DELETED);
        departmentSavePort.save(department);
    }

}
