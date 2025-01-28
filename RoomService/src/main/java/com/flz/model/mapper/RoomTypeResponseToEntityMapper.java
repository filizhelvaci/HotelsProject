package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeResponse;

public final class RoomTypeResponseToEntityMapper {

    private RoomTypeResponseToEntityMapper() {
    }

    public static RoomTypeEntity map(RoomTypeResponse roomTypeResponse) {

        return RoomTypeMapper.INSTANCE.toRoomTypeEntityFromResponse(roomTypeResponse);
    }

}
