package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomTypeEntityToResponseMapper extends BaseMapper<RoomTypeEntity, RoomTypeResponse> {

    RoomTypeEntityToResponseMapper INSTANCE = Mappers.getMapper(RoomTypeEntityToResponseMapper.class);

}
