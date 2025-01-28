package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.request.RoomTypeCreateRequest;

import java.time.LocalDateTime;

public final class RoomTypeCreateRequestToEntityMapper {

    private RoomTypeCreateRequestToEntityMapper() {
    }

    public static RoomTypeEntity map(RoomTypeCreateRequest roomTypeCreateRequest) {
        RoomTypeEntity roomTypeEntity = RoomTypeMapper.INSTANCE.toRoomTypeEntity(roomTypeCreateRequest);
        roomTypeEntity.setCreatedAt(LocalDateTime.now());
        roomTypeEntity.setCreatedBy("admin");
        return roomTypeEntity;
    }
}
