package com.flz.port;

import com.flz.model.EmployeeOld;

import java.util.Optional;

public interface EmployeeOldTestPort {

    Optional<EmployeeOld> findByIdentityNumber(String identity);

}
