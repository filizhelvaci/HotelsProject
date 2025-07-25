package com.flz.service.impl;

import com.flz.exception.EmployeeOldNotFoundException;
import com.flz.model.EmployeeOld;
import com.flz.model.EmployeeOldExperience;
import com.flz.model.mapper.EmployeeOldExperienceToResponseMapper;
import com.flz.model.response.EmployeeOldDetailsResponse;
import com.flz.model.response.EmployeeOldExperienceResponse;
import com.flz.port.EmployeeOldExperienceReadPort;
import com.flz.port.EmployeeOldReadPort;
import com.flz.service.EmployeeOldReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
class EmployeeOldReadServiceImpl implements EmployeeOldReadService {

    private final EmployeeOldReadPort employeeOldReadPort;
    private final EmployeeOldExperienceReadPort employeeOldExperienceReadPort;

    private final EmployeeOldExperienceToResponseMapper employeeOldExperienceToResponseMapper;

    @Override
    public EmployeeOldDetailsResponse findById(Long id) {

        EmployeeOld employeeOld = employeeOldReadPort.findById(id)
                .orElseThrow(() -> new EmployeeOldNotFoundException(id));

        List<EmployeeOldExperience> experiences = Optional.ofNullable(
                        employeeOldExperienceReadPort.findAllByEmployeeId(id)
                )
                .orElse(Collections.emptyList());

        List<EmployeeOldExperienceResponse> employeeOldExperiences = experiences.stream()
                .map(employeeOldExperienceToResponseMapper::map)
                .toList();

        if (employeeOldExperiences.isEmpty()) {
            Logger.getLogger("No employee old experiences found !");
        }

        return EmployeeOldDetailsResponse.builder()
                .employeeOld(employeeOld)
                .experiences(employeeOldExperiences)
                .build();
    }

    @Override
    public List<EmployeeOld> findAll(Integer page, Integer pageSize) {

        return employeeOldReadPort.findAll(page, pageSize);
    }

}
