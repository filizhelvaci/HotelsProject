package com.flz.port;

import com.flz.model.Department;
import com.flz.model.enums.DepartmentStatus;

import java.util.List;
import java.util.Optional;

public interface DepartmentReadPort {

    List<Department> findAll(Integer page, Integer pageSize);

    List<Department> findSummaryAll();

    Optional<Department> findById(Long id);

    Optional<Department> findByManagerIdAndStatus(Long managerId, DepartmentStatus status);

    boolean existsByName(String name);

    boolean existsByManagerId(Long id);

}
