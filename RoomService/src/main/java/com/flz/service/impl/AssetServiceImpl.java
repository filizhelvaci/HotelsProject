package com.flz.service.impl;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.repository.AssetRepository;
import com.flz.service.AssetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public List<AssetEntity> findAll() {
        return assetRepository.findAll();
    }

    @Override
    public AssetEntity findById(Long id) throws ResourceNotFoundException {
        AssetEntity assetEntity = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found ID: " + id));
        return assetEntity;
    }

    @Override
    public void create(AssetEntity assetEntity) {
        assetRepository.save(assetEntity);
    }

    @Override
    public void update(Long id, AssetEntity assetEntity) throws ResourceNotFoundException {

        AssetEntity assetEntityInfo = assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found ID: " + id));

        assetEntityInfo.setName(assetEntity.getName());
        assetEntityInfo.setPrice(assetEntity.getPrice());
        assetEntityInfo.setIsDefault(assetEntity.getIsDefault());

        assetRepository.save(assetEntityInfo);

    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        boolean exists = assetRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Asset not found ID:" + id);
        }
        assetRepository.deleteById(id);
    }


}
