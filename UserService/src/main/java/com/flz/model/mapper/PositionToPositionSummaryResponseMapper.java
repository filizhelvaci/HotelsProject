package com.flz.model.mapper;

import com.flz.model.Position;
import com.flz.model.response.PositionSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PositionToPositionSummaryResponseMapper extends BaseMapper<Position, PositionSummaryResponse> {

    PositionToPositionSummaryResponseMapper INSTANCE = Mappers.getMapper(PositionToPositionSummaryResponseMapper.class);

}
