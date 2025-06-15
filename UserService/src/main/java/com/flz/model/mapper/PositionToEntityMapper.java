package com.flz.model.mapper;

import com.flz.model.Position;
import com.flz.model.entity.PositionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PositionToEntityMapper extends BaseMapper<Position, PositionEntity> {

    PositionToEntityMapper INSTANCE = Mappers.getMapper(PositionToEntityMapper.class);

}
