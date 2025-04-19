package com.flz.service.impl;

import com.flz.model.Department;
import com.flz.model.mapper.DepartmentCreateRequestToDomainMapper;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.port.DepartmentSavePort;
import com.flz.service.DepartmentCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class DepartmentCreateServiceImpl implements DepartmentCreateService {

    private final DepartmentSavePort departmentSavePort;

    private final DepartmentCreateRequestToDomainMapper departmentCreateRequestToDomainMapper = DepartmentCreateRequestToDomainMapper.INSTANCE;

    @Override
    public void create(final DepartmentCreateRequest createRequest) {

        final Department department = departmentCreateRequestToDomainMapper.map(createRequest);
        departmentSavePort.save(department);

    }
}
