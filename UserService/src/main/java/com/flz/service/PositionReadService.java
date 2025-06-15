package com.flz.service;

import com.flz.model.Position;
import com.flz.model.response.PositionSummaryResponse;

import java.util.List;

public interface PositionReadService {

    List<Position> findAll(Integer page, Integer size);

    List<PositionSummaryResponse> findSummaryAll();

}
