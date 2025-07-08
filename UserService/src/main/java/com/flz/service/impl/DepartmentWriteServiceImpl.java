package com.flz.service.impl;

import com.flz.exception.DepartmentAlreadyDeletedException;
import com.flz.exception.DepartmentAlreadyExistsException;
import com.flz.exception.DepartmentNotFoundException;
import com.flz.model.Department;
import com.flz.model.mapper.DepartmentCreateRequestToDomainMapper;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
import com.flz.service.DepartmentWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class DepartmentWriteServiceImpl implements DepartmentWriteService {

    private final DepartmentSavePort departmentSavePort;
    private final DepartmentReadPort departmentReadPort;

    private final DepartmentCreateRequestToDomainMapper
            departmentCreateRequestToDomainMapper = DepartmentCreateRequestToDomainMapper.INSTANCE;

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

        String name = departmentUpdateRequest.getName();

        if (!(department.getName().equals(name))) {
            checkIfDepartmentNameExists(departmentUpdateRequest);
        }

        department.setName(departmentUpdateRequest.getName());
        department.setUpdatedAt(LocalDateTime.now());
        department.setUpdatedUser("SYSTEM");
        departmentSavePort.save(department);
    }

    void checkIfDepartmentNameExists(DepartmentUpdateRequest departmentUpdateRequest) {

        boolean existsByName = departmentReadPort
                .existsByName(departmentUpdateRequest.getName());
        if (existsByName) {
            throw new DepartmentAlreadyExistsException(departmentUpdateRequest.getName());
        }
    }


    @Override
    public void delete(Long id) {

        Department department = departmentReadPort.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        if (department.isDeleted()) {
            throw new DepartmentAlreadyDeletedException(department.getId());
        }

        department.delete();
        departmentSavePort.save(department);
    }


}
