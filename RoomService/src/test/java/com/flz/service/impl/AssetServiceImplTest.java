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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AssetServiceImplTest {

    @Mock
    AssetRepository assetRepository;

    @InjectMocks
    AssetServiceImpl assetService;

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


        Mockito.when(assetRepository.findById(mockId)).thenReturn(mockAssetEntity);

        AssetResponse assetResponse = assetService.findById(mockId);

        //Then
        assertNotNull(assetResponse);
        assertEquals(mockId, assetResponse.getId());
        Mockito.verify(assetRepository).findById(mockId);
    }

    @Test
    public void givenValidId_whenAssetEntityNotFoundById_throwsAssetNotFoundException() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(assetRepository.findById(mockId)).thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(AssetNotFoundException.class, () -> assetService.findById(mockId));
    }

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
        Mockito.when(assetRepository.findAll()).thenReturn(assetEntities);

        List<AssetsSummaryResponse> result = assetService.findSummaryAll();

        //Then
        assertEquals(assetsSummaryResponses, result);

        Mockito.verify(assetRepository).findAll();

    }


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
        Mockito.when(assetRepository.findAllById(mockIds)).thenReturn(mockAssetEntities);

        List<AssetResponse> mockAssetResponses = assetService.findAllById(mockIds);

        //Then
        assertNotNull(mockAssetResponses);
        assertEquals(mockAssetResponses.get(1).getName(), mockAssetEntities.get(1).getName());
        Mockito.verify(assetRepository).findAllById(mockIds);
    }

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
        Mockito.when(assetRepository.findById(mockId)).thenReturn(Optional.ofNullable(mockAssetEntity));
        if (mockAssetEntity != null) {
            mockAssetEntity.update(mockAssetUpdateRequest.getName(), mockAssetUpdateRequest.getPrice(), mockAssetUpdateRequest.getIsDefault());
            Mockito.when(assetRepository.save(mockAssetEntity)).thenReturn(null);
        }

        assetService.update(mockId, mockAssetUpdateRequest);

        //Then
        Mockito.verify(assetRepository).findById(mockId);
        assert mockAssetEntity != null;
        Mockito.verify(assetRepository).save(mockAssetEntity);

    }

    @Test
    public void givenValidIdAndAssetUpdateRequest_whenAssetEntityNotFoundById_throwsAssetNotFoundException() {

        //Given
        Long mockId = 10L;

        AssetUpdateRequest mockAssetUpdateRequest = new AssetUpdateRequest();
        mockAssetUpdateRequest.setName("updateAsset");
        mockAssetUpdateRequest.setPrice(BigDecimal.valueOf(2000));
        mockAssetUpdateRequest.setIsDefault(false);

        //When
        Mockito.when(assetRepository.findById(mockId)).thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(AssetNotFoundException.class, () -> assetService.update(mockId, mockAssetUpdateRequest));

    }

    @Test
    public void givenAssetCreateRequest_whenAssetByNameAlreadyExists_thenThrowAssetAlreadyExistsException() {

        AssetCreateRequest assetCreateRequest = new AssetCreateRequest();
        assetCreateRequest.setName("testAsset");

        when(assetRepository.existsByName(assetCreateRequest.getName())).thenReturn(true);

        assertThrows(AssetAlreadyExistsException.class, () -> assetService.create(assetCreateRequest));

        verify(assetRepository, never()).save(any());

    }

    @Test
    public void givenValidAssetCreateRequest_whenAssetCreateWithAssetCreateRequest_thenShouldBeSaveAssetEntity() {

        //Given
        AssetCreateRequest assetCreateRequest = new AssetCreateRequest();
        assetCreateRequest.setName("testAsset");
        assetCreateRequest.setPrice(BigDecimal.valueOf(1000));
        assetCreateRequest.setIsDefault(true);

        //When
        when(assetRepository.existsByName(assetCreateRequest.getName())).thenReturn(false);

        AssetEntity assetEntity = AssetCreateRequestToEntityMapper.INSTANCE.map(assetCreateRequest);
        when(assetRepository.save(assetEntity)).thenReturn(null);


        //Then
        assetService.create(assetCreateRequest);

        assertNotNull(assetEntity);
        assertEquals(assetCreateRequest.getName(), assetEntity.getName());
        verify(assetRepository).existsByName(assetCreateRequest.getName());
        verify(assetRepository).save(any(AssetEntity.class));
    }

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
        Mockito.when(assetRepository.existsById(mockId)).thenReturn(true);

        assetService.delete(mockId);

        //Then
        assertNotNull(assetEntity);
        assertEquals(mockId, assetEntity.getId());
        Mockito.verify(assetRepository).existsById(mockId);
        Mockito.verify(assetRepository).deleteById(mockId);

    }

    @Test
    public void givenValidId_whenAssetEntityNotFoundById_thenThrowAssetNotFoundException() {

        //Given
        Long mockId = 10L;

        //When
        when(assetRepository.existsById(mockId)).thenReturn(false);

        //Then
        Assertions.assertThrows(AssetNotFoundException.class, () -> assetService.delete(mockId));

    }

}