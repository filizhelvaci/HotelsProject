package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypesResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomTypeEntityToResponseMapper {

    private RoomTypeEntityToResponseMapper() {
    }

    public static RoomTypesResponse map(RoomTypeEntity roomTypeEntity) {

        List<RoomTypesResponse.Asset> assetList = new ArrayList<>();

        for (AssetEntity assetEntity : roomTypeEntity.getAssets()) {
            RoomTypesResponse.Asset asset = RoomTypesResponse.Asset.builder()
                    .id(assetEntity.getId())
                    .name(assetEntity.getName())
                    .build();
            assetList.add(asset);
        }

        return RoomTypesResponse.builder()
                .id(roomTypeEntity.getId())
                .name(roomTypeEntity.getName())
                .price(roomTypeEntity.getPrice())
                .personCount(roomTypeEntity.getPersonCount())
                .size(roomTypeEntity.getSize())
                .description(roomTypeEntity.getDescription())
                .assets(assetList)
                .build();
    }

}
