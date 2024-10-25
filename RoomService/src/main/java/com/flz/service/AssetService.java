package com.flz.service;

import com.flz.exception.ResourceNotFoundException;
import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;

import java.util.List;

public interface AssetService {

    List<AssetResponse> findAll();

    AssetResponse findById(Long id) throws ResourceNotFoundException;

    void create(AssetCreateRequest assetCreateRequest);

    void update(Long id, AssetUpdateRequest assetUpdateRequest) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;
}
