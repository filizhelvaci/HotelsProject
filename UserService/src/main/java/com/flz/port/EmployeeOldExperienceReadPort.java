package com.flz.port;

import com.flz.model.EmployeeOldExperience;

import java.util.List;

public interface EmployeeOldExperienceReadPort {

    List<EmployeeOldExperience> findAllByEmployeeId(Long employeeId);

}
