package com.flz.port.adapter;

import com.flz.model.Position;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.entity.PositionEntity;
import com.flz.model.mapper.DepartmentToEntityMapper;
import com.flz.model.mapper.PositionEntityToDomainMapper;
import com.flz.model.mapper.PositionToEntityMapper;
import com.flz.port.PositionReadPort;
import com.flz.port.PositionSavePort;
import com.flz.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class PositionAdapter implements PositionSavePort, PositionReadPort {

    private final PositionRepository positionRepository;

    private static final PositionToEntityMapper
            positionToEntityMapper = PositionToEntityMapper.INSTANCE;
    private static final PositionEntityToDomainMapper
            positionEntityToDomainMapper = PositionEntityToDomainMapper.INSTANCE;
    private static final DepartmentToEntityMapper
            departmentToEntityMapper = DepartmentToEntityMapper.INSTANCE;


    @Override
    public List<Position> findAll(Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);
        List<PositionEntity> positionEntities = positionRepository.findAll(pageable).getContent();
        return positionEntities.stream().map(positionEntityToDomainMapper::map).toList();
    }


    @Override
    public List<Position> findSummaryAll() {

        List<PositionEntity> positionEntities = positionRepository.findAll();
        return positionEntityToDomainMapper.map(positionEntities);
    }


    @Override
    public Optional<Position> findById(Long id) {

        Optional<PositionEntity> positionEntity = positionRepository.findById(id);
        return positionEntity.map(positionEntityToDomainMapper::map);
    }


    @Override
    public boolean existsByName(String name) {

        return positionRepository.existsByName(name);
    }


    @Override
    public void save(final Position position) {

        final PositionEntity positionEntity = positionToEntityMapper.map(position);
        final DepartmentEntity departmentEntity = departmentToEntityMapper.map(position.getDepartment());
        positionEntity.setDepartment(departmentEntity);
        positionRepository.save(positionEntity);
    }

}
