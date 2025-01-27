package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.RoomTypesResponse;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public final class RoomTypeEntityToPageResponseMapper {

    private RoomTypeEntityToPageResponseMapper() {
    }


    public static RoomTypesResponse map(RoomTypeEntity roomTypeEntity) {

        return RoomTypeMapper.INSTANCE.toRoomTypesResponse(roomTypeEntity);
    }

    public static List<RoomTypesResponse> map(List<RoomTypeEntity> roomTypeEntities) {
        List<RoomTypesResponse> roomTypesResponse = new ArrayList<>();
        for (RoomTypeEntity roomTypeEntity : roomTypeEntities) {
            RoomTypesResponse roomTypeResponse = map(roomTypeEntity);
            roomTypesResponse.add(roomTypeResponse);
        }
        return roomTypesResponse;
    }

    public static Page<RoomTypesResponse> map(Page<RoomTypeEntity> roomTypeEntities) {
        return roomTypeEntities.map(RoomTypeEntityToPageResponseMapper::map);
    }
}
