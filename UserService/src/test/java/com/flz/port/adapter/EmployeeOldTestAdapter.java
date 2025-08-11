package com.flz.port.adapter;

import com.flz.model.EmployeeOld;
import com.flz.model.mapper.EmployeeOldEntityToDomainMapper;
import com.flz.port.EmployeeOldTestPort;
import com.flz.repository.EmployeeOldRepository;
import org.springframework.stereotype.Component;

@Component
public class EmployeeOldTestAdapter implements EmployeeOldTestPort {

    private final EmployeeOldRepository employeeOldRepository;

    private final EmployeeOldEntityToDomainMapper
            employeeOldEntityToDomainMapper = EmployeeOldEntityToDomainMapper.INSTANCE;

    public EmployeeOldTestAdapter(EmployeeOldRepository employeeOldRepository) {
        this.employeeOldRepository = employeeOldRepository;
    }


    @Override
    public EmployeeOld findByIdentityNumber(final String identityNumber) {
        return employeeOldEntityToDomainMapper.map(employeeOldRepository.findByIdentityNumber(identityNumber));
    }

}
