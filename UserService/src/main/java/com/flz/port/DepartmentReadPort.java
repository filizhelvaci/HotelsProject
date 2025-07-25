package com.flz.port;

import com.flz.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentReadPort {

    List<Department> findAll(Integer page, Integer pageSize);

    List<Department> findSummaryAll();

    Optional<Department> findById(Long id);

    boolean existsByName(String name);

}
