package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypesResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomTypeEntityToBasicResponseMapper {

    private RoomTypeEntityToBasicResponseMapper() {
    }

    public static RoomTypesResponse map(RoomTypeEntity roomTypeEntity) {
        return RoomTypeMapper.INSTANCE.toRoomTypesResponse(roomTypeEntity);
    }

    public static List<RoomTypesResponse> map(List<RoomTypeEntity> roomTypeEntities) {
        List<RoomTypesResponse> roomTypeResponse = new ArrayList<>();
        for (RoomTypeEntity roomTypeEntity : roomTypeEntities) {
            RoomTypesResponse roomTypesResponse = map(roomTypeEntity);
            roomTypeResponse.add(roomTypesResponse);
        }
        return roomTypeResponse;
    }
}
