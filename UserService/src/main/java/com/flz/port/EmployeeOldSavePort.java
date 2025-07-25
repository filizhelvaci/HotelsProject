package com.flz.port;

import com.flz.model.EmployeeOld;

import java.util.Optional;

public interface EmployeeOldSavePort {

    Optional<EmployeeOld> save(EmployeeOld employeeOld);

}
