package com.flz.service;

import com.flz.model.EmployeeOld;
import com.flz.model.response.EmployeeOldDetailsResponse;

import java.util.List;

public interface EmployeeOldReadService {

    EmployeeOldDetailsResponse findById(Long id);

    List<EmployeeOld> findAll(Integer page, Integer size);

}
