package com.flz.model.mapper;

import com.flz.model.Position;
import com.flz.model.entity.PositionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = DepartmentEntityToDomainMapper.class)
public interface PositionEntityToDomainMapper extends BaseMapper<PositionEntity, Position> {

    PositionEntityToDomainMapper INSTANCE = Mappers.getMapper(PositionEntityToDomainMapper.class);

}
