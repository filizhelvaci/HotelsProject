package com.flz.service.impl;

import com.flz.exception.AssetAlreadyExistsException;
import com.flz.exception.AssetNotFoundException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.mapper.AssetCreateRequestToEntityMapper;
import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsSummaryResponse;
import com.flz.repository.AssetRepository;
import com.flz.service.impl.com.flz.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;


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

        //Given
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

        List<AssetsSummaryResponse> assetsSummaryResponses = List.of(
                AssetsSummaryResponse.builder()
                        .id(10L)
                        .name("testAsset1")
                        .build(),
                AssetsSummaryResponse.builder()
                        .id(11L)
                        .name("testAsset2")
                        .build(),
                AssetsSummaryResponse.builder()
                        .id(12L)
                        .name("testAsset3")
                        .build()
        );

        //When
        Mockito.when(assetRepository.findAll())
                .thenReturn(assetEntities);

        List<AssetsSummaryResponse> result = assetService.findSummaryAll();

        //Then
        Assertions.assertEquals(assetsSummaryResponses, result);

        //Verify
        Mockito.verify(assetRepository).findAll();

    }

    /**
     * findAllById
     */
    @Test
    public void givenValidAssetIdList__whenAssetEntitiesFoundAccordingToThatIdList_thenReturnAssetResponseList() {

        //Given
        List<Long> mockIds = List.of(10L, 11L, 12L);

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

        //When
        Mockito.when(assetRepository.findAllById(mockIds))
                .thenReturn(mockAssetEntities);

        List<AssetResponse> mockAssetResponses = assetService.findAllById(mockIds);

        //Then
        Assertions.assertNotNull(mockAssetResponses);
        Assertions.assertEquals(mockAssetResponses.get(1).getName(), mockAssetEntities.get(1).getName());

        //Verify
        Mockito.verify(assetRepository).findAllById(mockIds);
    }

    /**
     * findById
     */
    @Test
    public void givenValidId_whenAssetEntityFoundAccordingToThatId_thenReturnAssetResponse() {

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
        Mockito.verify(assetRepository).findById(mockId);
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
        Assertions.assertThrows(AssetNotFoundException.class,
                () -> assetService.findById(mockId));

        //Verify
    }

    /**
     * create
     */
    @Test
    public void givenValidAssetCreateRequest_whenAssetCreateWithAssetCreateRequest_thenShouldBeSaveAssetEntity() {

        //Given
        AssetCreateRequest assetCreateRequest = new AssetCreateRequest();
        assetCreateRequest.setName("testAsset");
        assetCreateRequest.setPrice(BigDecimal.valueOf(1000));
        assetCreateRequest.setIsDefault(true);

        //When
        Mockito.when(assetRepository.existsByName(assetCreateRequest.getName()))
                .thenReturn(false);

        AssetEntity assetEntity = AssetCreateRequestToEntityMapper.INSTANCE.map(assetCreateRequest);
        Mockito.when(assetRepository.save(assetEntity))
                .thenReturn(null);

        assetService.create(assetCreateRequest);

        //Then
        Assertions.assertNotNull(assetEntity);
        Assertions.assertEquals(assetCreateRequest.getName(), assetEntity.getName());

        //Verify
        Mockito.verify(assetRepository).existsByName(assetCreateRequest.getName());
        Mockito.verify(assetRepository).save(any(AssetEntity.class));
    }

    /**
     *  create-exception
     */
    @Test
    public void givenAssetCreateRequest_whenAssetByNameAlreadyExists_thenThrowAssetAlreadyExistsException() {

        //Given
        AssetCreateRequest assetCreateRequest = new AssetCreateRequest();
        assetCreateRequest.setName("testAsset");

        //When
        Mockito.when(assetRepository.existsByName(assetCreateRequest.getName()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(AssetAlreadyExistsException.class,
                () -> assetService.create(assetCreateRequest));

        //Verify
        Mockito.verify(assetRepository, never()).save(any());

    }

    /**
     *  update
     */
    @Test
    public void givenValidAssetIdAndAssetUpdateRequest_whenAssetEntityFoundAccordingToThatId_thenThatAssetEntityUpdate() {

        //Given
        Long mockId = 10L;

        AssetEntity mockAssetEntity = AssetEntity.builder()
                .id(mockId)
                .name("testAsset")
                .price(BigDecimal.valueOf(1000))
                .isDefault(true)
                .build();

        AssetUpdateRequest mockAssetUpdateRequest = new AssetUpdateRequest();
        mockAssetUpdateRequest.setName("updateAsset");
        mockAssetUpdateRequest.setPrice(BigDecimal.valueOf(2000));
        mockAssetUpdateRequest.setIsDefault(false);

        //When
        Mockito.when(assetRepository.findById(mockId))
                .thenReturn(Optional.ofNullable(mockAssetEntity));
        if (mockAssetEntity != null) {
            mockAssetEntity.update(mockAssetUpdateRequest.getName(), mockAssetUpdateRequest.getPrice(), mockAssetUpdateRequest.getIsDefault());
            Mockito.when(assetRepository.save(mockAssetEntity)).thenReturn(null);
        }

        assetService.update(mockId, mockAssetUpdateRequest);

        //Then

        //Verify
        Mockito.verify(assetRepository).findById(mockId);
        assert mockAssetEntity != null;
        Mockito.verify(assetRepository).save(mockAssetEntity);

    }

    /**
     *  update-exception
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

    }

    /**
     *  delete
     */
    @Test
    public void givenValidId_whenAssetEntityFoundById_thenDeleteAssetEntity() {

        //Given
        Long mockId = 10L;

        AssetEntity assetEntity = AssetEntity.builder()
                .id(mockId)
                .name("testAsset")
                .price(BigDecimal.valueOf(1000))
                .isDefault(true)
                .build();

        //When
        Mockito.when(assetRepository.existsById(mockId))
                .thenReturn(true);

        assetService.delete(mockId);

        //Then
        Assertions.assertNotNull(assetEntity);
        Assertions.assertEquals(mockId, assetEntity.getId());

        //Verify
        Mockito.verify(assetRepository).existsById(mockId);
        Mockito.verify(assetRepository).deleteById(mockId);

    }

    /**
     *  delete-exception
     */
    @Test
    public void givenValidId_whenAssetEntityNotFoundById_thenThrowAssetNotFoundException() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(assetRepository.existsById(mockId))
                .thenReturn(false);

        //Then
        Assertions.assertThrows(AssetNotFoundException.class,
                () -> assetService.delete(mockId));

        //Verify
    }

}