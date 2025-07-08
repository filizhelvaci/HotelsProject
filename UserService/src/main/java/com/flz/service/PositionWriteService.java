package com.flz.service;

import com.flz.model.request.PositionCreateRequest;
import com.flz.model.request.PositionUpdateRequest;

public interface PositionWriteService {

    void create(PositionCreateRequest positionCreateRequest);

    void update(Long id, PositionUpdateRequest positionUpdateRequest);

    void delete(Long id);

}
