package com.flz.controller;

import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.HotelResponse;
import com.flz.model.response.RoomTypeBasicResponse;
import com.flz.model.response.RoomTypeResponse;
import com.flz.service.RoomTypeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping("/room-types")
    public HotelResponse<List<RoomTypeBasicResponse>> findAll() {
        final List<RoomTypeBasicResponse> roomTypeBasicResponses = roomTypeService.findAll();
        return HotelResponse.successOf(roomTypeBasicResponses);
    }


    @GetMapping("/room-type/{id}")
    public HotelResponse<RoomTypeResponse> findById(@PathVariable(value = "id") Long id) {
        RoomTypeResponse roomTypeResponse = roomTypeService.findById(id);
        return HotelResponse.successOf(roomTypeResponse);
    }


    @PostMapping("/room-type")
    public HotelResponse<Void> create(@RequestBody @Valid RoomTypeCreateRequest roomTypeCreateRequest) {
        roomTypeService.create(roomTypeCreateRequest);
        return HotelResponse.success();
    }


    @PutMapping("/room-type/{id}")
    public HotelResponse<Void> update(@PathVariable(value = "id") @Positive Long id, @RequestBody @Valid RoomTypeUpdateRequest roomTypeUpdateRequest) {
        roomTypeService.update(id, roomTypeUpdateRequest);
        return HotelResponse.success();
    }


    @DeleteMapping("/room-type/{id}")
    public HotelResponse<Void> delete(@PathVariable(value = "id") Long id) {
        roomTypeService.delete(id);
        return HotelResponse.success();
    }
}
