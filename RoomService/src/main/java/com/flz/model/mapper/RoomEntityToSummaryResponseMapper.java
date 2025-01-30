package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.response.RoomsSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomEntityToSummaryResponseMapper extends BaseMapper<RoomEntity, RoomsSummaryResponse> {

    RoomEntityToSummaryResponseMapper INSTANCE = Mappers.getMapper(RoomEntityToSummaryResponseMapper.class);
}
