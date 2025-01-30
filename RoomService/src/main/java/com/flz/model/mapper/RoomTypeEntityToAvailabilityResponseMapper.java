package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeAvailabilityResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomTypeEntityToAvailabilityResponseMapper extends BaseMapper<RoomTypeEntity, RoomTypeAvailabilityResponse> {

    RoomTypeEntityToAvailabilityResponseMapper INSTANCE = Mappers.getMapper(RoomTypeEntityToAvailabilityResponseMapper.class);

    RoomTypeAvailabilityResponse toAvailability(RoomTypeEntity roomTypeEntity, Boolean availability);

}
