package com.flz.port.adapter;

import com.flz.model.EmployeeExperience;
import com.flz.model.entity.EmployeeExperienceEntity;
import com.flz.model.mapper.EmployeeExperienceEntityToDomainMapper;
import com.flz.model.mapper.EmployeeExperienceToEntityMapper;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.repository.EmployeeExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmployeeExperienceAdapter implements EmployeeExperienceSavePort, EmployeeExperienceReadPort {

    EmployeeExperienceRepository employeeExperienceRepository;

    private final EmployeeExperienceEntityToDomainMapper employeeExperienceEntityToDomainMapper = EmployeeExperienceEntityToDomainMapper.INSTANCE;
    private final EmployeeExperienceToEntityMapper employeeExperienceToEntityMapper = EmployeeExperienceToEntityMapper.INSTANCE;


    @Override
    public List<EmployeeExperience> findAll() {
        List<EmployeeExperienceEntity> employeeExperienceEntities = employeeExperienceRepository.findAll();
        return employeeExperienceEntityToDomainMapper.map(employeeExperienceEntities);
    }


    @Override
    public void save(final EmployeeExperience employeeExperience) {
        final EmployeeExperienceEntity employeeExperienceEntity = employeeExperienceToEntityMapper.map(employeeExperience);
        employeeExperienceRepository.save(employeeExperienceEntity);
    }


    @Override
    public boolean existsByEmployeeIdAndPositionIdAndStartDate(Long employeeId, Long positionId, LocalDate startDate) {
        return employeeExperienceRepository.existsByEmployeeIdAndPositionIdAndStartDate(employeeId, positionId, startDate);
    }


    @Override
    public Optional<EmployeeExperience> findTopByEmployeeIdOrderByStartDateDesc(Long employeeId) {
        return employeeExperienceRepository.findTopByEmployeeIdOrderByStartDateDesc(employeeId)
                .map(employeeExperienceEntityToDomainMapper::map);
    }


}
