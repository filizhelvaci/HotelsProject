package com.flz.port.adapter;

import com.flz.model.EmployeeOldExperience;
import com.flz.model.entity.EmployeeOldExperienceEntity;
import com.flz.model.mapper.EmployeeOldExperienceEntityToDomainMapper;
import com.flz.model.mapper.EmployeeOldExperienceToEntityMapper;
import com.flz.port.EmployeeOldExperienceReadPort;
import com.flz.port.EmployeeOldExperienceSavePort;
import com.flz.repository.EmployeeOldExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeOldExperienceAdapter implements EmployeeOldExperienceReadPort, EmployeeOldExperienceSavePort {

    private final EmployeeOldExperienceRepository employeeOldExperienceRepository;

    private final EmployeeOldExperienceToEntityMapper employeeOldExperienceToEntityMapper;
    private final EmployeeOldExperienceEntityToDomainMapper employeeOldExperienceEntityToDomainMapper;

    @Override
    public List<EmployeeOldExperience> findAllByEmployeeId(Long employeeId) {
        List<EmployeeOldExperienceEntity> employeeOldExperienceEntities = employeeOldExperienceRepository
                .findAllByEmployee_Id(employeeId);
        return employeeOldExperienceEntityToDomainMapper.map(employeeOldExperienceEntities);
    }

    @Override
    public List<EmployeeOldExperience> saveAll(List<EmployeeOldExperience> employeeOldExperiences) {
        List<EmployeeOldExperienceEntity> employeeOldExperienceEntities = employeeOldExperienceToEntityMapper
                .map(employeeOldExperiences);
        return employeeOldExperienceEntityToDomainMapper
                .map(employeeOldExperienceRepository.saveAll(employeeOldExperienceEntities));
    }


}
