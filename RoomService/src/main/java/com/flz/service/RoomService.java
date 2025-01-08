package com.flz.service;

import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsResponse;
import com.flz.model.response.RoomsSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoomService {
    Page<RoomsResponse> findAll(Integer number,
                                Integer minNumber,
                                Integer maxNumber,
                                int page,
                                int size,
                                String sortBy,
                                String sortDirection);

    List<RoomsSummaryResponse> findSummaryAll();

    RoomResponse findById(Long id);

    void create(RoomCreateRequest roomCreateRequest);

    void update(Long id, RoomUpdateRequest roomUpdateRequest);

    void delete(Long id);

}
