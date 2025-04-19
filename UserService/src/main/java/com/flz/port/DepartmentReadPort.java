package com.flz.port;

import com.flz.model.Department;

import java.util.Optional;

public interface DepartmentReadPort {

    Optional<Department> findById(Long id);
}
