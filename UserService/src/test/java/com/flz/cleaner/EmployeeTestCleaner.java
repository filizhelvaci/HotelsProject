package com.flz.cleaner;

import com.flz.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class EmployeeTestCleaner {

    private final EmployeeRepository employeeRepository;

    public EmployeeTestCleaner(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void cleanEmployees() {
        employeeRepository.deleteAllByFirstNameContainingIgnoreCase("test");
    }

}
