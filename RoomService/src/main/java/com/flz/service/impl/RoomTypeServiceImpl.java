package com.flz.service.impl;

import com.flz.exception.RoomTypeNotFoundException;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.mapper.RoomTypeCreateRequestToEntityMapper;
import com.flz.model.mapper.RoomTypeEntityToResponseMapper;
import com.flz.model.mapper.RoomTypeUpdateRequestToEntityMapper;
import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.RoomTypeResponse;
import com.flz.repository.RoomTypeRepository;
import com.flz.service.RoomTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public List<RoomTypeResponse> findAll() {
        List<RoomTypeEntity> roomTypeEntities = roomTypeRepository.findAll();
        return RoomTypeEntityToResponseMapper.map(roomTypeEntities);
    }

    @Override
    public RoomTypeResponse findById(Long id) {
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RoomTypeNotFoundException(id));
        return RoomTypeEntityToResponseMapper.map(roomTypeEntity);
    }

    @Override
    public void create(RoomTypeCreateRequest roomTypeCreateRequest) {
        RoomTypeEntity roomTypeEntity = RoomTypeCreateRequestToEntityMapper.map(roomTypeCreateRequest);
        roomTypeRepository.save(roomTypeEntity);
    }

    @Override
    public void update(Long id, RoomTypeUpdateRequest roomTypeUpdateRequest) {
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RoomTypeNotFoundException(id));
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
