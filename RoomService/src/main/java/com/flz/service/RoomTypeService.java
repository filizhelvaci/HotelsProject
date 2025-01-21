package com.flz.service;

import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomTypesResponse;
import com.flz.model.response.RoomTypesSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface RoomTypeService {

    List<RoomTypesSummaryResponse> findSummaryAll();

    RoomTypeResponse findById(Long id);

    Page<RoomTypesResponse> findAll(String name, BigDecimal minPrice, BigDecimal maxPrice, Integer personCount, Integer size, Pageable pageable);

    void create(RoomTypeCreateRequest roomTypeCreateRequest);

    void update(Long id, RoomTypeUpdateRequest roomTypeUpdateRequest);

    void delete(Long id);
}
