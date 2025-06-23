package com.flz.port;

import com.flz.model.EmployeeExperience;
import com.flz.model.response.EmployeeExperienceResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeExperienceReadPort {

    Optional<EmployeeExperience> findTopByEmployeeIdOrderByStartDateDesc(Long id);

    List<EmployeeExperienceResponse> findAllByEmployee_Id(Long employeeId);

    boolean existsByEmployeeIdAndPositionIdAndStartDate(Long employeeId, Long positionId, LocalDate startDate);

}