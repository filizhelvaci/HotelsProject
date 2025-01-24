package com.flz.service;

import com.flz.model.response.CustomerResponse;

import java.util.List;

public interface CustomerRoomService {

    List<CustomerResponse> getRoomTypesWithRooms();

}
