package com.flz.service.impl;

import com.flz.exception.RoomAlreadyExistsException;
import com.flz.exception.RoomNotFoundException;
import com.flz.exception.RoomTypeNotFoundException;
import com.flz.model.entity.RoomEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.enums.RoomStatus;
import com.flz.model.mapper.RoomCreateRequestToEntityMapper;
import com.flz.model.mapper.RoomEntityToPageResponseMapper;
import com.flz.model.mapper.RoomEntityToResponseMapper;
import com.flz.model.mapper.RoomEntityToSummaryResponseMapper;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.repository.RoomRepository;
import com.flz.repository.RoomTypeRepository;
import com.flz.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class RoomServiceImpl implements RoomService {

    private static final RoomEntityToResponseMapper roomEntityToResponseMapper = RoomEntityToResponseMapper.INSTANCE;
    private static final RoomEntityToSummaryResponseMapper roomEntityToSummaryResponseMapper = RoomEntityToSummaryResponseMapper.INSTANCE;
    private static final RoomCreateRequestToEntityMapper roomCreateRequestToEntityMapper = RoomCreateRequestToEntityMapper.INSTANCE;
    private static final RoomEntityToPageResponseMapper roomEntityToPageResponseMapper = RoomEntityToPageResponseMapper.INSTANCE;

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;


    @Override
    public List<RoomsSummaryResponse> findSummaryAll() {

        List<RoomEntity> roomEntities = roomRepository.findAll();
        return roomEntityToSummaryResponseMapper.map(roomEntities);
    }


    @Override
    public RoomResponse findById(Long id) {

        RoomEntity roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        return roomEntityToResponseMapper.map(roomEntity);
    }


    @Override
    public Page<RoomsResponse> findAll(Integer number, Integer floor, RoomStatus status, Long typeId, int page, int size, String property, Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(property).with(direction)));

        Specification<RoomEntity> spec = RoomEntity.generateSpecification(number, floor, status, typeId);

        Page<RoomEntity> roomEntities = roomRepository.findAll(spec, pageable);

        return roomEntityToPageResponseMapper.map(roomEntities);
    }


    @Override
    public void create(RoomCreateRequest roomCreateRequest) {

        boolean existsByNumber = roomRepository.existsByNumber(roomCreateRequest.getNumber());
        if (existsByNumber) {
            throw new RoomAlreadyExistsException(roomCreateRequest.getNumber().toString());
        }
        RoomEntity roomEntity = roomCreateRequestToEntityMapper.map(roomCreateRequest);

        Long typeId = roomCreateRequest.getRoomTypeId();
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(typeId)
                .orElseThrow(() -> new RoomTypeNotFoundException(typeId));
        roomEntity.setType(roomTypeEntity);
        roomRepository.save(roomEntity);
    }


    @Override
    public void update(Long id, RoomUpdateRequest roomUpdateRequest) {

        RoomEntity roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));

        Long typeId = roomUpdateRequest.getRoomTypeId();
        RoomTypeEntity roomTypeEntity = roomTypeRepository.findById(typeId)
                .orElseThrow(() -> new RoomTypeNotFoundException(typeId));
        roomEntity.setType(roomTypeEntity);

        roomEntity.update(
                roomUpdateRequest.getNumber(),
                roomUpdateRequest.getFloor(),
                roomUpdateRequest.getStatus()
        );

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
