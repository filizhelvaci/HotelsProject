package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.response.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomEntityToResponseMapper extends BaseMapper<RoomEntity, RoomResponse> {

    RoomEntityToResponseMapper INSTANCE = Mappers.getMapper(RoomEntityToResponseMapper.class);
}
