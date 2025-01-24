package com.flz.service.impl;

import com.flz.model.entity.RoomEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.mapper.RoomRoomTypeEntityToCustomerResponseMapper;
import com.flz.model.response.CustomerResponse;
import com.flz.repository.RoomRepository;
import com.flz.repository.RoomTypeRepository;
import com.flz.service.CustomerRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class CustomerRoomServiceImpl implements CustomerRoomService {

    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<CustomerResponse> getRoomTypesWithRooms() {

        List<RoomTypeEntity> roomTypes = roomTypeRepository.findAll();

        return roomTypes.stream().map(roomType -> {

            List<RoomEntity> rooms = roomRepository.findByType_Id(roomType.getId());

            return RoomRoomTypeEntityToCustomerResponseMapper.map(roomType, rooms);
        }).toList();
    }


}
