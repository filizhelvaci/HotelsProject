package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.request.AssetCreateRequest;

import java.time.LocalDateTime;

public final class AssetCreateRequestToEntityMapper {

    private AssetCreateRequestToEntityMapper() {
    }

    public static AssetEntity map(AssetCreateRequest createRequest) {
        return AssetEntity.builder()
                .name(createRequest.getName())
                .price(createRequest.getPrice())
                .isDefault(createRequest.getIsDefault())
                .createdAt(LocalDateTime.now())
                .createdBy("system")
                .build();
    }
}
