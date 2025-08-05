package com.flz.port;

import com.flz.model.Department;

public interface DepartmentTestPort {

    Department save(Department department);

    Department findByName(String departmentName);
}
