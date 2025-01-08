package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.response.RoomResponse;

import java.util.ArrayList;
import java.util.List;

public class RoomEntityToResponseMapper {

    private RoomEntityToResponseMapper() {
    }

    public static RoomResponse map(RoomEntity roomEntity) {
        return RoomResponse.builder()
                .id(roomEntity.getId())
                .number(roomEntity.getNumber())
                .floor(roomEntity.getFloor())
                .status(roomEntity.getStatus())
                .build();
    }

    public static List<RoomResponse> map(List<RoomEntity> roomEntities) {
        List<RoomResponse> roomResponses = new ArrayList<>();
        for (RoomEntity roomEntity : roomEntities) {
            RoomResponse roomResponse = map(roomEntity);
            roomResponses.add(roomResponse);
        }
        return roomResponses;
    }
}
