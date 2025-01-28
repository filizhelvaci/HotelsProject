package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.request.AssetCreateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsResponse;
import com.flz.model.response.AssetsSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssetMapper {

    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    AssetEntity toAssetEntity(final AssetCreateRequest assetCreateRequest);

    AssetEntity toAssetEntityFromAssetResponse(final AssetResponse assetResponse);

    AssetResponse toAssetResponse(final AssetEntity assetEntity);

    AssetsSummaryResponse toAssetsSummaryResponse(final AssetEntity assetEntity);

    AssetsResponse toAssetsResponse(final AssetEntity assetEntity);

}
