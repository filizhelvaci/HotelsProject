package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.repository.IAssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssetService {

    private final IAssetRepository IassetRepository;

    public AssetService(IAssetRepository IassetRepository) {
        this.IassetRepository = IassetRepository;
    }

    public List<AssetEntity> getAllAssets() {
        return IassetRepository.findAll();
    }

    public ResponseEntity<AssetEntity> getOneAsset(Long id) throws ResourceNotFoundException {
        AssetEntity assetEntity = IassetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found ID: " + id));
        return ResponseEntity.ok().body(assetEntity);
    }

    public AssetEntity createAsset(AssetEntity assetEntity) {
//        if (IassetRepository.findById(assetEntity.getId()).isPresent())
//            return null;
        return IassetRepository.save(assetEntity);
    }


    public Map<String, Boolean> deleteOneAsset(Long id) throws ResourceNotFoundException {
        AssetEntity assetEntity = IassetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found ID: " + id));
        IassetRepository.deleteById(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }

    public ResponseEntity<AssetEntity> updateOneAsset(AssetEntity assetEntity) throws ResourceNotFoundException {
        AssetEntity assetEntityInfo = IassetRepository.findById(assetEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found ID: " + assetEntity.getId()));
        return ResponseEntity.ok(IassetRepository.save(assetEntityInfo));
    }

}
