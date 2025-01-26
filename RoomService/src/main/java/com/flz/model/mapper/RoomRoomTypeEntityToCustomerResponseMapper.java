package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeAvailabilityResponse;

public final class RoomRoomTypeEntityToCustomerResponseMapper {

    private RoomRoomTypeEntityToCustomerResponseMapper() {
    }

    public static RoomTypeAvailabilityResponse map(RoomTypeEntity roomType, Boolean isAvailability) {


        return RoomTypeAvailabilityResponse.builder()
                .id(roomType.getId())
                .name(roomType.getName())
                .price(roomType.getPrice())
                .personCount(roomType.getPersonCount())
                .size(roomType.getSize())
                .isAvailability(isAvailability)
                .build();
    }
}
