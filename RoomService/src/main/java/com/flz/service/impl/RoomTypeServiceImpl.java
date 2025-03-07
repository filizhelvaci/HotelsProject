package com.flz.service.impl;

import com.flz.exception.RoomTypeAlreadyExistsException;
import com.flz.exception.RoomTypeNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.mapper.RoomTypeCreateRequestToEntityMapper;
import com.flz.model.mapper.RoomTypeEntityToPageResponseMapper;
import com.flz.model.mapper.RoomTypeEntityToResponseMapper;
import com.flz.model.mapper.RoomTypeEntityToSummaryResponseMapper;
import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomTypesResponse;
import com.flz.model.response.RoomTypesSummaryResponse;
import com.flz.repository.AssetRepository;
import com.flz.repository.RoomTypeRepository;
import com.flz.service.RoomTypeService;
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
class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeEntityToSummaryResponseMapper roomTypeEntityToSummaryResponseMapper = RoomTypeEntityToSummaryResponseMapper.INSTANCE;
    private final RoomTypeEntityToPageResponseMapper roomTypeEntityToPageResponseMapper = RoomTypeEntityToPageResponseMapper.INSTANCE;
    private final RoomTypeCreateRequestToEntityMapper roomTypeCreateRequestToEntityMapper = RoomTypeCreateRequestToEntityMapper.INSTANCE;
    private final RoomTypeEntityToResponseMapper roomTypeEntityToResponseMapper = RoomTypeEntityToResponseMapper.INSTANCE;

    private final RoomTypeRepository roomTypeRepository;
    private final AssetRepository assetRepository;


    @Override
    public List<RoomTypesSummaryResponse> findSummaryAll() {

        List<RoomTypeEntity> roomTypeEntities = roomTypeRepository.findAll();
        return roomTypeEntityToSummaryResponseMapper.map(roomTypeEntities);
    }


    @Override
    public RoomTypeResponse findById(Long id) {

        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RoomTypeNotFoundException(id));
        return roomTypeEntityToResponseMapper.map(roomTypeEntity);
    }


    @Override
    public Page<RoomTypesResponse> findAll(String name, BigDecimal minPrice, BigDecimal maxPrice, Integer personCount, Integer size, int page, int pageSize, String property, Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.by(property).with(direction)));

        Specification<RoomTypeEntity> spec = RoomTypeEntity.generateSpecification(name, minPrice, maxPrice, personCount, size);

        Page<RoomTypeEntity> roomTypeEntities = roomTypeRepository.findAll(spec, pageable);

        return roomTypeEntityToPageResponseMapper.map(roomTypeEntities);
    }


    @Override
    public void create(RoomTypeCreateRequest roomTypeCreateRequest) {

        boolean existsByName = roomTypeRepository.existsByName(roomTypeCreateRequest.getName());
        if (existsByName) {
            throw new RoomTypeAlreadyExistsException(roomTypeCreateRequest.getName());
        }

        RoomTypeEntity roomTypeEntity = roomTypeCreateRequestToEntityMapper.map(roomTypeCreateRequest);
        List<AssetEntity> assets = assetRepository.findAllById(roomTypeCreateRequest.getAssetIds());
        roomTypeEntity.setAssets(assets);
        roomTypeRepository.save(roomTypeEntity);
    }


    @Override
    public void update(Long id, RoomTypeUpdateRequest roomTypeUpdateRequest) {

        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RoomTypeNotFoundException(id));
        List<AssetEntity> assets = assetRepository.findAllById(roomTypeUpdateRequest.getAssetIds());
        roomTypeEntity.setAssets(assets);

        roomTypeEntity.update(roomTypeUpdateRequest.getName(),
                roomTypeUpdateRequest.getPrice(),
                roomTypeUpdateRequest.getPersonCount(),
                roomTypeUpdateRequest.getSize(),
                roomTypeUpdateRequest.getDescription());

        roomTypeRepository.save(roomTypeEntity);
    }


    @Override
    public void delete(Long id) throws RoomTypeNotFoundException {

        boolean exists = roomTypeRepository.existsById(id);
        if (!exists) {
            throw new RoomTypeNotFoundException(id);
        }
        roomTypeRepository.deleteById(id);
    }
}
