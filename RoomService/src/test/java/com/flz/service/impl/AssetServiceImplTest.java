package com.flz.service.impl;

import com.flz.exception.AssetAlreadyExistsException;
import com.flz.exception.AssetNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.mapper.AssetCreateRequestToEntityMapper;
import com.flz.model.mapper.AssetEntityToPageResponseMapper;
import com.flz.model.mapper.AssetEntityToResponseMapper;
import com.flz.model.mapper.AssetEntityToSummaryResponseMapper;
import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsResponse;
import com.flz.model.response.AssetsSummaryResponse;
import com.flz.repository.AssetRepository;
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

import static org.mockito.ArgumentMatchers.any;


class AssetServiceImplTest extends BaseTest {

    @Mock
    AssetRepository assetRepository;

    @InjectMocks
    AssetServiceImpl assetService;

    /**
     * findSummaryAll()
     */
    @Test
    public void whenCalledAllSummaryAsset_thenReturnListOfAssetsSummaryResponse() {

        //When
        List<AssetEntity> assetEntities = List.of(
                AssetEntity.builder()
                        .id(10L)
                        .name("testAsset1")
                        .price(BigDecimal.valueOf(1100))
                        .isDefault(true)
                        .build(),
                AssetEntity.builder()
                        .id(11L)
                        .name("testAsset2")
                        .price(BigDecimal.valueOf(1200))
                        .isDefault(false)
                        .build(),
                AssetEntity.builder()
                        .id(12L)
                        .name("testAsset3")
                        .price(BigDecimal.valueOf(1300))
                        .isDefault(true)
                        .build()
        );

        Mockito.when(assetRepository.findAll())
                .thenReturn(assetEntities);
        List<AssetsSummaryResponse> assetsSummaryResponses =
                AssetEntityToSummaryResponseMapper.INSTANCE.map(assetEntities);
        List<AssetsSummaryResponse> result = assetService.findSummaryAll();

        //Then
        Assertions.assertEquals(assetsSummaryResponses, result);

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1)).findAll();

    }

    /**
     * findAllById
     */
    @Test
    public void givenValidAssetIdList__whenAssetEntitiesFoundAccordingToThatIdList_thenReturnAssetResponseList() {

        //Given
        List<Long> mockIds = List.of(10L, 11L, 12L);

        //When
        List<AssetEntity> mockAssetEntities = List.of(
                AssetEntity.builder()
                        .id(10L)
                        .name("testAsset1")
                        .price(BigDecimal.valueOf(1100))
                        .isDefault(true)
                        .build(),
                AssetEntity.builder()
                        .id(11L)
                        .name("testAsset2")
                        .price(BigDecimal.valueOf(1200))
                        .isDefault(false)
                        .build(),
                AssetEntity.builder()
                        .id(12L)
                        .name("testAsset3")
                        .price(BigDecimal.valueOf(1300))
                        .isDefault(true)
                        .build()
        );

        Mockito.when(assetRepository.findAllById(mockIds))
                .thenReturn(mockAssetEntities);

        List<AssetResponse> mockAssetResponses =
                AssetEntityToResponseMapper.INSTANCE.map(mockAssetEntities);
        List<AssetResponse> assetResponses = assetService.findAllById(mockIds);

        //Then
        Assertions.assertNotNull(assetResponses);
        Assertions.assertEquals(assetResponses, mockAssetResponses);

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1))
                .findAllById(mockIds);

    }

    /**
     * findById
     */
    @Test
    public void givenValidId_whenAssetEntityFoundAccordingById_thenReturnAssetResponse() {

        //Given
        Long mockId = 1L;

        //When
        Optional<AssetEntity> mockAssetEntity = Optional.of(AssetEntity.builder()
                .id(1L)
                .name("testAsset")
                .price(BigDecimal.valueOf(1000))
                .isDefault(true)
                .build());

        Mockito.when(assetRepository.findById(mockId))
                .thenReturn(mockAssetEntity);
        AssetResponse assetResponse = assetService.findById(mockId);

        //Then
        Assertions.assertNotNull(assetResponse);
        Assertions.assertEquals(mockId, assetResponse.getId());

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1))
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
        Mockito.when(assetRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(
                AssetNotFoundException.class,
                () -> assetService.findById(mockId),
                "This Asset not found ID = " + mockId);

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1))
                .findById(mockId);

    }

    /**
     * findAll
     */
    @Test
    public void givenFilterParameters_whenAssetEntityFoundByFilterParameters_thenReturnAssetsResponseList() {

        //Given
        String name = "test";
        BigDecimal minPrice = BigDecimal.valueOf(100);
        BigDecimal maxPrice = BigDecimal.valueOf(2000);
        Boolean isDefault = true;
        int page = 0;
        int size = 5;
        String property = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        List<AssetEntity> mockAssetEntities = List.of(
                AssetEntity.builder()
                        .id(10L)
                        .name("test Asset1")
                        .price(BigDecimal.valueOf(1100))
                        .isDefault(true)
                        .build(),
                AssetEntity.builder()
                        .id(11L)
                        .name("test Asset2")
                        .price(BigDecimal.valueOf(1200))
                        .isDefault(true)
                        .build(),
                AssetEntity.builder()
                        .id(12L)
                        .name("test Asset3")
                        .price(BigDecimal.valueOf(1300))
                        .isDefault(true)
                        .build()
        );

        Page<AssetEntity> mockAssetPageEntities = new PageImpl<>(mockAssetEntities);

        Mockito.when(assetRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(mockAssetPageEntities);

        Page<AssetsResponse> mockAssetsResponses =
                AssetEntityToPageResponseMapper.INSTANCE.map(mockAssetPageEntities);

        Page<AssetsResponse> result = assetService
                .findAll(name, minPrice, maxPrice, isDefault, page, size, property, direction);

        //Then
        Assertions.assertNotNull(mockAssetPageEntities);
        Assertions.assertNotNull(mockAssetsResponses);
        Assertions.assertEquals(result, mockAssetsResponses);

        //Verify
        Mockito.verify(assetRepository).findAll(any(Specification.class), any(Pageable.class));

    }

    /**
     * create
     */
    @Test
    public void givenAssetCreateRequest_whenAssetByNameIsNotInTheDatabase_thenCreateAndSaveAssetEntity() {

        //Given
        AssetCreateRequest mockAssetCreateRequest = new AssetCreateRequest();
        mockAssetCreateRequest.setName("testAsset");
        mockAssetCreateRequest.setPrice(BigDecimal.valueOf(1000));
        mockAssetCreateRequest.setIsDefault(true);

        //When
        Mockito.when(assetRepository.existsByName(mockAssetCreateRequest.getName()))
                .thenReturn(false);

        AssetEntity mockAssetEntity =
                AssetCreateRequestToEntityMapper.INSTANCE.map(mockAssetCreateRequest);

        Mockito.when(assetRepository.save(mockAssetEntity))
                .thenReturn(mockAssetEntity);

        assetService.create(mockAssetCreateRequest);

        //Then
        Assertions.assertNotNull(mockAssetEntity);
        Assertions.assertEquals(mockAssetCreateRequest.getName(), mockAssetEntity.getName());

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1))
                .existsByName(mockAssetCreateRequest.getName());
        Mockito.verify(assetRepository, Mockito.times(1))
                .save(any());
    }

    /**
     * create-exception
     */
    @Test
    public void givenAssetCreateRequest_whenAssetByNameAlreadyExists_thenThrowAssetAlreadyExistsException() {

        //Given
        AssetCreateRequest mockAssetCreateRequest = new AssetCreateRequest();
        mockAssetCreateRequest.setName("testAsset");

        //When
        Mockito.when(assetRepository.existsByName(mockAssetCreateRequest.getName()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(AssetAlreadyExistsException.class,
                () -> assetService.create(mockAssetCreateRequest));

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1))
                .existsByName(mockAssetCreateRequest.getName());
        Mockito.verify(assetRepository, Mockito.never())
                .save(any());

    }

    /**
     * update
     */
    @Test
    public void givenValidAssetIdAndAssetUpdateRequest_whenAssetEntityFoundById_thenThatAssetEntityUpdate() {

        //Given
        Long mockId = 10L;

        AssetUpdateRequest mockAssetUpdateRequest = new AssetUpdateRequest();
        mockAssetUpdateRequest.setName("updateAsset");
        mockAssetUpdateRequest.setPrice(BigDecimal.valueOf(2000));
        mockAssetUpdateRequest.setIsDefault(false);

        //When
        AssetEntity mockAssetEntity = AssetEntity.builder()
                .id(mockId)
                .name("testAsset")
                .price(BigDecimal.valueOf(1000))
                .isDefault(true)
                .build();

        Mockito.when(assetRepository.findById(mockId))
                .thenReturn(Optional.of(mockAssetEntity));

        mockAssetEntity.update(
                mockAssetUpdateRequest.getName(),
                mockAssetUpdateRequest.getPrice(),
                mockAssetUpdateRequest.getIsDefault()
        );

        Mockito.when(assetRepository.save(mockAssetEntity))
                .thenReturn(mockAssetEntity);

        assetService.update(mockId, mockAssetUpdateRequest);

        //Then
        Assertions.assertNotNull(mockAssetEntity);
        Assertions.assertEquals(mockId, mockAssetEntity.getId());

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(assetRepository, Mockito.times(1))
                .save(mockAssetEntity);

    }

    /**
     * update-exception
     */
    @Test
    public void givenValidIdAndAssetUpdateRequest_whenAssetEntityNotFoundById_throwsAssetNotFoundException() {

        //Given
        Long mockId = 10L;

        AssetUpdateRequest mockAssetUpdateRequest = new AssetUpdateRequest();
        mockAssetUpdateRequest.setName("updateAsset");
        mockAssetUpdateRequest.setPrice(BigDecimal.valueOf(2000));
        mockAssetUpdateRequest.setIsDefault(false);

        //When
        Mockito.when(assetRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(AssetNotFoundException.class,
                () -> assetService.update(mockId, mockAssetUpdateRequest));

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(assetRepository, Mockito.never())
                .save(any());
    }

    /**
     * delete
     */
    @Test
    public void givenValidId_whenAssetEntityFoundById_thenDeleteAssetEntity() {

        //Given
        Long mockId = 10L;

        AssetEntity mockAssetEntity = AssetEntity.builder()
                .id(mockId)
                .name("testAsset")
                .price(BigDecimal.valueOf(1000))
                .isDefault(true)
                .build();

        //When
        Mockito.when(assetRepository.existsById(mockId))
                .thenReturn(true);

        Mockito.doNothing().when(assetRepository)
                .deleteById(mockId);

        assetService.delete(mockId);

        //Then
        Assertions.assertNotNull(mockAssetEntity);
        Assertions.assertEquals(mockId, mockAssetEntity.getId());

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1))
                .existsById(mockId);
        Mockito.verify(assetRepository, Mockito.times(1))
                .deleteById(mockId);

    }

    /**
     * delete-exception
     */
    @Test
    public void givenValidId_whenAssetEntityNotFoundById_thenReturnThrowAssetNotFoundException() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(assetRepository.existsById(mockId))
                .thenReturn(false);

        //Then
        Assertions.assertThrows(AssetNotFoundException.class,
                () -> assetService.delete(mockId));

        //Verify
        Mockito.verify(assetRepository, Mockito.times(1))
                .existsById(mockId);
        Mockito.verify(assetRepository, Mockito.never())
                .deleteById(mockId);
    }

}