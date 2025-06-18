package com.flz.port;

import com.flz.model.EmployeeExperience;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeExperienceReadPort {

    List<EmployeeExperience> findAll();

    Optional<EmployeeExperience> findTopByEmployeeIdOrderByStartDateDesc(Long id);

    boolean existsByEmployeeIdAndPositionIdAndStartDate(Long employeeId, Long positionId, LocalDate startDate);

}