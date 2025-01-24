package com.flz.controller;

import com.flz.model.response.CustomerResponse;
import com.flz.model.response.HotelResponse;
import com.flz.service.CustomerRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CustomerRoomController {

    private final CustomerRoomService customerRoomService;

    @GetMapping("/ourRooms")
    public HotelResponse<List<CustomerResponse>> getRoomTypesWithRooms() {
        List<CustomerResponse> responses = customerRoomService.getRoomTypesWithRooms();
        return HotelResponse.successOf(responses);
    }
}
