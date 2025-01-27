package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypesSummaryResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomTypeEntityToSummaryResponseMapper {

    private RoomTypeEntityToSummaryResponseMapper() {
    }

    public static RoomTypesSummaryResponse map(RoomTypeEntity roomTypeEntity) {
        return RoomTypeMapper.INSTANCE.toRoomTypesSummaryResponse(roomTypeEntity);
    }

    public static List<RoomTypesSummaryResponse> map(List<RoomTypeEntity> roomTypeEntities) {
        List<RoomTypesSummaryResponse> roomTypesSummaryResponses = new ArrayList<>();
        for (RoomTypeEntity roomTypeEntity : roomTypeEntities) {
            RoomTypesSummaryResponse roomTypesSummaryResponse = map(roomTypeEntity);
            roomTypesSummaryResponses.add(roomTypesSummaryResponse);
        }
        return roomTypesSummaryResponses;
    }
}
