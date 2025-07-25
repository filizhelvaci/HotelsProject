package com.flz.port;

import com.flz.model.EmployeeOldExperience;

import java.util.List;

public interface EmployeeOldExperienceSavePort {

    List<EmployeeOldExperience> saveAll(List<EmployeeOldExperience> employeeOldExperiences);

}
