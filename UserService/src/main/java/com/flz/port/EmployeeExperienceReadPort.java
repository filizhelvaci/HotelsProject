package com.flz.port;

import com.flz.model.EmployeeExperience;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeExperienceReadPort {

    Optional<EmployeeExperience> findTopByEmployeeIdOrderByStartDateDesc(Long id);

    List<EmployeeExperience> findAllByEmployeeId(Long employeeId);

    boolean existsByEmployeeIdAndPositionIdAndStartDate(Long employeeId, Long positionId, LocalDate startDate);

}