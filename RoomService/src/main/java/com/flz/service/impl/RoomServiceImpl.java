package com.flz.service.impl;

import com.flz.exception.RoomAlreadyExistsException;
import com.flz.exception.RoomNotFoundException;
import com.flz.model.entity.RoomEntity;
import com.flz.model.mapper.RoomCreateRequestToEntityMapper;
import com.flz.model.mapper.RoomEntityToResponseMapper;
import com.flz.model.mapper.RoomEntityToSummaryResponseMapper;
import com.flz.model.mapper.RoomUpdateRequestToEntityMapper;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.repository.RoomRepository;
import com.flz.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class RoomServiceImpl extends RoomService {

    private final RoomRepository roomRepository;

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
//
//    @Override
//    public Page<AssetsResponse> findAll(String name,
//                                        BigDecimal minPrice,
//                                        BigDecimal maxPrice,
//                                        int page,
//                                        int size,
//                                        String sortBy,
//                                        String sortDirection) {
//
//        Sort sort = Sort.by(
//                sortDirection.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy)
//        );
//
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        Page<AssetEntity> assetEntities = assetRepository.findByNameContainingAndPriceBetween(
//                name != null ? name : "",
//                minPrice != null ? minPrice : BigDecimal.ZERO,
//                maxPrice != null ? maxPrice : BigDecimal.valueOf(Long.MAX_VALUE),
//                pageable
//        );
//
//        return AssetEntityToPageResponseMapper.map(assetEntities);
//    }

    @Override
    public void create(RoomCreateRequest roomCreateRequest) {

        boolean existsByNumber = roomRepository.existsByNumber(roomCreateRequest.getNumber());
        if (existsByNumber) {
            throw new RoomAlreadyExistsException(roomCreateRequest.getNumber().toString());
        }
        RoomEntity roomEntity = RoomCreateRequestToEntityMapper.map(roomCreateRequest);
        roomRepository.save(roomEntity);

    }


    @Override
    public void update(Long id, RoomUpdateRequest roomUpdateRequest) {

        RoomEntity roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
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
