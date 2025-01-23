package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeResponse;

public final class RoomTypeResponseToEntityMapper {

    private RoomTypeResponseToEntityMapper() {
    }

    public static RoomTypeEntity map(RoomTypeResponse roomTypeResponse) {
        return RoomTypeEntity.builder()
                .id(roomTypeResponse.getId())
                .name(roomTypeResponse.getName())
                .price(roomTypeResponse.getPrice())
                .personCount(roomTypeResponse.getPersonCount())
                .size(roomTypeResponse.getSize())
                .description(roomTypeResponse.getDescription())
                .build();
    }
}
