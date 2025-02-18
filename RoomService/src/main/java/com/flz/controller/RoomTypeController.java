package com.flz.controller;

import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.HotelResponse;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomTypesResponse;
import com.flz.model.response.RoomTypesSummaryResponse;
import com.flz.service.RoomTypeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

import java.math.BigDecimal;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RoomTypeController {

    private final RoomTypeService roomTypeService;


    @GetMapping("/room-types")
    public HotelResponse<Page<RoomTypesResponse>> findAll(
            @RequestParam(required = false) @Size(max = 150) String name,
            @RequestParam(required = false) @Positive BigDecimal minPrice,
            @RequestParam(required = false) @Positive BigDecimal maxPrice,
            @RequestParam(required = false) @Positive @Max(100) Integer personCount,
            @RequestParam(required = false) @Positive @Max(1000) Integer size,
            @RequestParam @Min(0) int page,
            @RequestParam @Min(0) int pageSize,
            @RequestParam @NotBlank String property,
            @RequestParam Sort.Direction direction) {

        Page<RoomTypesResponse> roomTypesResponses = roomTypeService.findAll(name, minPrice, maxPrice, personCount, size, page, pageSize, property, direction);
        return HotelResponse.successOf(roomTypesResponses);
    }


    @GetMapping("/room-types/summary")
    public HotelResponse<List<RoomTypesSummaryResponse>> findSummaryAll() {
        final List<RoomTypesSummaryResponse> roomTypesSummaryResponses = roomTypeService.findSummaryAll();
        return HotelResponse.successOf(roomTypesSummaryResponses);
    }


    @GetMapping("/room-type/{id}")
    public HotelResponse<RoomTypeResponse> findById(@PathVariable(value = "id") @Positive Long id) {
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
    public HotelResponse<Void> delete(@PathVariable(value = "id") @Positive Long id) {
        roomTypeService.delete(id);
        return HotelResponse.success();
    }

}
