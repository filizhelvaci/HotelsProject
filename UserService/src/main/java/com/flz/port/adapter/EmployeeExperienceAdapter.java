package com.flz.port.adapter;

import com.flz.model.EmployeeExperience;
import com.flz.model.entity.EmployeeExperienceEntity;
import com.flz.model.mapper.EmployeeExperienceEntityToDomainMapper;
import com.flz.model.mapper.EmployeeExperienceEntityToResponseMapper;
import com.flz.model.mapper.EmployeeExperienceToEntityMapper;
import com.flz.model.response.EmployeeExperienceResponse;
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

    private final EmployeeExperienceRepository employeeExperienceRepository;

    private final EmployeeExperienceEntityToDomainMapper employeeExperienceEntityToDomainMapper = EmployeeExperienceEntityToDomainMapper.INSTANCE;
    private final EmployeeExperienceToEntityMapper employeeExperienceToEntityMapper = EmployeeExperienceToEntityMapper.INSTANCE;
    private final EmployeeExperienceEntityToResponseMapper employeeExperienceEntityToResponseMapper = EmployeeExperienceEntityToResponseMapper.INSTANCE;

    @Override
    public List<EmployeeExperienceResponse> findAllByEmployee_Id(Long employeeId) {
        List<EmployeeExperienceEntity> employeeExperienceEntities = employeeExperienceRepository.findAllByEmployee_Id(employeeId);

        return employeeExperienceEntityToResponseMapper.map(employeeExperienceEntities);

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
        return employeeExperienceRepository.findTopByEmployeeIdOrderByStartDateDesc(employeeId).map(employeeExperienceEntityToDomainMapper::map);
    }

}
