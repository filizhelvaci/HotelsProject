package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypesSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomTypeEntityToSummaryResponseMapper extends BaseMapper<RoomTypeEntity, RoomTypesSummaryResponse> {

    RoomTypeEntityToSummaryResponseMapper INSTANCE = Mappers.getMapper(RoomTypeEntityToSummaryResponseMapper.class);

}
