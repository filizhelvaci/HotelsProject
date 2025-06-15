package com.flz.service.impl;

import com.flz.exception.EmployeeNotFoundException;
import com.flz.model.Employee;
import com.flz.port.EmployeeReadPort;
import com.flz.service.EmployeeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeReadServiceImpl implements EmployeeReadService {

    private final EmployeeReadPort employeeReadPort;

    @Override
    public Employee findById(Long id) {
        return employeeReadPort.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }


}
