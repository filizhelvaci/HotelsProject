package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.response.RoomsResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class RoomEntityToPageResponseMapper {

    private RoomEntityToPageResponseMapper() {
    }

    public static RoomsResponse map(RoomEntity entity) {

        RoomsResponse.RoomType roomType = RoomsResponse.RoomType.builder()
                .id(entity.getType().getId())
                .name(entity.getType().getName())
                .build();

        return RoomsResponse.builder()
                .id(entity.getId())
                .floor(entity.getFloor())
                .number(entity.getNumber())
                .status(entity.getStatus())
                .roomType(roomType)
                .build();
    }

    public static List<RoomsResponse> map(List<RoomEntity> entities) {
        List<RoomsResponse> responses = new ArrayList<>();
        for (RoomEntity entity : entities) {
            RoomsResponse roomsResponse = map(entity);
            responses.add(roomsResponse);
        }
        return responses;
    }

    public static Page<RoomsResponse> map(Page<RoomEntity> roomEntities) {
        return roomEntities.map(RoomEntityToPageResponseMapper::map);
    }
}
