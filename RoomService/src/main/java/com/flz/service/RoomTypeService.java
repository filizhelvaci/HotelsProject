package com.flz.service;

import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomTypesResponse;
import com.flz.model.response.RoomTypesSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

public interface RoomTypeService {

    Page<RoomTypesResponse> findAll(String name, BigDecimal minPrice, BigDecimal maxPrice, Integer personCount, Integer size, int page, int pageSize, String property, Sort.Direction direction);

    List<RoomTypesSummaryResponse> findSummaryAll();

    RoomTypeResponse findById(Long id);

    void create(RoomTypeCreateRequest roomTypeCreateRequest);

    void update(Long id, RoomTypeUpdateRequest roomTypeUpdateRequest);

    void delete(Long id);
}
