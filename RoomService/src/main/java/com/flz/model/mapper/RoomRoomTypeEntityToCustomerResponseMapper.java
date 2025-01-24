package com.flz.model.mapper;

import com.flz.model.entity.RoomEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.CustomerResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomRoomTypeEntityToCustomerResponseMapper {

    private RoomRoomTypeEntityToCustomerResponseMapper() {
    }

    public static CustomerResponse map(RoomTypeEntity roomTypeEntity, List<RoomEntity> rooms) {


        List<CustomerResponse.Room> roomResponses = new ArrayList<>();

        for (RoomEntity roomEntity : rooms) {
            CustomerResponse.Room room = CustomerResponse.Room.builder()
                    .id(roomEntity.getId())
                    .number(roomEntity.getNumber())
                    .status(roomEntity.getStatus())
                    .build();
            roomResponses.add(room);
        }

        return CustomerResponse.builder()
                .roomTypeId(roomTypeEntity.getId())
                .roomTypeName(roomTypeEntity.getName())
                .price(roomTypeEntity.getPrice())
                .personCount(roomTypeEntity.getPersonCount())
                .size(roomTypeEntity.getSize())
                .rooms(roomResponses)
                .build();
    }
}
