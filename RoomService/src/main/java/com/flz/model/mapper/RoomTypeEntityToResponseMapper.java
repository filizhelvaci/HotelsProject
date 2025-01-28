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

        List<RoomTypeResponse.Asset> assets = new ArrayList<>();

        for (AssetEntity assetEntity : roomTypeEntity.getAssets()) {
            RoomTypeResponse.Asset asset = RoomTypeResponse.Asset.builder()
                    .id(assetEntity.getId())
                    .name(assetEntity.getName())
                    .build();
            assets.add(asset);
        }

        RoomTypeResponse roomTypeResponse = RoomTypeMapper.INSTANCE.toRoomTypeResponse(roomTypeEntity);
        roomTypeResponse.setAssets(assets);
        return roomTypeResponse;
    }
}
