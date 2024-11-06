package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.request.RoomTypeCreateRequest;

import java.time.LocalDateTime;

public final class RoomTypeCreateRequestToEntityMapper {

    private RoomTypeCreateRequestToEntityMapper() {
    }

    public static RoomTypeEntity map(RoomTypeCreateRequest roomTypeCreateRequest) {
        return RoomTypeEntity.builder()
                .name(roomTypeCreateRequest.getName())
                .price(roomTypeCreateRequest.getPrice())
                .personCount(roomTypeCreateRequest.getPersonCount())
                .size(roomTypeCreateRequest.getSize())
                .description(roomTypeCreateRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .createdBy("system")
                .build();

    }
}
