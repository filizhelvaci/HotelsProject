package com.flz.service.impl;

import com.flz.exception.RoomAlreadyExistsException;
import com.flz.exception.RoomNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.enums.RoomStatus;
import com.flz.model.mapper.RoomCreateRequestToEntityMapper;
import com.flz.model.mapper.RoomEntityToPageResponseMapper;
import com.flz.model.mapper.RoomEntityToSummaryResponseMapper;
import com.flz.model.mapper.RoomTypeResponseToEntityMapper;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomsResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.repository.RoomRepository;
import com.flz.service.RoomTypeService;
import com.flz.service.impl.com.flz.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class RoomServiceImplTest extends BaseTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    RoomTypeService roomTypeService;

    @InjectMocks
    RoomServiceImpl roomService;

    /**
     * findSummaryAll()
     */
    @Test
    public void whenCalledAllSummaryRooms_thenReturnListOfRoomsSummaryResponse() {

        //When
        List<AssetEntity> mockAssets = getAssetEntities();
        RoomTypeEntity mockRoomTypeEntity1 = getRoomType(mockAssets);
        List<RoomEntity> mockRoomEntities = getRoomEntities(mockRoomTypeEntity1);

        Mockito.when(roomRepository.findAll())
                .thenReturn(mockRoomEntities);
        List<RoomsSummaryResponse> mockRoomsSummaryResponses =
                RoomEntityToSummaryResponseMapper.INSTANCE.map(mockRoomEntities);
        List<RoomsSummaryResponse> result = roomService.findSummaryAll();

        //Then
        Assertions.assertNotNull(mockRoomsSummaryResponses);
        Assertions.assertEquals(3, result.size());

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1)).findAll();

    }


    /**
     * findById
     */
    @Test
    public void givenValidId_whenRoomEntityFoundAccordingById_thenReturnRoomResponse() {

        //Given
        Long mockId = 10L;

        //When
        List<AssetEntity> mockAssets = getAssetEntities();
        RoomTypeEntity mockRoomTypeEntity1 = getRoomType(mockAssets);
        Optional<RoomEntity> mockRoomEntity = Optional.of(getRoomEntity(mockRoomTypeEntity1));

        Mockito.when(roomRepository.findById(mockId))
                .thenReturn(mockRoomEntity);
        RoomResponse roomResponse = roomService.findById(mockId);

        //Then
        Assertions.assertNotNull(roomResponse);
        Assertions.assertEquals(mockRoomEntity.get().getNumber(), roomResponse.getNumber());

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(mockId);

    }

    /**
     * findById-Exception
     */
    @Test
    public void givenValidId_whenAssetEntityNotFoundById_throwsAssetNotFoundException() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(roomRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(
                RoomNotFoundException.class,
                () -> roomService.findById(mockId),
                "This Room not found ID = " + mockId);

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(mockId);

    }

    /**
     * findAll
     */
    @Test
    public void givenFilterParameters_whenRoomEntityFoundByFilterParameters_thenReturnRoomsResponseList() {

        //Given
        Integer number = 102;
        Integer floor = 2;
        RoomStatus status = RoomStatus.EMPTY;
        Long typeId = 10L;
        int page = 0;
        int size = 5;
        String property = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        List<AssetEntity> mockAssets = getAssetEntities();
        RoomTypeEntity mockRoomTypeEntity1 = getRoomType(mockAssets);
        List<RoomEntity> mockRoomEntities = getRoomEntities(mockRoomTypeEntity1);

        Page<RoomEntity> mockRoomPageEntities = new PageImpl<>(mockRoomEntities);

        Mockito.when(roomRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(mockRoomPageEntities);

        Page<RoomsResponse> mockRoomsResponses =
                RoomEntityToPageResponseMapper.INSTANCE.map(mockRoomPageEntities);

        Page<RoomsResponse> result = roomService
                .findAll(number, floor, status, typeId, page, size, property, direction);

        //Then
        Assertions.assertNotNull(mockRoomPageEntities);
        Assertions.assertNotNull(mockRoomsResponses);
        Assertions.assertEquals(3, result.getContent().size());

        //Verify
        Mockito.verify(roomRepository).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

    }

    /**
     * create
     */
    @Test
    public void givenRoomCreateRequest_whenRoomByNameIsNotInTheDatabase_thenCreateAndSaveRoomEntity() {

        //Given
        RoomCreateRequest mockRoomCreateRequest = new RoomCreateRequest();
        mockRoomCreateRequest.setNumber(301);
        mockRoomCreateRequest.setFloor(3);
        mockRoomCreateRequest.setStatus(RoomStatus.EMPTY);
        mockRoomCreateRequest.setRoomTypeId(1L);

        //When
        Mockito.when(roomRepository.existsByNumber(mockRoomCreateRequest.getNumber()))
                .thenReturn(false);

        RoomEntity mockRoomEntity =
                RoomCreateRequestToEntityMapper.INSTANCE.map(mockRoomCreateRequest);

        Mockito.when(roomRepository.save(mockRoomEntity))
                .thenReturn(mockRoomEntity);

        roomService.create(mockRoomCreateRequest);

        //Then
        Assertions.assertNotNull(mockRoomEntity);
        Assertions.assertEquals(mockRoomCreateRequest.getNumber(), mockRoomEntity.getNumber());

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .existsByNumber(mockRoomCreateRequest.getNumber());
        Mockito.verify(roomRepository, Mockito.times(1))
                .save(Mockito.any());
    }

    /**
     * create-exception
     */
    @Test
    public void givenRoomCreateRequest_whenRoomByNumberAlreadyExists_thenThrowRoomAlreadyExistsException() {

        //Given
        RoomCreateRequest mockRoomCreateRequest = new RoomCreateRequest();
        mockRoomCreateRequest.setNumber(205);

        //When
        Mockito.when(roomRepository.existsByNumber(mockRoomCreateRequest.getNumber()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(RoomAlreadyExistsException.class,
                () -> roomService.create(mockRoomCreateRequest));

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .existsByNumber(mockRoomCreateRequest.getNumber());
        Mockito.verify(roomRepository, Mockito.never())
                .save(Mockito.any());

    }

    /**
     * update
     */
    @Test
    public void givenValidRoomIdAndAssetUpdateRequest_whenRoomEntityFoundById_thenThatRoomEntityUpdate() {

        //Given
        Long mockId = 10L;

        RoomUpdateRequest mockRoomUpdateRequest = new RoomUpdateRequest();
        mockRoomUpdateRequest.setNumber(205);
        mockRoomUpdateRequest.setFloor(2);
        mockRoomUpdateRequest.setStatus(RoomStatus.FULL);
        mockRoomUpdateRequest.setRoomTypeId(1L);

        //When
        List<AssetEntity> mockAssets = getAssetEntities();
        RoomTypeEntity mockRoomTypeEntity1 = getRoomType(mockAssets);
        RoomEntity mockRoomEntity = getRoomEntity(mockRoomTypeEntity1);

        Mockito.when(roomRepository.findById(mockId))
                .thenReturn(Optional.of(mockRoomEntity));

        List<RoomTypeResponse.Asset> mockAssetList = List.of(
                RoomTypeResponse.Asset.builder().id(1L).name("Yatak Seti").build(),
                RoomTypeResponse.Asset.builder().id(3L).name("Wifi").build()
        );
        RoomTypeResponse mockRoomTypeResponse = RoomTypeResponse.builder()
                .id(2L)
                .name("Standart Oda")
                .price(BigDecimal.valueOf(2000))
                .size(30)
                .personCount(2)
                .description("Bu mock response test için hazırlanmıştır ")
                .assets(mockAssetList)
                .build();

        Mockito.when(roomTypeService.findById(mockRoomUpdateRequest.getRoomTypeId()))
                .thenReturn(mockRoomTypeResponse);
        mockRoomEntity.setType(RoomTypeResponseToEntityMapper.INSTANCE.map(mockRoomTypeResponse));

        mockRoomEntity.update(
                mockRoomUpdateRequest.getNumber(),
                mockRoomUpdateRequest.getFloor(),
                mockRoomUpdateRequest.getStatus()
        );

        Mockito.when(roomRepository.save(mockRoomEntity))
                .thenReturn(mockRoomEntity);

        roomService.update(mockId, mockRoomUpdateRequest);

        //Then
        Assertions.assertNotNull(mockRoomEntity);
        Assertions.assertEquals(mockId, mockRoomEntity.getId());

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(roomRepository, Mockito.times(1))
                .save(mockRoomEntity);

    }


    /**
     * update-exception
     */

    /**
     * delete
     */
    /**
     * delete-exception
     */

    private static List<AssetEntity> getAssetEntities() {
        return List.of(
                AssetEntity.builder().id(1L).name("Yatak Seti").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetEntity.builder().id(2L).name("55 inç LCD TV").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetEntity.builder().id(3L).name("Wifi").price(BigDecimal.valueOf(0)).isDefault(false).build(),
                AssetEntity.builder().id(4L).name("Çay/kahve makinesi").price(BigDecimal.valueOf(100)).isDefault(false).build()
        );
    }

    private static RoomTypeEntity getRoomType(List<AssetEntity> mockAssets) {
        return RoomTypeEntity.builder()
                .id(1L)
                .name("Standart Oda")
                .price(BigDecimal.valueOf(2000))
                .size(30)
                .personCount(2)
                .description("Bahçe manzaralı bu oda standart şekilde tasarlanmıştır ve bir yatak odası bulunmaktadır. " +
                        "Bir kanepe/koltuk bulunur ve tuvaleti olan bir banyo sunmaktadır. " +
                        "Ayrıca WiFi, 55 inç LCD TV, MP3 çalar/radyo/çalar saat, çay/kahve yapma olanaklarını " +
                        "ve WiFi erişimini kapsamaktadır.")
                .assets(mockAssets)
                .build();
    }

    private static RoomEntity getRoomEntity(RoomTypeEntity roomTypeEntity) {
        return RoomEntity.builder()
                .id(10L)
                .number(201)
                .floor(2)
                .status(RoomStatus.EMPTY)
                .type(roomTypeEntity)
                .build();
    }

    private List<RoomEntity> getRoomEntities(RoomTypeEntity mockRoomTypeEntity1) {
        return List.of(
                getRoomEntity(mockRoomTypeEntity1),
                getRoomEntity(mockRoomTypeEntity1),
                getRoomEntity(mockRoomTypeEntity1)
        );
    }


}

