package com.flz.port;

import com.flz.model.Employee;

public interface EmployeeTestPort {

    Employee findByIdentityNumber(String identity);
}
