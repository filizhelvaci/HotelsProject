package com.flz.service;

import com.flz.model.request.EmployeeExperienceCreateRequest;

public interface EmployeeExperienceWriteService {

    void create(Long id, EmployeeExperienceCreateRequest createRequest);

}
