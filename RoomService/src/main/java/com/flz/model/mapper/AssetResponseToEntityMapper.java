package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.response.AssetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssetResponseToEntityMapper extends BaseMapper<AssetResponse, AssetEntity> {

    AssetResponseToEntityMapper INSTANCE = Mappers.getMapper(AssetResponseToEntityMapper.class);

}
