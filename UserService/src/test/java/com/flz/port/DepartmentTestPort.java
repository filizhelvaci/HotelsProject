package com.flz.port;

import com.flz.model.Department;

import java.util.Optional;

public interface DepartmentTestPort {

    Department save(Department department);

    Optional<Department> findByName(String departmentName);
}
