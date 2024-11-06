package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeBasicResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomTypeEntityToBasicResponseMapper {

    private RoomTypeEntityToBasicResponseMapper() {
    }

    public static RoomTypeBasicResponse map(RoomTypeEntity roomTypeEntity) {
        return RoomTypeBasicResponse.builder()
                .id(roomTypeEntity.getId())
                .name(roomTypeEntity.getName())
                .price(roomTypeEntity.getPrice())
                .personCount(roomTypeEntity.getPersonCount())
                .size(roomTypeEntity.getSize())
                .build();
    }

    public static List<RoomTypeBasicResponse> map(List<RoomTypeEntity> roomTypeEntities) {
        List<RoomTypeBasicResponse> roomTypeBasicResponses = new ArrayList<>();
        for (RoomTypeEntity roomTypeEntity : roomTypeEntities) {
            RoomTypeBasicResponse roomTypeBasicResponse = map(roomTypeEntity);
            roomTypeBasicResponses.add(roomTypeBasicResponse);
        }
        return roomTypeBasicResponses;
    }
}
