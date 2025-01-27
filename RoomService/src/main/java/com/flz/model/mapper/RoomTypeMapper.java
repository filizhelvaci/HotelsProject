package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.response.RoomTypeAvailabilityResponse;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomTypesResponse;
import com.flz.model.response.RoomTypesSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomTypeMapper {

    RoomTypeMapper INSTANCE = Mappers.getMapper(RoomTypeMapper.class);

    RoomTypeEntity toRoomTypeEntity(final RoomTypeCreateRequest roomTypeCreateRequest);

    RoomTypeResponse toRoomTypeResponse(final RoomTypeEntity roomTypeEntity);

    RoomTypesResponse toRoomTypesResponse(final RoomTypeEntity roomTypeEntity);

    RoomTypesSummaryResponse toRoomTypesSummaryResponse(final RoomTypeEntity roomTypeEntity);

    RoomTypeEntity toRoomTypeEntityFromResponse(final RoomTypeResponse roomTypeResponse);

    @Mapping(target = "isAvailability", ignore = true)
    RoomTypeAvailabilityResponse toRoomTypeAvailabilityResponse(final RoomTypeEntity roomTypeEntity, Boolean isAvailability);

}
