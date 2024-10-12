package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.entity.AssetEntity;

import java.util.List;

public interface AssetService {

    List<AssetEntity> findAll();

    AssetEntity findById(Long id) throws ResourceNotFoundException;

    void create(AssetEntity assetEntity);

    void update(Long id, AssetEntity assetEntity) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
