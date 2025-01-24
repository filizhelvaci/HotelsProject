package com.flz.service.impl;

import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.mapper.RoomRoomTypeEntityToCustomerResponseMapper;
import com.flz.model.response.CustomerResponse;
import com.flz.model.response.ForCustomerRoomResponse;
import com.flz.service.CustomerRoomService;
import com.flz.service.RoomService;
import com.flz.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class CustomerRoomServiceImpl implements CustomerRoomService {

    private final RoomTypeService roomTypeService;
    private final RoomService roomService;

    @Override
    public List<CustomerResponse> getRoomTypesWithRooms() {

        List<RoomTypeEntity> roomTypeEntities = roomTypeService.findAll();
        List<CustomerResponse> customerResponses = new ArrayList<>();

        for (RoomTypeEntity roomType : roomTypeEntities) {
            List<ForCustomerRoomResponse> rooms = roomService.findRoomsByRoomTypeId(roomType.getId());
            CustomerResponse response = RoomRoomTypeEntityToCustomerResponseMapper.map(roomType, rooms);
            customerResponses.add(response);
        }

        return customerResponses;
    }


}
