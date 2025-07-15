package com.flz.service.impl;

import com.flz.exception.EmployeeNotFoundException;
import com.flz.model.Employee;
import com.flz.model.mapper.EmployeeToEmployeeSummaryResponseMapper;
import com.flz.model.response.EmployeeDetailsResponse;
import com.flz.model.response.EmployeeExperienceResponse;
import com.flz.model.response.EmployeeSummaryResponse;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeReadPort;
import com.flz.service.EmployeeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class EmployeeReadServiceImpl implements EmployeeReadService {

    private final EmployeeReadPort employeeReadPort;
    private final EmployeeExperienceReadPort employeeExperienceReadPort;

    private final EmployeeToEmployeeSummaryResponseMapper
            employeeToEmployeeSummaryResponseMapper = EmployeeToEmployeeSummaryResponseMapper.INSTANCE;

    @Override
    public EmployeeDetailsResponse findById(Long id) {

        Employee employee = employeeReadPort.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        List<EmployeeExperienceResponse> employeeExperiences = employeeExperienceReadPort
                .findAllByEmployee_Id(id);
        if (employeeExperiences.isEmpty()) {
            System.out.println("No employee experiences found, " +
                    "So please add employee experience for this employee");
        }

        return EmployeeDetailsResponse.builder()
                .employee(employee)
                .experiences(employeeExperiences)
                .build();
    }


    @Override
    public List<EmployeeSummaryResponse> findSummaryAll() {

        return employeeReadPort.findSummaryAll();
    }


    @Override
    public List<Employee> findAll(Integer page, Integer pageSize) {

        return employeeReadPort.findAll(page, pageSize);
    }


}
