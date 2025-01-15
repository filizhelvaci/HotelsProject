package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.response.RoomResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class RoomEntityToPageResponseMapper {

    private RoomEntityToPageResponseMapper() {
    }

    public static RoomResponse map(RoomEntity entity) {

        RoomResponse.RoomType roomType = RoomResponse.RoomType.builder()
                .id(entity.getType().getId())
                .name(entity.getType().getName())
                .build();

        return RoomResponse.builder()
                .id(entity.getId())
                .floor(entity.getFloor())
                .number(entity.getNumber())
                .status(entity.getStatus())
                .roomType(roomType)
                .build();
    }

    public static List<RoomResponse> map(List<RoomEntity> entities) {
        List<RoomResponse> responses = new ArrayList<>();
        for (RoomEntity entity : entities) {
            RoomResponse roomResponse = map(entity);
            responses.add(roomResponse);
        }
        return responses;
    }

    public static Page<RoomResponse> map(Page<RoomEntity> roomEntities) {
        return roomEntities.map(RoomEntityToPageResponseMapper::map);
    }
}
