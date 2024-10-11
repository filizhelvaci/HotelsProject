package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    //    http://localhost:8082/api/v1/asset/{id}
    @GetMapping("/asset/{id}")
    public ResponseEntity<AssetEntity> asset(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(assetService.getOneAsset(id).getBody());
    }

    // http://localhost:8082/api/v1/assets
    @GetMapping("/assets")
    public List<AssetEntity> getAllAssets() {
        return assetService.getAllAssets();
    }

    // http://localhost:8082/api/v1/asset
    @PostMapping("/asset")
    public AssetEntity createAsset(@RequestBody AssetEntity assetEntity) {
        return assetService.createAsset(assetEntity);
    }

    // http://localhost:8082/api/v1/asset/{id}
    @DeleteMapping("/asset/{id}")
    public Map<String, Boolean> deleteOneAsset(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        return assetService.deleteOneAsset(id);
    }

    // http://localhost:8082/api/v1/asset/1
    @PutMapping("/asset/{id}")
    public ResponseEntity<AssetEntity> updateOneAsset(
            @PathVariable(value = "id") Long id,
            @RequestBody AssetEntity assetEntity) throws ResourceNotFoundException {
        assetEntity.setId(id);
        System.out.println(assetEntity.toString());
        return assetService.updateOneAsset(assetEntity);
    }
}
