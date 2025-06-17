package com.flz.service;

import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.EmployeeUpdateRequest;

public interface EmployeeCreateService {

    void create(EmployeeCreateRequest employeeCreateRequest);

    void update(Long id, EmployeeUpdateRequest employeeUpdateRequest);

    void delete(Long id);
}
