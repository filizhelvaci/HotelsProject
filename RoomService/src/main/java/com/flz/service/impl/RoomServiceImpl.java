package com.flz.service.impl;

import com.flz.exception.RoomAlreadyExistsException;
import com.flz.exception.RoomNotFoundException;
import com.flz.model.entity.RoomEntity;
import com.flz.model.mapper.RoomCreateRequestToEntityMapper;
import com.flz.model.mapper.RoomEntityToResponseMapper;
import com.flz.model.mapper.RoomEntityToSummaryResponseMapper;
import com.flz.model.mapper.RoomTypeResponseToEntityMapper;
import com.flz.model.mapper.RoomUpdateRequestToEntityMapper;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.repository.RoomRepository;
import com.flz.service.RoomService;
import com.flz.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeService roomTypeService;

    @Override
    public List<RoomsSummaryResponse> findSummaryAll() {

        List<RoomEntity> roomEntities = roomRepository.findAll();
        return RoomEntityToSummaryResponseMapper.map(roomEntities);

    }

    @Override
    public RoomResponse findById(Long id) {

        RoomEntity roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        return RoomEntityToResponseMapper.map(roomEntity);

    }

    @Override
    public void create(RoomCreateRequest roomCreateRequest) {

        boolean existsByNumber = roomRepository.existsByNumber(roomCreateRequest.getNumber());
        if (existsByNumber) {
            throw new RoomAlreadyExistsException(roomCreateRequest.getNumber().toString());
        }
        RoomEntity roomEntity = RoomCreateRequestToEntityMapper.map(roomCreateRequest);
        RoomTypeResponse roomTypeResponse = roomTypeService.findById(roomCreateRequest.getRoomTypeId());
        roomEntity.setType(RoomTypeResponseToEntityMapper.map(roomTypeResponse));
        roomRepository.save(roomEntity);

    }

    @Override
    public void update(Long id, RoomUpdateRequest roomUpdateRequest) {

        RoomEntity roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));

        RoomTypeResponse roomTypeResponse = roomTypeService.findById(roomUpdateRequest.getRoomTypeId());
        roomEntity.setType(RoomTypeResponseToEntityMapper.map(roomTypeResponse));
        RoomUpdateRequestToEntityMapper.map(roomUpdateRequest, roomEntity);
        roomRepository.save(roomEntity);

    }

    @Override
    public void delete(Long id) throws RoomNotFoundException {

        boolean exists = roomRepository.existsById(id);
        if (!exists) {
            throw new RoomNotFoundException(id);
        }
        roomRepository.deleteById(id);

    }


}
