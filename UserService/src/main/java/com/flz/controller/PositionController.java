package com.flz.controller;

import com.flz.model.Position;
import com.flz.model.request.PageRequest;
import com.flz.model.request.PositionCreateRequest;
import com.flz.model.request.PositionUpdateRequest;
import com.flz.model.response.HotelResponse;
import com.flz.model.response.PositionSummaryResponse;
import com.flz.service.PositionReadService;
import com.flz.service.PositionWriteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class PositionController {

    private final PositionWriteService positionWriteService;
    private final PositionReadService positionReadService;

    @GetMapping("/positions")
    public HotelResponse<List<Position>> findAll(@Valid PageRequest pageRequest) {
        List<Position> positions = positionReadService.findAll(pageRequest.getPage(), pageRequest.getPageSize());
        return HotelResponse.successOf(positions);
    }

    @GetMapping("/positions/summary")
    public HotelResponse<List<PositionSummaryResponse>> findSummaryAll() {
        List<PositionSummaryResponse> positionSummaryResponses = positionReadService.findSummaryAll();
        return HotelResponse.successOf(positionSummaryResponses);
    }

    @PostMapping("/position")
    public HotelResponse<Void> create(@RequestBody @Valid PositionCreateRequest createRequest) {
        positionWriteService.create(createRequest);
        return HotelResponse.success();
    }

    @PutMapping("/position/{id}")
    public HotelResponse<Void> update(@PathVariable(value = "id") @Positive Long id,
                                      @RequestBody @Valid PositionUpdateRequest positionUpdateRequest) {
        positionWriteService.update(id, positionUpdateRequest);
        return HotelResponse.success();
    }

    @DeleteMapping("/position/{id}")
    public HotelResponse<Void> delete(@PathVariable(value = "id") @Positive Long id) {
        positionWriteService.delete(id);
        return HotelResponse.success();
    }

}
