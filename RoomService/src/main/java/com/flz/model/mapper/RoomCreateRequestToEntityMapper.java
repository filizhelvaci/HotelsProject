package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.request.RoomCreateRequest;

import java.time.LocalDateTime;

public final class RoomCreateRequestToEntityMapper {

    private RoomCreateRequestToEntityMapper() {
    }

    public static RoomEntity map(RoomCreateRequest createRequest) {
        return RoomEntity.builder()
                .number(createRequest.getNumber())
                .floor(createRequest.getFloor())
                .status(createRequest.getStatus())
                .createdAt(LocalDateTime.now())
                .createdBy("hotel-system")
                .build();
    }
}
