package com.flz.service.impl;

import com.flz.exception.EmployeeExperienceAlreadyExistsException;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.request.EmployeeExperienceCreateRequest;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.port.EmployeeReadPort;
import com.flz.port.PositionReadPort;
import com.flz.service.EmployeeExperienceWriteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class EmployeeExperienceCreateServiceImpl implements EmployeeExperienceWriteService {

    private final EmployeeExperienceSavePort employeeExperienceSavePort;
    private final EmployeeExperienceReadPort employeeExperienceReadPort;
    private final EmployeeReadPort employeeReadPort;
    private final PositionReadPort positionReadPort;


    @Override
    @Transactional
    public void create(Long employeeId, EmployeeExperienceCreateRequest createRequest) {

        Employee employee = employeeReadPort.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        Employee employeeSupervisor = employeeReadPort.findById(createRequest.getSupervisorId())
                .orElseThrow(() -> new EmployeeNotFoundException(createRequest.getSupervisorId()));

        Position position = positionReadPort.findById(createRequest.getPositionId())
                .orElseThrow(() -> new PositionNotFoundException(createRequest.getPositionId()));

        boolean existsByEmployeeIdAndPositionIdAndStartDate = employeeExperienceReadPort
                .existsByEmployeeIdAndPositionIdAndStartDate(
                        employeeId,
                        createRequest.getPositionId(),
                        createRequest.getStartDate());

        if (existsByEmployeeIdAndPositionIdAndStartDate) {
            throw new EmployeeExperienceAlreadyExistsException(employeeId);
        }

        Optional<EmployeeExperience> lastExperience = employeeExperienceReadPort
                .findTopByEmployeeIdOrderByStartDateDesc(employeeId);
        if (lastExperience.isPresent()) {
            EmployeeExperience lastEmployeeExperience = lastExperience.get();
            LocalDate newEndDate = createRequest.getStartDate().minusDays(1);
            lastEmployeeExperience.setEndDate(newEndDate);
            employeeExperienceSavePort.save(lastEmployeeExperience);
        }

        EmployeeExperience employeeExperience = EmployeeExperience.builder()
                .salary(createRequest.getSalary())
                .startDate(createRequest.getStartDate())
                .position(position)
                .employee(employee)
                .supervisor(employeeSupervisor)
                .build();
        employeeExperienceSavePort.save(employeeExperience);
    }


}
