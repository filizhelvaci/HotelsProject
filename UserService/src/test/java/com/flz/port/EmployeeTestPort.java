package com.flz.port;

import com.flz.model.Employee;

import java.util.Optional;

public interface EmployeeTestPort {

    Optional<Employee> findByIdentityNumber(String identity);

}
