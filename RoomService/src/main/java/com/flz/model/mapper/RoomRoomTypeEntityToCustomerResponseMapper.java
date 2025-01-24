package com.flz.model.mapper;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.response.CustomerResponse;
import com.flz.model.response.ForCustomerRoomResponse;

import java.util.ArrayList;
import java.util.List;

public final class RoomRoomTypeEntityToCustomerResponseMapper {

    private RoomRoomTypeEntityToCustomerResponseMapper() {
    }

    public static CustomerResponse map(RoomTypeEntity roomType, List<ForCustomerRoomResponse> rooms) {


        List<CustomerResponse.Room> roomResponses = new ArrayList<>();

        for (ForCustomerRoomResponse forCustomerRoomResponse : rooms) {
            CustomerResponse.Room room = CustomerResponse.Room.builder()
                    .id(forCustomerRoomResponse.getId())
                    .number(forCustomerRoomResponse.getNumber())
                    .status(forCustomerRoomResponse.getStatus())
                    .build();
            roomResponses.add(room);
        }

        return CustomerResponse.builder()
                .roomTypeId(roomType.getId())
                .roomTypeName(roomType.getName())
                .price(roomType.getPrice())
                .personCount(roomType.getPersonCount())
                .size(roomType.getSize())
                .rooms(roomResponses)
                .build();
    }
}
