package com.flz.service.impl;

import com.flz.exception.EmployeeNotFoundException;
import com.flz.model.Employee;
import com.flz.model.mapper.EmployeeToEmployeeSummaryResponseMapper;
import com.flz.model.response.EmployeeSummaryResponse;
import com.flz.port.EmployeeReadPort;
import com.flz.service.EmployeeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class EmployeeReadServiceImpl implements EmployeeReadService {

    private final EmployeeReadPort employeeReadPort;

    private final EmployeeToEmployeeSummaryResponseMapper
            employeeToEmployeeSummaryResponseMapper = EmployeeToEmployeeSummaryResponseMapper.INSTANCE;

    @Override
    public Employee findById(Long id) {

        return employeeReadPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }


    @Override
    public List<EmployeeSummaryResponse> findSummaryAll() {

        List<Employee> employee = employeeReadPort.findSummaryAll();
        return employeeToEmployeeSummaryResponseMapper.map(employee);
    }


    @Override
    public List<Employee> findAll(Integer page, Integer pageSize) {

        return employeeReadPort.findAll(page, pageSize);
    }


}
