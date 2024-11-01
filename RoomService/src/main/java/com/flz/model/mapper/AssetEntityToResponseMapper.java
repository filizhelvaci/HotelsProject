package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.response.AssetResponse;

import java.util.ArrayList;
import java.util.List;

public final class AssetEntityToResponseMapper {

    private AssetEntityToResponseMapper() {
    }

    public static AssetResponse map(AssetEntity assetEntity) {
        return AssetResponse.builder()
                .id(assetEntity.getId())
                .name(assetEntity.getName())
                .price(assetEntity.getPrice())
                .isDefault(assetEntity.getIsDefault())
                .build();
    }

    public static List<AssetResponse> map(List<AssetEntity> assetEntities) {
        List<AssetResponse> assetResponses = new ArrayList<>();
        for (AssetEntity assetEntity : assetEntities) {
            AssetResponse assetResponse = map(assetEntity);
            assetResponses.add(assetResponse);
        }
        return assetResponses;
    }
}
