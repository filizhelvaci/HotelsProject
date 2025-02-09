package com.flz.service.impl;

import com.flz.exception.AssetAlreadyExistsException;
import com.flz.exception.AssetNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.mapper.AssetCreateRequestToEntityMapper;
import com.flz.model.mapper.AssetEntityToPageResponseMapper;
import com.flz.model.mapper.AssetEntityToResponseMapper;
import com.flz.model.mapper.AssetEntityToSummaryResponseMapper;
import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsResponse;
import com.flz.model.response.AssetsSummaryResponse;
import com.flz.repository.AssetRepository;
import com.flz.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
class AssetServiceImpl implements AssetService {

    private final AssetEntityToResponseMapper assetEntityToResponseMapper = AssetEntityToResponseMapper.INSTANCE;
    private final AssetEntityToSummaryResponseMapper assetEntityToSummaryResponseMapper = AssetEntityToSummaryResponseMapper.INSTANCE;
    private final AssetCreateRequestToEntityMapper assetCreateRequestToEntityMapper = AssetCreateRequestToEntityMapper.INSTANCE;
    private final AssetEntityToPageResponseMapper assetEntityToPageResponseMapper = AssetEntityToPageResponseMapper.INSTANCE;

    private final AssetRepository assetRepository;

    @Override
    public List<AssetsSummaryResponse> findSummaryAll() {

        List<AssetEntity> assetEntities = assetRepository.findAll();
        return assetEntityToSummaryResponseMapper.map(assetEntities);

    }

    @Override
    public List<AssetResponse> findAllById(List<Long> ids) {

        List<AssetEntity> assetEntities = assetRepository.findAllById(ids);
        return assetEntityToResponseMapper.map(assetEntities);

    }

    @Override
    public AssetResponse findById(Long id) {

        AssetEntity assetEntity = assetRepository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException(id));
        return assetEntityToResponseMapper.map(assetEntity);

    }

    @Override
    public Page<AssetsResponse> findAll(String name, BigDecimal minPrice, BigDecimal maxPrice, Boolean isDefault, int page, int size, String property, Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(property).with(direction)));

        Specification<AssetEntity> spec = AssetEntity.generateSpecification(name, minPrice, maxPrice, isDefault);

        Page<AssetEntity> assetEntities = assetRepository.findAll(spec, pageable);

        return assetEntityToPageResponseMapper.map(assetEntities);
    }

    @Override
    public void create(AssetCreateRequest assetCreateRequest) {

        boolean existsByName = assetRepository.existsByName(assetCreateRequest.getName());
        if (existsByName) {
            throw new AssetAlreadyExistsException(assetCreateRequest.getName());
        }
        AssetEntity assetEntity = assetCreateRequestToEntityMapper.map(assetCreateRequest);
        assetRepository.save(assetEntity);

    }


    @Override
    public void update(Long id, AssetUpdateRequest assetUpdateRequest) {

        AssetEntity assetEntity = assetRepository.findById(id)
                .orElseThrow(() -> new AssetNotFoundException(id));

        assetEntity.update(
                assetUpdateRequest.getName(),
                assetUpdateRequest.getPrice(),
                assetUpdateRequest.getIsDefault()
        );

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
