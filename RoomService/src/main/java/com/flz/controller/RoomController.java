package com.flz.controller;

import com.flz.model.enums.RoomStatus;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.HotelResponse;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.service.RoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms/summary")
    public HotelResponse<List<RoomsSummaryResponse>> findSummaryAll() {
        final List<RoomsSummaryResponse> roomsSummaryResponse = roomService.findSummaryAll();
        return HotelResponse.successOf(roomsSummaryResponse);
    }

    @GetMapping("/room/{id}")
    public HotelResponse<RoomResponse> findById(@PathVariable(value = "id") Long id) {
        RoomResponse roomResponse = roomService.findById(id);
        return HotelResponse.successOf(roomResponse);
    }

    @PostMapping("/rooms")
    public HotelResponse<Page<RoomResponse>> findAll(
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) Integer floor,
            @RequestParam(required = false) RoomStatus status,
            @RequestParam(required = false) Long typeId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String property,
            @RequestParam Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(property).with(direction)));
        Page<RoomResponse> roomsResponses = roomService.getFilteredRooms(number, floor, status, typeId, pageable);
        return HotelResponse.successOf(roomsResponses);
    }

    @PostMapping("/room")
    public HotelResponse<Void> create(@RequestBody @Valid RoomCreateRequest createRequest) {
        roomService.create(createRequest);
        return HotelResponse.success();
    }

    @PutMapping("/room/{id}")
    public HotelResponse<Void> update(@PathVariable(value = "id") @Positive Long id, @RequestBody @Valid RoomUpdateRequest roomUpdateRequest) {
        roomService.update(id, roomUpdateRequest);
        return HotelResponse.success();
    }

    @DeleteMapping("/room/{id}")
    public HotelResponse<Void> delete(@PathVariable(value = "id") Long id) {
        roomService.delete(id);
        return HotelResponse.success();
    }
}
