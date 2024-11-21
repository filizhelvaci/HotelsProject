package com.flz.service.impl;

import com.flz.exception.AssetNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.mapper.AssetCreateRequestToEntityMapper;
import com.flz.model.mapper.AssetEntityToResponseMapper;
import com.flz.model.mapper.AssetUpdateRequestToEntityMapper;
import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.repository.AssetRepository;
import com.flz.service.AssetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }


    @Override
    public List<AssetResponse> findAll() {
        List<AssetEntity> assetEntities = assetRepository.findAll();
        return AssetEntityToResponseMapper.map(assetEntities);
    }

    @Override
    public List<AssetResponse> findAllById(List<Long> ids) {
        List<AssetEntity> assetEntities = assetRepository.findAllById(ids);
        return AssetEntityToResponseMapper.map(assetEntities);
    }

    @Override
    public AssetResponse findById(Long id) {
        AssetEntity assetEntity = assetRepository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException(id));
        return AssetEntityToResponseMapper.map(assetEntity);
    }


    @Override
    public void create(AssetCreateRequest assetCreateRequest) {
        AssetEntity assetEntity = AssetCreateRequestToEntityMapper.map(assetCreateRequest);
        assetRepository.save(assetEntity);
    }


    @Override
    public void update(Long id, AssetUpdateRequest assetUpdateRequest) {
        AssetEntity assetEntity = assetRepository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException(id));
        AssetUpdateRequestToEntityMapper.map(assetUpdateRequest, assetEntity);
        assetRepository.save(assetEntity);
    }


    @Override
    public void delete(Long id) throws AssetNotFoundException {
        boolean exists = assetRepository.existsById(id);
        if (!exists) {
            throw new AssetNotFoundException(id);
        }
        assetRepository.deleteById(id);
    }

}
