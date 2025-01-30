package com.flz.model.mapper;

import com.flz.model.entity.AssetEntity;
import com.flz.model.request.AssetCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssetCreateRequestToEntityMapper extends BaseMapper<AssetCreateRequest, AssetEntity> {

    AssetCreateRequestToEntityMapper INSTANCE = Mappers.getMapper(AssetCreateRequestToEntityMapper.class);

}
