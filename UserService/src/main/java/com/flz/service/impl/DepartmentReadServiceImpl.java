package com.flz.service.impl;

import com.flz.exception.DepartmentNotFoundException;
import com.flz.model.Department;
import com.flz.model.mapper.DepartmentToDepartmentResponseMapper;
import com.flz.model.response.DepartmentResponse;
import com.flz.port.DepartmentReadPort;
import com.flz.service.DepartmentReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DepartmentReadServiceImpl implements DepartmentReadService {

    private final DepartmentReadPort departmentReadPort;

    private final DepartmentToDepartmentResponseMapper departmentToDepartmentResponseMapper = DepartmentToDepartmentResponseMapper.INSTANCE;

    @Override
    public DepartmentResponse findById(Long id) {

        Department department = departmentReadPort.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));
        return departmentToDepartmentResponseMapper.map(department);
    }

}
