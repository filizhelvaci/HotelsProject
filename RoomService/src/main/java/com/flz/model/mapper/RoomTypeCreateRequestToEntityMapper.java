package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.request.RoomTypeCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomTypeCreateRequestToEntityMapper extends BaseMapper<RoomTypeCreateRequest, RoomTypeEntity> {

    RoomTypeCreateRequestToEntityMapper INSTANCE = Mappers.getMapper(RoomTypeCreateRequestToEntityMapper.class);
}
