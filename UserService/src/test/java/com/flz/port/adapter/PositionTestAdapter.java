package com.flz.port.adapter;

import com.flz.model.Position;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.entity.PositionEntity;
import com.flz.model.mapper.DepartmentToEntityMapper;
import com.flz.model.mapper.PositionEntityToDomainMapper;
import com.flz.model.mapper.PositionToEntityMapper;
import com.flz.port.PositionTestPort;
import com.flz.repository.PositionRepository;
import org.springframework.stereotype.Component;

@Component
public class PositionTestAdapter implements PositionTestPort {

    private final PositionRepository positionRepository;

    private final PositionToEntityMapper positionToEntityMapper = PositionToEntityMapper.INSTANCE;
    private final PositionEntityToDomainMapper positionEntityToDomainMapper = PositionEntityToDomainMapper.INSTANCE;
    private final DepartmentToEntityMapper departmentToEntityMapper = DepartmentToEntityMapper.INSTANCE;

    public PositionTestAdapter(PositionRepository positionRepository) {

        this.positionRepository = positionRepository;
    }

    @Override
    public Position save(final Position position) {
        final PositionEntity positionEntity = positionToEntityMapper.map(position);
        final DepartmentEntity departmentEntity = departmentToEntityMapper.map(position.getDepartment());
        positionEntity.setDepartment(departmentEntity);
        PositionEntity positionSavedEntity = positionRepository.save(positionEntity);
        return positionEntityToDomainMapper.map(positionSavedEntity);
    }

    @Override
    public Position findByName(final String name) {
        PositionEntity positionEntity = positionRepository.findByName(name);
        return positionEntityToDomainMapper.map(positionEntity);
    }

}
