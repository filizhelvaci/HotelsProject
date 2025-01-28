package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.request.AssetCreateRequest;

import java.time.LocalDateTime;

public final class AssetCreateRequestToEntityMapper {

    private AssetCreateRequestToEntityMapper() {
    }

    public static AssetEntity map(AssetCreateRequest assetcreateRequest) {
        AssetEntity assetEntity = AssetMapper.INSTANCE.toAssetEntity(assetcreateRequest);
        assetEntity.setCreatedAt(LocalDateTime.now());
        assetEntity.setCreatedBy("admin");
        return assetEntity;
    }
}
