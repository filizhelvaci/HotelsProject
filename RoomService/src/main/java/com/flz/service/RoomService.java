package com.flz.service;

import com.flz.model.enums.RoomStatus;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.ForCustomerRoomResponse;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsResponse;
import com.flz.model.response.RoomsSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {

    Page<RoomsResponse> findAll(Integer number, Integer floor, RoomStatus status, Long typeId, Pageable pageable);

    List<RoomsSummaryResponse> findSummaryAll();

    RoomResponse findById(Long id);

    List<ForCustomerRoomResponse> findRoomsByRoomTypeId(Long roomTypeId);

    void create(RoomCreateRequest roomCreateRequest);

    void update(Long id, RoomUpdateRequest roomUpdateRequest);

    void delete(Long id);

}
