package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomTypeResponseToEntityMapper extends BaseMapper<RoomTypeResponse, RoomTypeEntity> {

    RoomTypeResponseToEntityMapper INSTANCE = Mappers.getMapper(RoomTypeResponseToEntityMapper.class);

}
