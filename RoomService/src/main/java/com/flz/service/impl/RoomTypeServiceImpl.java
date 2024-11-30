package com.flz.service.impl;

import com.flz.exception.RoomTypeAlreadyExistsException;
import com.flz.exception.RoomTypeNotFoundException;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.mapper.AssetResponseToEntityMapper;
import com.flz.model.mapper.RoomTypeCreateRequestToEntityMapper;
import com.flz.model.mapper.RoomTypeEntityToBasicResponseMapper;
import com.flz.model.mapper.RoomTypeEntityToResponseMapper;
import com.flz.model.mapper.RoomTypeUpdateRequestToEntityMapper;
import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.RoomTypeBasicResponse;
import com.flz.model.response.RoomTypeResponse;
import com.flz.repository.RoomTypeRepository;
import com.flz.service.AssetService;
import com.flz.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    private final AssetService assetService;

    @Override
    public List<RoomTypeBasicResponse> findAll() {
        List<RoomTypeEntity> roomTypeEntities = roomTypeRepository.findAll();
        return RoomTypeEntityToBasicResponseMapper.map(roomTypeEntities);
    }

    @Override
    public RoomTypeResponse findById(Long id) {
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(id).orElseThrow(() -> new RoomTypeNotFoundException(id));
        return RoomTypeEntityToResponseMapper.map(roomTypeEntity);
    }

    @Override
    public void create(RoomTypeCreateRequest roomTypeCreateRequest) {
        if (roomTypeRepository.existsByName(roomTypeCreateRequest.getName()))
            throw new RoomTypeAlreadyExistsException(roomTypeCreateRequest.getName());
        RoomTypeEntity roomTypeEntity = RoomTypeCreateRequestToEntityMapper.map(roomTypeCreateRequest);
        List<AssetResponse> assets = assetService.findAllById(roomTypeCreateRequest.getAssetIds());
        roomTypeEntity.setAssets(AssetResponseToEntityMapper.map(assets));
        roomTypeRepository.save(roomTypeEntity);
    }

    @Override
    public void update(Long id, RoomTypeUpdateRequest roomTypeUpdateRequest) {
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(id).orElseThrow(() -> new RoomTypeNotFoundException(id));
        List<AssetResponse> assets = assetService.findAllById(roomTypeUpdateRequest.getAssetIds());
        roomTypeEntity.setAssets(AssetResponseToEntityMapper.map(assets));
        RoomTypeUpdateRequestToEntityMapper.map(roomTypeUpdateRequest, roomTypeEntity);
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
