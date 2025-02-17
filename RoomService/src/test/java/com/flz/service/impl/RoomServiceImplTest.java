package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.RoomAlreadyExistsException;
import com.flz.exception.RoomNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.enums.RoomStatus;
import com.flz.model.mapper.RoomCreateRequestToEntityMapper;
import com.flz.model.mapper.RoomEntityToPageResponseMapper;
import com.flz.model.mapper.RoomEntityToSummaryResponseMapper;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.repository.RoomRepository;
import com.flz.repository.RoomTypeRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class RoomServiceImplTest extends BaseTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    RoomTypeRepository roomTypeRepository;

    @InjectMocks
    RoomServiceImpl roomService;

    /**
     * {@link RoomServiceImpl#findSummaryAll()}
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
     * {@link RoomServiceImpl#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryRoomIfRoomListIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(roomRepository.findAll()).thenReturn(Collections.emptyList());

        List<RoomsSummaryResponse> roomsSummaryResponses = roomService.findSummaryAll();

        //Then
        Assertions.assertNotNull(roomRepository);
        Assertions.assertEquals(0, roomRepository.count());
        Assertions.assertTrue(roomsSummaryResponses.isEmpty());

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1)).findAll();

    }

    /**
     * {@link RoomServiceImpl#findById(Long)}
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
     * {@link RoomServiceImpl#findById(Long)}
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
     * {@link RoomServiceImpl#findAll(Integer, Integer, RoomStatus, Long, int, int, String, Sort.Direction)}
     */
    @Test
    public void givenFilterParameters_whenRoomEntityFoundByFilterParameters_thenReturnRoomsResponseList() {

        //Given
        Integer mockNumber = 102;
        Integer mockFloor = 2;
        RoomStatus mockStatus = RoomStatus.EMPTY;
        Long mockTypeId = 10L;
        int mockPage = 0;
        int mockSize = 5;
        String mockProperty = "name";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        List<AssetEntity> mockAssets = getAssetEntities();
        RoomTypeEntity mockRoomTypeEntity1 = getRoomType(mockAssets);
        List<RoomEntity> mockRoomEntities = getRoomEntities(mockRoomTypeEntity1);

        Page<RoomEntity> mockRoomPageEntities = new PageImpl<>(mockRoomEntities);

        Mockito.when(roomRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(mockRoomPageEntities);

        Page<RoomsResponse> mockRoomsResponses = RoomEntityToPageResponseMapper
                .INSTANCE.map(mockRoomPageEntities);

        Page<RoomsResponse> result = roomService
                .findAll(mockNumber, mockFloor, mockStatus, mockTypeId, mockPage, mockSize, mockProperty, mockDirection);

        //Then
        Assertions.assertNotNull(mockRoomPageEntities);
        Assertions.assertNotNull(mockRoomsResponses);
        Assertions.assertEquals(3, result.getContent().size());

        //Verify
        Mockito.verify(roomRepository).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

    }

    /**
     * {@link RoomServiceImpl#findAll(Integer, Integer, RoomStatus, Long, int, int, String, Sort.Direction)}
     */
    @Test
    public void whenCalledFilteredRoomListIfRoomListIsEmpty_thenReturnEmptyList() {

        //Given
        Integer mockNumber = 102;
        Integer mockFloor = 2;
        RoomStatus mockStatus = RoomStatus.EMPTY;
        Long mockTypeId = 10L;
        int mockPage = 0;
        int mockSize = 5;
        String mockProperty = "name";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        Mockito.when(roomRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(Page.empty());

        Page<RoomsResponse> roomsResponses
                = roomService.findAll(mockNumber, mockFloor, mockStatus, mockTypeId, mockPage, mockSize, mockProperty, mockDirection);

        //Then
        Assertions.assertNotNull(roomRepository);
        Assertions.assertEquals(0, roomRepository.count());
        Assertions.assertTrue(roomsResponses.isEmpty());

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

    }

    /**
     * {@link RoomServiceImpl#create(RoomCreateRequest)}
     */
    @Test
    public void givenRoomCreateRequest_whenRoomByNameIsNotInTheDatabase_thenCreateAndSaveRoomEntity() {

        //Given
        Long mockTypeId = 1L;

        RoomCreateRequest mockRoomCreateRequest = new RoomCreateRequest();
        mockRoomCreateRequest.setNumber(301);
        mockRoomCreateRequest.setFloor(3);
        mockRoomCreateRequest.setStatus(RoomStatus.EMPTY);
        mockRoomCreateRequest.setRoomTypeId(mockTypeId);

        //When
        List<AssetEntity> mockAssetEntities = getAssetEntities();

        Optional<RoomTypeEntity> mockRoomTypeEntity = Optional.of(RoomTypeEntity.builder()
                .id(1L)
                .name("Lux Room")
                .price(BigDecimal.valueOf(2000))
                .size(45)
                .personCount(2)
                .description("This is room type test")
                .assets(mockAssetEntities)
                .build());

        Mockito.when(roomRepository.existsByNumber(mockRoomCreateRequest.getNumber())).thenReturn(false);

        RoomEntity roomEntity = RoomCreateRequestToEntityMapper.INSTANCE.map(mockRoomCreateRequest);

        Long mockRoomTypeId = mockRoomCreateRequest.getRoomTypeId();
        Mockito.when(roomTypeRepository.findById(mockRoomTypeId)).thenReturn(mockRoomTypeEntity);
        roomEntity.setType(mockRoomTypeEntity.get());

        Mockito.when(roomRepository.save(roomEntity)).thenReturn(roomEntity);

        roomService.create(mockRoomCreateRequest);

        //Then
        Assertions.assertNotNull(roomEntity);
        Assertions.assertEquals(mockRoomCreateRequest.getNumber(), roomEntity.getNumber());

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .existsByNumber(mockRoomCreateRequest.getNumber());
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .findById(mockRoomTypeId);
        Mockito.verify(roomRepository, Mockito.times(1))
                .save(Mockito.any());
    }

    /**
     * {@link RoomServiceImpl#create(RoomCreateRequest)}
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
     * {@link RoomServiceImpl#update(Long, RoomUpdateRequest)}
     */
    @Test
    public void givenValidRoomIdAndRoomUpdateRequest_whenRoomEntityFoundById_thenThatRoomEntityUpdate() {

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

        List<AssetEntity> assetEntities = getAssetEntities();
        RoomTypeEntity mockRoomTypeEntity = getRoomType(assetEntities);

        Mockito.when(roomTypeRepository.findById(mockRoomUpdateRequest.getRoomTypeId()))
                .thenReturn(Optional.of(mockRoomTypeEntity));

        mockRoomEntity.setType(mockRoomTypeEntity);

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
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .findById(mockRoomUpdateRequest.getRoomTypeId());
        Mockito.verify(roomRepository, Mockito.times(1))
                .save(mockRoomEntity);
    }

    /**
     * {@link RoomServiceImpl#update(Long, RoomUpdateRequest)}
     */
    @Test
    public void givenValidIdAndRoomUpdateRequest_whenRoomEntityNotFoundById_throwsRoomNotFoundException() {

        //Given
        Long mockId = 10L;

        RoomUpdateRequest mockRoomUpdateRequest = new RoomUpdateRequest();
        mockRoomUpdateRequest.setNumber(205);
        mockRoomUpdateRequest.setFloor(2);
        mockRoomUpdateRequest.setStatus(RoomStatus.FULL);
        mockRoomUpdateRequest.setRoomTypeId(1L);

        //When
        Mockito.when(roomRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(RoomNotFoundException.class,
                () -> roomService.update(mockId, mockRoomUpdateRequest));

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(roomRepository, Mockito.never())
                .save(Mockito.any());
    }

    /**
     * {@link RoomServiceImpl#update(Long, RoomUpdateRequest)}
     */
    @Test
    public void givenValidId_whenRoomEntityFoundById_thenDeleteRoomEntity() {

        //Given
        Long mockId = 10L;

        //When
        List<AssetEntity> mockAssets = getAssetEntities();
        RoomTypeEntity mockRoomTypeEntity1 = getRoomType(mockAssets);
        RoomEntity mockRoomEntity = getRoomEntity(mockRoomTypeEntity1);

        Mockito.when(roomRepository.existsById(mockId))
                .thenReturn(true);

        Mockito.doNothing().when(roomRepository)
                .deleteById(mockId);

        roomService.delete(mockId);

        //Then
        Assertions.assertNotNull(mockRoomEntity);
        Assertions.assertEquals(mockId, mockRoomEntity.getId());

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .existsById(mockId);
        Mockito.verify(roomRepository, Mockito.times(1))
                .deleteById(mockId);

    }

    /**
     * {@link RoomServiceImpl#delete(Long)}
     */
    @Test
    public void givenValidId_whenRoomEntityNotFoundById_thenReturnThrowRoomNotFoundException() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(roomRepository.existsById(mockId))
                .thenReturn(false);

        //Then
        Assertions.assertThrows(RoomNotFoundException.class,
                () -> roomService.delete(mockId));

        //Verify
        Mockito.verify(roomRepository, Mockito.times(1))
                .existsById(mockId);
        Mockito.verify(roomRepository, Mockito.never())
                .deleteById(mockId);
    }

    /**
     * # Methodized Objects
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

