package com.flz.controller;

import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsSummaryResponse;
import com.flz.model.response.HotelResponse;
import com.flz.service.AssetService;
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
class AssetController {

    private final AssetService assetService;

    @GetMapping("/assets")
    public HotelResponse<List<AssetResponse>> findAll() {
        final List<AssetResponse> assetResponses = assetService.findAll();
        return HotelResponse.successOf(assetResponses);
    }

    @GetMapping("/asset")
    public HotelResponse<List<AssetsSummaryResponse>> findSummaryAll() {
        final List<AssetsSummaryResponse> assetsSummaryResponse = assetService.findSummaryAll();
        return HotelResponse.successOf(assetsSummaryResponse);
    }

    @GetMapping("/asset/{id}")
    public HotelResponse<AssetResponse> findById(@PathVariable(value = "id") Long id) {
        AssetResponse assetResponse = assetService.findById(id);
        return HotelResponse.successOf(assetResponse);
    }

    @PostMapping("/asset")
    public HotelResponse<Void> create(@RequestBody @Valid AssetCreateRequest createRequest) {
        assetService.create(createRequest);
        return HotelResponse.success();
    }

    @PutMapping("/asset/{id}")
    public HotelResponse<Void> update(@PathVariable(value = "id") @Positive Long id, @RequestBody @Valid AssetUpdateRequest assetUpdateRequest) {
        assetService.update(id, assetUpdateRequest);
        return HotelResponse.success();
    }

    @DeleteMapping("/asset/{id}")
    public HotelResponse<Void> delete(@PathVariable(value = "id") Long id) {
        assetService.delete(id);
        return HotelResponse.success();
    }
}
