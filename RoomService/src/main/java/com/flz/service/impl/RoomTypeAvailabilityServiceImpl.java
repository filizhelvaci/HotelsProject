package com.flz.service.impl;

import com.flz.model.entity.RoomEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.enums.RoomStatus;
import com.flz.model.mapper.RoomTypeEntityToAvailabilityResponseMapper;
import com.flz.model.response.RoomTypeAvailabilityResponse;
import com.flz.repository.RoomRepository;
import com.flz.repository.RoomTypeRepository;
import com.flz.service.RoomTypeAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class RoomTypeAvailabilityServiceImpl implements RoomTypeAvailabilityService {

    private final RoomTypeRepository roomTypeRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<RoomTypeAvailabilityResponse> findRoomTypesAvailability() {

        List<RoomTypeEntity> roomTypeEntities = roomTypeRepository.findAll();
        List<RoomEntity> roomEntities = roomRepository.findAll();

        List<RoomTypeAvailabilityResponse> roomTypeAvailabilityResponses = new ArrayList<>();

        for (RoomTypeEntity roomType : roomTypeEntities) {

            boolean isAvailable = false;

            for (RoomEntity room : roomEntities) {

                if (!room.getType().getId().equals(roomType.getId())) {
                    continue;
                }

                if (RoomStatus.EMPTY != room.getStatus()) {
                    continue;
                }
                isAvailable = true;
            }

            RoomTypeAvailabilityResponse response = RoomTypeEntityToAvailabilityResponseMapper.INSTANCE.toAvailability(roomType, isAvailable);

            roomTypeAvailabilityResponses.add(response);
        }

        return roomTypeAvailabilityResponses;
    }

}
