package com.flz.port;

import com.flz.model.EmployeeOld;

public interface EmployeeOldTestPort {

    EmployeeOld findByIdentityNumber(String identity);

}
