package com.flz.controller;

import com.flz.model.enums.RoomStatus;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.HotelResponse;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.service.RoomService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
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

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
class RoomController {

    private final RoomService roomService;


    @GetMapping("/rooms")
    public HotelResponse<Page<RoomsResponse>> findAll(
            @RequestParam(required = false) @Positive @Max(10000) Integer number,
            @RequestParam(required = false) @Positive @Max(100) Integer floor,
            @RequestParam(required = false) RoomStatus status,
            @RequestParam(required = false) @Positive Long typeId,
            @RequestParam @Min(0) int page,
            @RequestParam @Min(0) int size,
            @RequestParam @NotBlank String property,
            @RequestParam Sort.Direction direction) {

        Page<RoomsResponse> roomsResponses = roomService.findAll(number, floor, status, typeId, page, size, property, direction);
        return HotelResponse.successOf(roomsResponses);
    }


    @GetMapping("/rooms/summary")
    public HotelResponse<List<RoomsSummaryResponse>> findSummaryAll() {
        final List<RoomsSummaryResponse> roomsSummaryResponse = roomService.findSummaryAll();
        return HotelResponse.successOf(roomsSummaryResponse);
    }


    @GetMapping("/room/{id}")
    public HotelResponse<RoomResponse> findById(@PathVariable(value = "id") @Positive Long id) {
        RoomResponse roomResponse = roomService.findById(id);
        return HotelResponse.successOf(roomResponse);
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
    public HotelResponse<Void> delete(@PathVariable(value = "id") @Positive Long id) {
        roomService.delete(id);
        return HotelResponse.success();
    }

}
