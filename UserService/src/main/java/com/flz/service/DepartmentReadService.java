package com.flz.service;

import com.flz.model.response.DepartmentResponse;

public interface DepartmentReadService {

    DepartmentResponse findById(Long id);

}
