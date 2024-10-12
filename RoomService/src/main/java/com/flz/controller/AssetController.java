package com.flz.controller;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;


    @GetMapping("/assets")
    public ResponseEntity<List<AssetEntity>> findAll() {
        final List<AssetEntity> entities = assetService.findAll();
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/asset/{id}")
    public ResponseEntity<AssetEntity> findById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        AssetEntity entity = assetService.findById(id);
        return ResponseEntity.ok(entity);
    }


    @PostMapping("/asset")
    public ResponseEntity<Void> create(@RequestBody AssetEntity assetEntity) {
        assetService.create(assetEntity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/asset/{id}")
    public ResponseEntity<Void> update(
            @PathVariable(value = "id") Long id,
            @RequestBody AssetEntity assetEntity) throws ResourceNotFoundException {

        assetService.update(id, assetEntity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/asset/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        assetService.delete(id);
        return ResponseEntity.ok().build();
    }


}
