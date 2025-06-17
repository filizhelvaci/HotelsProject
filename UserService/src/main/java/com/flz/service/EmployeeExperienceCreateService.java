package com.flz.service;

import com.flz.model.request.EmployeeExperienceCreateRequest;

public interface EmployeeExperienceCreateService {

    void create(Long id, EmployeeExperienceCreateRequest createRequest);

}
