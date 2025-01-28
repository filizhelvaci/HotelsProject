package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.response.AssetsSummaryResponse;

import java.util.ArrayList;
import java.util.List;

public final class AssetEntityToSummaryResponseMapper {

    private AssetEntityToSummaryResponseMapper() {
    }

    public static AssetsSummaryResponse map(AssetEntity assetEntity) {
        return AssetMapper.INSTANCE.toAssetsSummaryResponse(assetEntity);
    }

    public static List<AssetsSummaryResponse> map(List<AssetEntity> assetEntities) {
        List<AssetsSummaryResponse> assetsSummaryResponses = new ArrayList<>();
        for (AssetEntity assetEntity : assetEntities) {
            AssetsSummaryResponse assetSummaryResponse = map(assetEntity);
            assetsSummaryResponses.add(assetSummaryResponse);
        }
        return assetsSummaryResponses;
    }
}
