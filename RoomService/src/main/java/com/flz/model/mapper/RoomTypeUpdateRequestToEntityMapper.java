package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.request.RoomTypeUpdateRequest;

import java.time.LocalDateTime;

public final class RoomTypeUpdateRequestToEntityMapper {

    private RoomTypeUpdateRequestToEntityMapper() {
    }

    public static void map(RoomTypeUpdateRequest roomTypeUpdateRequest, RoomTypeEntity roomTypeEntity) {
        roomTypeEntity.setName(roomTypeUpdateRequest.getName());
        roomTypeEntity.setPrice(roomTypeUpdateRequest.getPrice());
        roomTypeEntity.setPersonCount(roomTypeUpdateRequest.getPersonCount());
        roomTypeEntity.setSize(roomTypeUpdateRequest.getSize());
        roomTypeEntity.setDescription(roomTypeUpdateRequest.getDescription());
        roomTypeEntity.setUpdatedAt(LocalDateTime.now());
        roomTypeEntity.setUpdatedBy("system");

    }
}
