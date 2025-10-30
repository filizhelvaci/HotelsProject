package com.flz.controller;

import com.flz.model.response.HotelResponse;
import com.flz.model.response.RoomTypeAvailabilityResponse;
import com.flz.service.RoomTypeAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RoomTypeAvailabilityController {

    private final RoomTypeAvailabilityService roomTypeAvailabilityService;

    @GetMapping("/room-type-availabilities")
    public HotelResponse<List<RoomTypeAvailabilityResponse>> findRoomTypesAvailability() {
        List<RoomTypeAvailabilityResponse> responses = roomTypeAvailabilityService.findRoomTypesAvailability();
        return HotelResponse.successOf(responses);
    }
}
