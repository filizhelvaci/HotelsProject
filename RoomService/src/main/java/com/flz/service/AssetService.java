package com.flz.service;

import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsResponse;
import com.flz.model.response.AssetsSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface AssetService {

    Page<AssetsResponse> getFilteredAssets(String name, BigDecimal minPrice, BigDecimal maxPrice, Boolean isDefault, Pageable pageable);

    List<AssetsSummaryResponse> findSummaryAll();

    List<AssetResponse> findAllById(List<Long> ids);

    AssetResponse findById(Long id);

    void create(AssetCreateRequest assetCreateRequest);

    void update(Long id, AssetUpdateRequest assetUpdateRequest);

    void delete(Long id);


}
