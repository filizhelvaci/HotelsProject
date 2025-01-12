package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.request.RoomUpdateRequest;

import java.time.LocalDateTime;

public final class RoomUpdateRequestToEntityMapper {

    private RoomUpdateRequestToEntityMapper() {
    }

    public static void map(RoomUpdateRequest roomUpdateRequest, RoomEntity roomEntity) {
        roomEntity.setNumber(roomUpdateRequest.getNumber());
        roomEntity.setFloor(roomUpdateRequest.getFloor());
        roomEntity.setStatus(roomUpdateRequest.getStatus());
        roomEntity.setUpdatedAt(LocalDateTime.now());
        roomEntity.setUpdatedBy("hotel-system");
    }
}
