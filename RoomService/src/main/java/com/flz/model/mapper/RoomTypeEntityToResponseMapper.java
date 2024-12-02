package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.AssetsSummaryResponse;
import com.flz.model.response.RoomTypeResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomTypeEntityToResponseMapper {

    private RoomTypeEntityToResponseMapper() {
    }

    public static RoomTypeResponse map(RoomTypeEntity roomTypeEntity) {

        List<AssetsSummaryResponse> assetList = new ArrayList<>();

        for (int i = 0; i < roomTypeEntity.getAssets().size(); i++) {
            AssetsSummaryResponse assetsSummaryResponse = AssetsSummaryResponse.builder()
                    .id(roomTypeEntity.getAssets().get(i).getId())
                    .name(roomTypeEntity.getAssets().get(i).getName())
                    .build();
            assetList.add(assetsSummaryResponse);
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
