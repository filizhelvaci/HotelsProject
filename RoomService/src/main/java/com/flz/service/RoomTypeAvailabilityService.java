package com.flz.service;

import com.flz.model.response.RoomTypeAvailabilityResponse;

import java.util.List;

public interface RoomTypeAvailabilityService {

    List<RoomTypeAvailabilityResponse> findRoomTypesAvailability();

}
