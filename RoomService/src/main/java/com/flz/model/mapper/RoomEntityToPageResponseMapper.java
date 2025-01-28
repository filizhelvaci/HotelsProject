package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.response.RoomsResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public final class RoomEntityToPageResponseMapper {

    private RoomEntityToPageResponseMapper() {
    }

    public static RoomsResponse map(RoomEntity roomEntity) {

        RoomsResponse.RoomType roomType = RoomsResponse.RoomType.builder()
                .id(roomEntity.getType().getId())
                .name(roomEntity.getType().getName())
                .build();

        RoomsResponse roomsResponse = RoomMapper.INSTANCE.toRoomsResponse(roomEntity);
        roomsResponse.setRoomType(roomType);
        return roomsResponse;

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
