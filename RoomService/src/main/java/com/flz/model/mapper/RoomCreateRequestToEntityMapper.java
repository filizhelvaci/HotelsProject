package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.request.RoomCreateRequest;

import java.time.LocalDateTime;

public final class RoomCreateRequestToEntityMapper {

    private RoomCreateRequestToEntityMapper() {
    }

    public static RoomEntity map(RoomCreateRequest createRequest) {

        RoomEntity roomEntity = RoomMapper.INSTANCE.toRoomEntity(createRequest);
        roomEntity.setCreatedAt(LocalDateTime.now());
        roomEntity.setCreatedBy("admin");
        return roomEntity;
    }

}
