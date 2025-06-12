package com.flz.service;

import com.flz.model.Department;
import com.flz.model.response.DepartmentSummaryResponse;

import java.util.List;

public interface DepartmentReadService {

    List<Department> findAll(Integer page, Integer size);

    List<DepartmentSummaryResponse> findSummaryAll();

}
