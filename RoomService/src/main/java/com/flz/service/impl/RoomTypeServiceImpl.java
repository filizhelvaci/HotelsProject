package com.flz.service.impl;

import com.flz.model.entity.RoomTypeEntity;
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
    public List<RoomTypeEntity> findAll() {
        return List.of();
    }

    @Override
    public RoomTypeEntity findById(Long id) {
        return null;
    }

    @Override
    public void create(RoomTypeEntity roomTypeEntity) {

    }

    @Override
    public void update(Long id, RoomTypeEntity roomTypeEntity) {

    }

    @Override
    public void delete(Long id) {

    }
}
