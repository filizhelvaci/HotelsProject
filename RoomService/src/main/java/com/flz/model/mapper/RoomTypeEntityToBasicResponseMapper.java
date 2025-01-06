package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypesResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomTypeEntityToBasicResponseMapper {

    private RoomTypeEntityToBasicResponseMapper() {
    }

    public static RoomTypesResponse map(RoomTypeEntity roomTypeEntity) {
        return RoomTypesResponse.builder()
                .id(roomTypeEntity.getId())
                .name(roomTypeEntity.getName())
                .price(roomTypeEntity.getPrice())
                .personCount(roomTypeEntity.getPersonCount())
                .size(roomTypeEntity.getSize())
                .build();
    }

    public static List<RoomTypesResponse> map(List<RoomTypeEntity> roomTypeEntities) {
        List<RoomTypesResponse> roomTypeRespons = new ArrayList<>();
        for (RoomTypeEntity roomTypeEntity : roomTypeEntities) {
            RoomTypesResponse roomTypesResponse = map(roomTypeEntity);
            roomTypeRespons.add(roomTypesResponse);
        }
        return roomTypeRespons;
    }
}
