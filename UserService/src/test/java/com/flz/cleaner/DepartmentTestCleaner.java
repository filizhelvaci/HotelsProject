package com.flz.cleaner;

import com.flz.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DepartmentTestCleaner {

    private final DepartmentRepository departmentRepository;

    public DepartmentTestCleaner(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void cleanTestDepartments() {

        departmentRepository.deleteAllByNameContainingIgnoreCase("test");
    }
}