package com.flz.service;

import com.flz.model.Employee;
import com.flz.model.response.EmployeeDetailsResponse;
import com.flz.model.response.EmployeeSummaryResponse;

import java.util.List;

public interface EmployeeReadService {

    EmployeeDetailsResponse findById(Long id);

    List<Employee> findAll(Integer page, Integer size);

    List<EmployeeSummaryResponse> findSummaryAll();

}
