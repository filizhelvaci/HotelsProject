package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.response.AssetsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper
public interface AssetEntityToPageResponseMapper extends BaseMapper<AssetEntity, AssetsResponse> {

    AssetEntityToPageResponseMapper INSTANCE = Mappers.getMapper(AssetEntityToPageResponseMapper.class);

    AssetsResponse map(AssetEntity assetEntity);

    default Page<AssetsResponse> map(Page<AssetEntity> assetEntities) {
        return assetEntities.map(this::map);
    }
}
