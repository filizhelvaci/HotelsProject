package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.response.RoomsSummaryResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomEntityToSummaryResponseMapper {

    private RoomEntityToSummaryResponseMapper() {
    }

    public static RoomsSummaryResponse map(RoomEntity roomEntity) {
        return RoomMapper.INSTANCE.toRoomsSummaryResponse(roomEntity);
    }

    public static List<RoomsSummaryResponse> map(List<RoomEntity> roomEntities) {
        List<RoomsSummaryResponse> responses = new ArrayList<>();
        for (RoomEntity roomEntity : roomEntities) {
            RoomsSummaryResponse response = map(roomEntity);
            responses.add(response);
        }
        return responses;
    }
}
