package com.flz.service.impl;

import com.flz.exception.AssetAlreadyExistsException;
import com.flz.exception.AssetNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.mapper.AssetCreateRequestToEntityMapper;
import com.flz.model.mapper.AssetEntityToResponseMapper;
import com.flz.model.mapper.AssetEntityToSummaryResponseMapper;
import com.flz.model.mapper.AssetUpdateRequestToEntityMapper;
import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsSummaryResponse;
import com.flz.repository.AssetRepository;
import com.flz.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    @Override
    public List<AssetResponse> findAll() {
        List<AssetEntity> assetEntities = assetRepository.findAll();
        return AssetEntityToResponseMapper.map(assetEntities);
    }

    @Override
    public List<AssetsSummaryResponse> findSummaryAll() {
        List<AssetEntity> assetEntities = assetRepository.findAll();
        return AssetEntityToSummaryResponseMapper.map(assetEntities);
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
    public Page<AssetResponse> findAllByName(String name, int offset, int pageSize) {
        Pageable pageable = PageRequest.of(offset, pageSize);
        Page<AssetEntity> assetEntityPage = assetRepository.findByNameContaining(name, pageable);
        return assetEntityPage.map(AssetEntityToResponseMapper::map);
    }

    @Override
    public void create(AssetCreateRequest assetCreateRequest) {
        if (Boolean.TRUE.equals(assetRepository.existsByName(assetCreateRequest.getName())))
            throw new AssetAlreadyExistsException(assetCreateRequest.getName());
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
