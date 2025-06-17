package com.flz.service;

import com.flz.model.EmployeeExperience;

import java.util.List;

public interface EmployeeExperienceReadService {

    EmployeeExperience findById(Long id);

    List<EmployeeExperience> findAll(Integer page, Integer size);

}
