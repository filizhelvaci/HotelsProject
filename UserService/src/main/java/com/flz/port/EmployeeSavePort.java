package com.flz.port;

import com.flz.model.Employee;

import java.util.Optional;

public interface EmployeeSavePort {

    Optional<Employee> save(Employee employee);

}
