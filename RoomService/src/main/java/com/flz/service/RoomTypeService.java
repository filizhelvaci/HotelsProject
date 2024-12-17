package com.flz.service;

import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.RoomTypeBasicResponse;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomTypesSummaryResponse;

import java.util.List;

public interface RoomTypeService {

    List<RoomTypeBasicResponse> findAll();

    List<RoomTypesSummaryResponse> findSummaryAll();

    RoomTypeResponse findById(Long id);

    void create(RoomTypeCreateRequest roomTypeCreateRequest);

    void update(Long id, RoomTypeUpdateRequest roomTypeUpdateRequest);

    void delete(Long id);
}
