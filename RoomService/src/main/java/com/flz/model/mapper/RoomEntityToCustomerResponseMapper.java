package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.response.ForCustomerRoomResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomEntityToCustomerResponseMapper {

    private RoomEntityToCustomerResponseMapper() {
    }

    public static ForCustomerRoomResponse map(RoomEntity roomEntity) {

        return ForCustomerRoomResponse.builder()
                .id(roomEntity.getId())
                .number(roomEntity.getNumber())
                .status(roomEntity.getStatus())
                .build();

    }

    public static List<ForCustomerRoomResponse> map(List<RoomEntity> roomEntities) {
        List<ForCustomerRoomResponse> forCustomerRoomResponses = new ArrayList<>();
        for (RoomEntity roomEntity : roomEntities) {
            ForCustomerRoomResponse forCustomerRoomResponse = map(roomEntity);
            forCustomerRoomResponses.add(forCustomerRoomResponse);
        }
        return forCustomerRoomResponses;
    }
}
