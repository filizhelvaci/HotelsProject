package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeWithAssetResponse;

public final class RoomTypeEntityWithAssetsToResponseMapper {

    private RoomTypeEntityWithAssetsToResponseMapper() {
    }

    public static RoomTypeWithAssetResponse map(RoomTypeEntity roomTypeEntity) {
        return RoomTypeWithAssetResponse.builder()
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
