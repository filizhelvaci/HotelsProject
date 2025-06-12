package com.flz.service.impl;

import com.flz.model.Department;
import com.flz.model.mapper.DepartmentToDepartmentSummaryResponseMapper;
import com.flz.model.response.DepartmentSummaryResponse;
import com.flz.port.DepartmentReadPort;
import com.flz.service.DepartmentReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class DepartmentReadServiceImpl implements DepartmentReadService {

    private final DepartmentReadPort departmentReadPort;

    private final DepartmentToDepartmentSummaryResponseMapper departmentToDepartmentSummaryResponseMapper = DepartmentToDepartmentSummaryResponseMapper.INSTANCE;

    @Override
    public List<DepartmentSummaryResponse> findSummaryAll() {
        List<Department> department = departmentReadPort.findSummaryAll();
        return departmentToDepartmentSummaryResponseMapper.map(department);
    }

    @Override
    public List<Department> findAll(Integer page, Integer pageSize) {
        return departmentReadPort.findAll(page, pageSize);
    }

}
