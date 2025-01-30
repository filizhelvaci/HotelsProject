package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.response.AssetsSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssetEntityToSummaryResponseMapper extends BaseMapper<AssetEntity, AssetsSummaryResponse> {

    AssetEntityToSummaryResponseMapper INSTANCE = Mappers.getMapper(AssetEntityToSummaryResponseMapper.class);

}
