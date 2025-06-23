package com.flz.port;

import com.flz.model.Employee;
import com.flz.model.response.EmployeeSummaryResponse;

import java.util.List;
import java.util.Optional;

public interface EmployeeReadPort {

    Optional<Employee> findById(Long id);

    List<Employee> findAll(Integer page, Integer pageSize);

    List<EmployeeSummaryResponse> findSummaryAll();

    boolean existsByIdentity(String identity);
}
