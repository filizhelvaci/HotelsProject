package com.flz.service;

import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;

public interface DepartmentCreateService {

    void create(DepartmentCreateRequest departmentCreateRequest);

    void update(Long id, DepartmentUpdateRequest departmentUpdateRequest);

    void delete(Long id);
}
