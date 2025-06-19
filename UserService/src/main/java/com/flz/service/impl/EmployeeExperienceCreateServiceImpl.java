package com.flz.service.impl;

import com.flz.exception.EmployeeExperienceAlreadyExistsException;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.request.EmployeeExperienceCreateRequest;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.service.EmployeeExperienceCreateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class EmployeeExperienceCreateServiceImpl implements EmployeeExperienceCreateService {

    private final EmployeeExperienceSavePort employeeExperienceSavePort;
    private final EmployeeExperienceReadPort employeeExperienceReadPort;

    @Override
    @Transactional
    public void create(Long employeeId, EmployeeExperienceCreateRequest createRequest) {

        boolean existsByEmployeeIdAndPositionIdAndStartDate = employeeExperienceReadPort.existsByEmployeeIdAndPositionIdAndStartDate(employeeId, createRequest.getPositionId(), createRequest.getStartDate());
        if (existsByEmployeeIdAndPositionIdAndStartDate) {
            throw new EmployeeExperienceAlreadyExistsException(employeeId);
        }

        Optional<EmployeeExperience> lastExperience = employeeExperienceReadPort.findTopByEmployeeIdOrderByStartDateDesc(employeeId);
        if (lastExperience.isPresent()) {
            EmployeeExperience lastEmployeeExperience = lastExperience.get();
            LocalDate newTransitionDate = createRequest.getStartDate().minusDays(1);
            lastEmployeeExperience.setTransitionDate(newTransitionDate);
            employeeExperienceSavePort.save(lastEmployeeExperience);
        }

        EmployeeExperience employeeExperience = EmployeeExperience.builder().salary(createRequest.getSalary()).startDate(createRequest.getStartDate()).position(Position.builder().id(createRequest.getPositionId()).build()).employee(Employee.builder().id(employeeId).build()).supervisor(Employee.builder().id(createRequest.getSupervisorId()).build()).build();
        employeeExperienceSavePort.save(employeeExperience);
    }


}
