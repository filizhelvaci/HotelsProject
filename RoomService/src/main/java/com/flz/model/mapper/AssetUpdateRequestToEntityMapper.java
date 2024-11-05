package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.request.AssetUpdateRequest;

import java.time.LocalDateTime;

public final class AssetUpdateRequestToEntityMapper {

    private AssetUpdateRequestToEntityMapper() {
    }

    public static void map(AssetUpdateRequest assetUpdateRequest, AssetEntity assetEntity) {
        assetEntity.setName(assetUpdateRequest.getName());
        assetEntity.setPrice(assetUpdateRequest.getPrice());
        assetEntity.setIsDefault(assetUpdateRequest.getIsDefault());
        assetEntity.setUpdatedAt(LocalDateTime.now());
        assetEntity.setUpdatedBy("system");
    }
}
