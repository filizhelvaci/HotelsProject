package com.flz.port;

import com.flz.model.Employee;

import java.util.Optional;

public interface EmployeeReadPort {

    Optional<Employee> findById(Long id);
}
