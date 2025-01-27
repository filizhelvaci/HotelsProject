package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.request.AssetCreateRequest;

public final class AssetCreateRequestToEntityMapper {

    private AssetCreateRequestToEntityMapper() {
    }

    public static AssetEntity map(AssetCreateRequest createRequest) {
        return AssetMapper.INSTANCE.toAssetEntity(createRequest);
    }
}
