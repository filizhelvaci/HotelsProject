package com.flz.model.mapper;

import com.flz.model.Position;
import com.flz.model.request.PositionCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PositionCreateRequestToPositionDomainMapper extends BaseMapper<PositionCreateRequest, Position> {

    PositionCreateRequestToPositionDomainMapper INSTANCE = Mappers.getMapper(PositionCreateRequestToPositionDomainMapper.class);

}
