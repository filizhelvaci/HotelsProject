package com.flz.service.impl;

import com.flz.exception.AssetAlreadyExistsException;
import com.flz.model.entity.AssetEntity;
import com.flz.model.mapper.AssetCreateRequestToEntityMapper;
import com.flz.model.request.AssetCreateRequest;
import com.flz.repository.AssetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AssetServiceImplTest {

    @Mock
    AssetRepository assetRepository;

    @Mock
    AssetCreateRequestToEntityMapper assetCreateRequestToEntityMapper;

    @InjectMocks
    AssetServiceImpl assetService;


    @Test
    public void Should_Return_ThrowException_When_AssetAlreadyExists() {

        AssetCreateRequest assetCreateRequest = new AssetCreateRequest();
        assetCreateRequest.setName("testAsset");

        when(assetRepository.existsByName(assetCreateRequest.getName())).thenReturn(true);

        assertThrows(AssetAlreadyExistsException.class, () -> assetService.create(assetCreateRequest));

        verify(assetRepository, never()).save(any());

    }

    @Test
    public void WhenSaveAssetCreateRequest__ShouldBeAssetEntity() {
        // arrange
        AssetCreateRequest assetCreateRequest = new AssetCreateRequest();
        assetCreateRequest.setName("testAsset");
        assetCreateRequest.setPrice(BigDecimal.valueOf(1000));
        assetCreateRequest.setIsDefault(true);

        when(assetRepository.existsByName(assetCreateRequest.getName())).thenReturn(false);

        when(assetCreateRequestToEntityMapper.map(assetCreateRequest))
                .thenReturn(AssetEntity.builder()
                        .name("testAsset")
                        .price(BigDecimal.valueOf(1000))
                        .isDefault(true)
                        .build());
        // act
        assetService.create(assetCreateRequest);

        // assert
        verify(assetRepository).save(any(AssetEntity.class));
    }

}