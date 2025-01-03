package com.flz.controller;

import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.service.AssetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }


    @GetMapping("/assets")
    public ResponseEntity<List<AssetResponse>> findAll() {
        final List<AssetResponse> assetResponses = assetService.findAll();
        return ResponseEntity.ok(assetResponses);
    }

    @GetMapping("/asset/{id}")
    public ResponseEntity<AssetResponse> findById(@PathVariable(value = "id") Long id) {
        AssetResponse assetResponse = assetService.findById(id);
        return ResponseEntity.ok(assetResponse);
    }

    @PostMapping("/asset")
    public ResponseEntity<Void> create(@RequestBody @Valid AssetCreateRequest createRequest) {
        assetService.create(createRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/asset/{id}")
    public ResponseEntity<Void> update(@PathVariable(value = "id") @Positive Long id, @RequestBody @Valid AssetUpdateRequest assetUpdateRequest) {
        assetService.update(id, assetUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/asset/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
        assetService.delete(id);
        return ResponseEntity.ok().build();
    }
}
