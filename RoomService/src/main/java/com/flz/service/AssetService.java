package com.flz.service;

import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AssetService {

    List<AssetResponse> findAll();

    List<AssetsSummaryResponse> findSummaryAll();

    List<AssetResponse> findAllById(List<Long> ids);

    Page<AssetResponse> findAllByName(String name, int offset, int pageSize);

    AssetResponse findById(Long id);

    void create(AssetCreateRequest assetCreateRequest);

    void update(Long id, AssetUpdateRequest assetUpdateRequest);

    void delete(Long id);


}
