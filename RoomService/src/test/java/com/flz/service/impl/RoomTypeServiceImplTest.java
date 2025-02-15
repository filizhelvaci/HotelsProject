package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.RoomTypeAlreadyExistsException;
import com.flz.exception.RoomTypeNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.entity.RoomTypeEntity;
import com.flz.model.mapper.AssetResponseToEntityMapper;
import com.flz.model.mapper.RoomTypeCreateRequestToEntityMapper;
import com.flz.model.mapper.RoomTypeEntityToPageResponseMapper;
import com.flz.model.mapper.RoomTypeEntityToSummaryResponseMapper;
import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomTypesResponse;
import com.flz.model.response.RoomTypesSummaryResponse;
import com.flz.repository.RoomTypeRepository;
import com.flz.service.AssetService;
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

class RoomTypeServiceImplTest extends BaseTest {

    @Mock
    RoomTypeRepository roomTypeRepository;

    @Mock
    AssetService assetService;

    @InjectMocks
    RoomTypeServiceImpl roomTypeService;

    /**
     * {@link RoomTypeServiceImpl#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryRoomType_thenReturnListOfRoomTypesSummaryResponse() {

        //When
        List<AssetEntity> mockAssets = getAssets();
        List<RoomTypeEntity> mockRoomTypeEntities = getRoomTypes(mockAssets);
        Mockito.when(roomTypeRepository.findAll())
                .thenReturn(mockRoomTypeEntities);
        List<RoomTypesSummaryResponse> mockRoomTypesSummaryResponses =
                RoomTypeEntityToSummaryResponseMapper.INSTANCE.map(mockRoomTypeEntities);
        List<RoomTypesSummaryResponse> result = roomTypeService.findSummaryAll();

        //Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockRoomTypesSummaryResponses.size(), result.size());

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1)).findAll();

    }

    /**
     * {@link RoomTypeServiceImpl#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryRoomTypeListIfAllRoomTypeListIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(roomTypeRepository.findAll()).thenReturn(Collections.emptyList());

        List<RoomTypesSummaryResponse> roomTypeSummaryResponses = roomTypeService.findSummaryAll();

        //Then
        Assertions.assertNotNull(roomTypeRepository);
        Assertions.assertEquals(0, roomTypeRepository.count());
        Assertions.assertTrue(roomTypeSummaryResponses.isEmpty());

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1)).findAll();

    }

    /**
     * {@link RoomTypeServiceImpl#findById(Long)}
     */
    @Test
    public void givenValidId_whenRoomTypeEntityFoundAccordingById_thenReturnRoomTypeResponse() {

        //Given
        Long mockId = 1L;

        //When
        List<AssetEntity> mockAssets = getAssets();

        Optional<RoomTypeEntity> mockRoomTypeEntity = Optional.of(getRoomType(mockAssets));

        Mockito.when(roomTypeRepository.findById(mockId))
                .thenReturn(mockRoomTypeEntity);
        RoomTypeResponse roomTypeResponse = roomTypeService.findById(mockId);

        //Then
        Assertions.assertNotNull(roomTypeResponse);

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .findById(mockId);

    }

    /**
     * {@link RoomTypeServiceImpl#findById(Long)}
     */
    @Test
    public void givenValidId_whenRoomTypeEntityNotFoundById_throwsRoomTypeNotFoundException() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(roomTypeRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(
                RoomTypeNotFoundException.class,
                () -> roomTypeService.findById(mockId),
                "This Room Type not found ID = " + mockId);

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .findById(mockId);

    }

    /**
     * {@link RoomTypeServiceImpl#findAll(String, BigDecimal, BigDecimal, Integer, Integer, int, int, String, Sort.Direction)}
     */
    @Test
    public void givenFilterParameters_whenAssetEntityFoundByFilterParameters_thenReturnAssetsResponseList() {

        //Given
        String name = "test";
        BigDecimal minPrice = BigDecimal.valueOf(100);
        BigDecimal maxPrice = BigDecimal.valueOf(2000);
        Integer personCount = 2;
        Integer size = 50;
        int page = 0;
        int pageSize = 5;
        String property = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        List<AssetEntity> mockAssets = getAssets();
        List<RoomTypeEntity> mockRoomTypeEntities = getRoomTypes(mockAssets);
        Page<RoomTypeEntity> mockRoomTypePageEntities = new PageImpl<>(mockRoomTypeEntities);

        Mockito.when(roomTypeRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(mockRoomTypePageEntities);

        Page<RoomTypesResponse> mockRoomTypesResponses =
                RoomTypeEntityToPageResponseMapper.INSTANCE.map(mockRoomTypePageEntities);

        Page<RoomTypesResponse> result = roomTypeService
                .findAll(name, minPrice, maxPrice, personCount, size, page, pageSize, property, direction);

        //Then
        Assertions.assertNotNull(mockRoomTypesResponses);
        Assertions.assertEquals(result.getContent().size(), mockRoomTypesResponses.getContent().size());
        Assertions.assertNotNull(mockRoomTypesResponses.getContent());

        //Verify
        Mockito.verify(roomTypeRepository).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

    }

    /**
     * {@link RoomTypeServiceImpl#findAll(String, BigDecimal, BigDecimal, Integer, Integer, int, int, String, Sort.Direction)}
     */
    @Test
    public void whenCalledFilteredRoomTypeListIfRoomTypeListIsEmpty_thenReturnEmptyList() {

        //Given
        String name = "test";
        BigDecimal minPrice = BigDecimal.valueOf(100);
        BigDecimal maxPrice = BigDecimal.valueOf(2000);
        Integer personCount = 2;
        Integer size = 50;
        int page = 0;
        int pageSize = 5;
        String property = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        Mockito.when(roomTypeRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(Page.empty());

        Page<RoomTypesResponse> roomTypeResponses
                = roomTypeService.findAll(name, minPrice, maxPrice, personCount, size, page, pageSize, property, direction);

        //Then
        Assertions.assertNotNull(roomTypeRepository);
        Assertions.assertEquals(0, roomTypeRepository.count());
        Assertions.assertTrue(roomTypeResponses.isEmpty());

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));

    }

    /**
     * {@link RoomTypeServiceImpl#create(RoomTypeCreateRequest)}
     */
    @Test
    public void givenRoomTypeCreateRequest_whenRoomTypeByNameIsNotInTheDatabase_thenCreateAndSaveRoomTypeEntity() {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();
        mockRoomTypeCreateRequest.setName("testNewRoomType");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setPersonCount(3);
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setDescription("Bu bir mock test açıklamasıdır :) ");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 2L, 3L, 4L));

        //When
        List<AssetResponse> mockAssetsResponse = List.of(
                AssetResponse.builder().id(1L).name("Yatak Seti").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetResponse.builder().id(2L).name("55 inç LCD TV").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetResponse.builder().id(3L).name("Wifi").price(BigDecimal.valueOf(0)).isDefault(false).build(),
                AssetResponse.builder().id(4L).name("Çay/kahve makinesi").price(BigDecimal.valueOf(100)).isDefault(false).build()
        );

        Mockito.when(roomTypeRepository.existsByName(mockRoomTypeCreateRequest.getName()))
                .thenReturn(false);

        RoomTypeEntity mockRoomTypeEntity =
                RoomTypeCreateRequestToEntityMapper.INSTANCE.map(mockRoomTypeCreateRequest);

        Mockito.when(assetService.findAllById(mockRoomTypeCreateRequest.getAssetIds()))
                .thenReturn(mockAssetsResponse);

        mockRoomTypeEntity.setAssets(AssetResponseToEntityMapper.INSTANCE.map(mockAssetsResponse));

        Mockito.when(roomTypeRepository.save(mockRoomTypeEntity))
                .thenReturn(mockRoomTypeEntity);

        roomTypeService.create(mockRoomTypeCreateRequest);

        //Then
        Assertions.assertNotNull(mockRoomTypeEntity);

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .existsByName(mockRoomTypeCreateRequest.getName());
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .save(Mockito.any());
    }

    /**
     * {@link RoomTypeServiceImpl#create(RoomTypeCreateRequest)}
     */
    @Test
    public void givenRoomTypeCreateRequest_whenRoomTypeByNameAlreadyExists_thenThrowRoomTypeAlreadyExistsException() {

        //Given
        RoomTypeCreateRequest roomTypeCreateRequest = new RoomTypeCreateRequest();
        roomTypeCreateRequest.setName("testNewRoomType");

        //When
        Mockito.when(roomTypeRepository.existsByName(roomTypeCreateRequest.getName()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(RoomTypeAlreadyExistsException.class,
                () -> roomTypeService.create(roomTypeCreateRequest));

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .existsByName(roomTypeCreateRequest.getName());
        Mockito.verify(roomTypeRepository, Mockito.never())
                .save(Mockito.any());

    }


    /**
     * {@link RoomTypeServiceImpl#update(Long, RoomTypeUpdateRequest)}
     */
    @Test
    public void givenValidRoomTypeIdAndRoomTypeUpdateRequest_whenRoomTypeEntityFoundById_thenThatRoomTypeEntityUpdate() {

        //Given
        Long mockId = 1L;

        List<AssetResponse> mockAssetsResponse = List.of(
                AssetResponse.builder().id(5L).name("Nevresim Seti").price(BigDecimal.valueOf(10)).isDefault(false).build(),
                AssetResponse.builder().id(6L).name("65 inç LCD TV").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetResponse.builder().id(7L).name("Kahvaltı Seti").price(BigDecimal.valueOf(250)).isDefault(false).build(),
                AssetResponse.builder().id(4L).name("Çay/kahve makinesi").price(BigDecimal.valueOf(100)).isDefault(false).build()
        );

        RoomTypeUpdateRequest mockRoomTypeUpdateRequest = new RoomTypeUpdateRequest();
        mockRoomTypeUpdateRequest.setName("update Room Type Name");
        mockRoomTypeUpdateRequest.setPrice(BigDecimal.valueOf(5000));
        mockRoomTypeUpdateRequest.setPersonCount(10);
        mockRoomTypeUpdateRequest.setSize(140);
        mockRoomTypeUpdateRequest.setDescription("update Room Type Description");
        mockRoomTypeUpdateRequest.setAssetIds(List.of(5L, 6L, 7L, 4L));

        //When
        List<AssetEntity> mockAssets = getAssets();

        RoomTypeEntity mockRoomTypeEntity = getRoomType(mockAssets);

        Mockito.when(roomTypeRepository.findById(mockId))
                .thenReturn(Optional.of(mockRoomTypeEntity));

        Mockito.when(assetService.findAllById(mockRoomTypeUpdateRequest.getAssetIds()))
                .thenReturn(mockAssetsResponse);

        mockRoomTypeEntity.setAssets(AssetResponseToEntityMapper.INSTANCE.map(mockAssetsResponse));

        mockRoomTypeEntity.update(
                mockRoomTypeUpdateRequest.getName(),
                mockRoomTypeUpdateRequest.getPrice(),
                mockRoomTypeUpdateRequest.getPersonCount(),
                mockRoomTypeUpdateRequest.getSize(),
                mockRoomTypeUpdateRequest.getDescription()
        );

        Mockito.when(roomTypeRepository.save(mockRoomTypeEntity))
                .thenReturn(mockRoomTypeEntity);

        roomTypeService.update(mockId, mockRoomTypeUpdateRequest);

        //Then
        Assertions.assertNotNull(mockRoomTypeEntity);

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(assetService, Mockito.times(1))
                .findAllById(mockRoomTypeUpdateRequest.getAssetIds());
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .save(mockRoomTypeEntity);
    }

    /**
     * {@link RoomTypeServiceImpl#update(Long, RoomTypeUpdateRequest)}
     */
    @Test
    public void givenValidIdAndRoomTypeUpdateRequest_whenRoomTypeEntityNotFoundById_throwsRoomTypeNotFoundException() {

        //Given
        Long mockId = 10L;

        RoomTypeUpdateRequest mockRoomTypeUpdateRequest = new RoomTypeUpdateRequest();
        mockRoomTypeUpdateRequest.setName("update Room Type Name");
        mockRoomTypeUpdateRequest.setPrice(BigDecimal.valueOf(5000));
        mockRoomTypeUpdateRequest.setPersonCount(10);
        mockRoomTypeUpdateRequest.setSize(140);
        mockRoomTypeUpdateRequest.setDescription("update Room Type Description");
        mockRoomTypeUpdateRequest.setAssetIds(List.of(5L, 6L, 7L, 4L));

        //When
        Mockito.when(roomTypeRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(RoomTypeNotFoundException.class,
                () -> roomTypeService.update(mockId, mockRoomTypeUpdateRequest));

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(assetService, Mockito.never())
                .findAllById(Mockito.any());
        Mockito.verify(roomTypeRepository, Mockito.never())
                .save(Mockito.any());
    }

    /**
     * {@link RoomTypeServiceImpl#delete(Long)}
     */
    @Test
    public void givenValidId_whenRoomTypeEntityFoundById_thenDeleteRoomTypeEntity() {

        //Given
        Long mockId = 1L;

        List<AssetEntity> mockAssets = getAssets();

        RoomTypeEntity mockRoomTypeEntity = getRoomType(mockAssets);

        //When
        Mockito.when(roomTypeRepository.existsById(mockId))
                .thenReturn(true);

        Mockito.doNothing().when(roomTypeRepository)
                .deleteById(mockId);

        roomTypeService.delete(mockId);

        //Then
        Assertions.assertNotNull(mockRoomTypeEntity);
        Assertions.assertEquals(mockId, mockRoomTypeEntity.getId());

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .existsById(mockId);
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .deleteById(mockId);

    }

    /**
     * {@link RoomTypeServiceImpl#delete(Long)}
     */
    @Test
    public void givenValidId_whenRoomTypeEntityNotFoundById_thenReturnThrowRoomTypeNotFoundException() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(roomTypeRepository.existsById(mockId))
                .thenReturn(false);

        //Then
        Assertions.assertThrows(RoomTypeNotFoundException.class,
                () -> roomTypeService.delete(mockId));

        //Verify
        Mockito.verify(roomTypeRepository, Mockito.times(1))
                .existsById(mockId);
        Mockito.verify(roomTypeRepository, Mockito.never())
                .deleteById(mockId);
    }

    /**
     * @return
     */
    private static List<RoomTypeEntity> getRoomTypes(List<AssetEntity> mockAssets) {
        return List.of(getRoomType(mockAssets),
                RoomTypeEntity.builder()
                        .id(2L)
                        .name("Lux Oda")
                        .price(BigDecimal.valueOf(4000))
                        .size(50)
                        .personCount(3)
                        .description("Bahçe manzarası, balkon, küvet, duş ve ayrı tuvalet, 55-inç TV, WiFi, " +
                                "50 metrekarelik bu konforlu ve ferah bahçe manzaralı odada bir king yatak, balkon, " +
                                "kanepe veya koltuk, küvetli banyo, duş ve ayrı tuvalet bulunmaktadır. " +
                                "55-inç LCD televizyonda bir programın veya MP3 çalar radyo-çalar saatte müziğin keyfini çıkarın." +
                                "Diğer hizmetler arasında WiFi ve çay/kahve yapma olanakları yer almaktadır.")
                        .assets(mockAssets)
                        .build()
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

    private static List<AssetEntity> getAssets() {
        return List.of(
                AssetEntity.builder().id(1L).name("Yatak Seti").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetEntity.builder().id(2L).name("55 inç LCD TV").price(BigDecimal.valueOf(100)).isDefault(false).build(),
                AssetEntity.builder().id(3L).name("Wifi").price(BigDecimal.valueOf(0)).isDefault(false).build(),
                AssetEntity.builder().id(4L).name("Çay/kahve makinesi").price(BigDecimal.valueOf(100)).isDefault(false).build()
        );
    }


}