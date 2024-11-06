package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomTypeEntityToResponseMapper {

    private RoomTypeEntityToResponseMapper() {
    }

    public static RoomTypeResponse map(RoomTypeEntity roomTypeEntity) {
        return RoomTypeResponse.builder()
                .id(roomTypeEntity.getId())
                .name(roomTypeEntity.getName())
                .price(roomTypeEntity.getPrice())
                .personCount(roomTypeEntity.getPersonCount())
                .size(roomTypeEntity.getSize())
                .description(roomTypeEntity.getDescription())
                .build();
    }

    public static List<RoomTypeResponse> map(List<RoomTypeEntity> roomTypeEntities) {
        List<RoomTypeResponse> roomTypeResponses = new ArrayList<>();
        for (RoomTypeEntity roomTypeEntity : roomTypeEntities) {
            RoomTypeResponse roomTypeResponse = map(roomTypeEntity);
            roomTypeResponses.add(roomTypeResponse);
        }
        return roomTypeResponses;
    }

}
