package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.request.RoomCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomCreateRequestToEntityMapper extends BaseMapper<RoomCreateRequest, RoomEntity> {

    RoomCreateRequestToEntityMapper INSTANCE = Mappers.getMapper(RoomCreateRequestToEntityMapper.class);

}
