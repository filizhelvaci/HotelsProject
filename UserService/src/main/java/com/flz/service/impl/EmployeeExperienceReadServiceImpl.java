package com.flz.service.impl;

import com.flz.model.EmployeeExperience;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.service.EmployeeExperienceReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class EmployeeExperienceReadServiceImpl implements EmployeeExperienceReadService {

    private final EmployeeExperienceReadPort employeeExperienceReadPort;

    @Override
    public List<EmployeeExperience> findAll() {
        return employeeExperienceReadPort.findAll();

    }

}
