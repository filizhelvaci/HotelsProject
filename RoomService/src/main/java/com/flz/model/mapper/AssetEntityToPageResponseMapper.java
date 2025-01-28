package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.response.AssetsResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public final class AssetEntityToPageResponseMapper {

    private AssetEntityToPageResponseMapper() {
    }

    public static AssetsResponse map(AssetEntity assetEntity) {
        return AssetMapper.INSTANCE.toAssetsResponse(assetEntity);
    }

    public static List<AssetsResponse> map(List<AssetEntity> assetEntities) {
        List<AssetsResponse> assetsResponses = new ArrayList<>();
        for (AssetEntity assetEntity : assetEntities) {
            AssetsResponse assetsResponse = map(assetEntity);
            assetsResponses.add(assetsResponse);
        }
        return assetsResponses;
    }

    public static Page<AssetsResponse> map(Page<AssetEntity> assetEntities) {
        return assetEntities.map(AssetEntityToPageResponseMapper::map);
    }
}
