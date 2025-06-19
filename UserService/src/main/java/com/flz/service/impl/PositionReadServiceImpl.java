package com.flz.service.impl;

import com.flz.model.Position;
import com.flz.model.mapper.PositionToPositionSummaryResponseMapper;
import com.flz.model.response.PositionSummaryResponse;
import com.flz.port.PositionReadPort;
import com.flz.service.PositionReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class PositionReadServiceImpl implements PositionReadService {

    private final PositionReadPort positionReadPort;

    private final PositionToPositionSummaryResponseMapper
            positionToPositionSummaryResponseMapper = PositionToPositionSummaryResponseMapper.INSTANCE;


    @Override
    public List<PositionSummaryResponse> findSummaryAll() {

        List<Position> positions = positionReadPort.findSummaryAll();
        return positionToPositionSummaryResponseMapper.map(positions);
    }


    @Override
    public List<Position> findAll(Integer page, Integer pageSize) {

        return positionReadPort.findAll(page, pageSize);
    }


}
