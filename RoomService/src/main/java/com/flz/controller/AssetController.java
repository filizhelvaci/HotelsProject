package com.flz.controller;

import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsResponse;
import com.flz.model.response.AssetsSummaryResponse;
import com.flz.model.response.HotelResponse;
import com.flz.service.AssetService;
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

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
class AssetController {

    private final AssetService assetService;

    @GetMapping("/assets")
    public HotelResponse<Page<AssetsResponse>> findAll(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) BigDecimal minPrice,
                                                       @RequestParam(required = false) BigDecimal maxPrice,
                                                       @RequestParam(required = false) Boolean isDefault,
                                                       @RequestParam int page,
                                                       @RequestParam int size,
                                                       @RequestParam String property,
                                                       @RequestParam Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(property).with(direction)));
        Page<AssetsResponse> assetsResponses = assetService.findAll(name, minPrice, maxPrice, isDefault, pageable);
        return HotelResponse.successOf(assetsResponses);
    }

    @GetMapping("/assets/summary")
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
