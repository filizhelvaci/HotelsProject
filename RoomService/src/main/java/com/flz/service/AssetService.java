package com.flz.service;

import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsResponse;
import com.flz.model.response.AssetsSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

public interface AssetService {

    Page<AssetsResponse> findAll(String name, BigDecimal minPrice, BigDecimal maxPrice, Boolean isDefault, int page, int size, String property, Sort.Direction direction);

    List<AssetsSummaryResponse> findSummaryAll();

    List<AssetResponse> findAllById(List<Long> ids);

    AssetResponse findById(Long id);

    void create(AssetCreateRequest assetCreateRequest);

    void update(Long id, AssetUpdateRequest assetUpdateRequest);

    void delete(Long id);


}
