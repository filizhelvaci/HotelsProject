package com.flz.port;

import com.flz.model.EmployeeOld;

import java.util.List;
import java.util.Optional;

public interface EmployeeOldReadPort {

    Optional<EmployeeOld> findById(Long id);

    List<EmployeeOld> findAll(Integer page, Integer pageSize);

}
