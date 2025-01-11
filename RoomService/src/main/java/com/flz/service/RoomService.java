package com.flz.service;

import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsSummaryResponse;

import java.util.List;

public interface RoomService {

    List<RoomsSummaryResponse> findSummaryAll();

    RoomResponse findById(Long id);

    void create(RoomCreateRequest roomCreateRequest);

    void update(Long id, RoomUpdateRequest roomUpdateRequest);

    void delete(Long id);

}
