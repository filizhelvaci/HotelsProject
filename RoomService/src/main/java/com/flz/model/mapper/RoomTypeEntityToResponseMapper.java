package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeResponse;

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
                .assets(roomTypeEntity.getAssets().stream().map(AssetEntity::getName).toList())
                .build();
    }

}
