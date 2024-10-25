package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.request.AssetCreateRequest;

import java.util.ArrayList;
import java.util.List;

public final class AssetCreateRequestToEntityMapper {

    private AssetCreateRequestToEntityMapper() {
    }

    public static AssetEntity map(AssetCreateRequest createRequest) {
        return AssetEntity.builder()
                .name(createRequest.getName())
                .price(createRequest.getPrice())
                .isDefault(createRequest.getIsDefault())
                .build();
    }

    public static List<AssetEntity> map(List<AssetCreateRequest> createRequests) {

        List<AssetEntity> assetEntities = new ArrayList<>();

        for (AssetCreateRequest createRequest : createRequests) {
            AssetEntity assetEntity = map(createRequest);
            assetEntities.add(assetEntity);
        }
        return assetEntities;
    }
}
