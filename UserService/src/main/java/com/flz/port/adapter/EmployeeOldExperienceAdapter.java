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
class EmployeeOldExperienceAdapter implements EmployeeOldExperienceReadPort, EmployeeOldExperienceSavePort {

    private final EmployeeOldExperienceRepository employeeOldExperienceRepository;

    private final EmployeeOldExperienceToEntityMapper employeeOldExperienceToEntityMapper = EmployeeOldExperienceToEntityMapper.INSTANCE;
    private final EmployeeOldExperienceEntityToDomainMapper employeeOldExperienceEntityToDomainMapper = EmployeeOldExperienceEntityToDomainMapper.INSTANCE;

    @Override
    public List<EmployeeOldExperience> findAllByEmployeeOldId(Long employeeOldId) {
        List<EmployeeOldExperienceEntity> employeeOldExperienceEntities = employeeOldExperienceRepository
                .findAllByEmployeeOld_Id(employeeOldId);
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
