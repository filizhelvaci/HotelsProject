package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.response.AssetResponse;

import java.util.ArrayList;
import java.util.List;

public final class AssetResponseToEntityMapper {

    private AssetResponseToEntityMapper() {
    }

    public static AssetEntity map(AssetResponse assetResponse) {
        return AssetEntity.builder()
                .id(assetResponse.getId())
                .name(assetResponse.getName())
                .price(assetResponse.getPrice())
                .isDefault(assetResponse.getIsDefault())
                .build();
    }

    public static List<AssetEntity> map(List<AssetResponse> assetResponses) {
        List<AssetEntity> assetEntities = new ArrayList<>();
        for (AssetResponse assetResponse : assetResponses) {
            AssetEntity assetEntity = map(assetResponse);
            assetEntities.add(assetEntity);
        }
        return assetEntities;
    }
}
