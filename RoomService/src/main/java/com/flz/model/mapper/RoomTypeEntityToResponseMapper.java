package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypeResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomTypeEntityToResponseMapper {

    private RoomTypeEntityToResponseMapper() {
    }

    public static RoomTypeResponse map(RoomTypeEntity roomTypeEntity) {

        List<RoomTypeResponse.Asset> assetList = new ArrayList<>();

        for (AssetEntity assetEntity : roomTypeEntity.getAssets()) {
            RoomTypeResponse.Asset asset = RoomTypeResponse.Asset.builder()
                    .id(assetEntity.getId())
                    .name(assetEntity.getName())
                    .build();
            assetList.add(asset);
        }

        return RoomTypeResponse.builder()
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
