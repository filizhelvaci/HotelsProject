package com.flz.service;

import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.RoomTypeBasicResponse;
import com.flz.model.response.RoomTypeWithAssetResponse;

import java.util.List;

public interface RoomTypeService {

    List<RoomTypeBasicResponse> findAll();

    RoomTypeWithAssetResponse findById(Long id);

    void create(RoomTypeCreateRequest roomTypeCreateRequest);

    void update(Long id, RoomTypeUpdateRequest roomTypeUpdateRequest);

    void delete(Long id);
}
