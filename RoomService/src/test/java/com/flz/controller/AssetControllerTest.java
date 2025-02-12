package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.exception.AssetNotFoundException;
import com.flz.model.request.AssetCreateRequest;
import com.flz.model.response.AssetResponse;
import com.flz.model.response.AssetsResponse;
import com.flz.model.response.AssetsSummaryResponse;
import com.flz.service.AssetService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssetController.class)
class AssetControllerTest extends BaseTest {

    @MockBean
    AssetService assetService;

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * Create-Controller
     * {@link AssetController#create(AssetCreateRequest)}
     */
    @Test
    public void givenValidAssetCreateRequest_whenAssetCreated_thenSuccessResponse() throws Exception {

        //Given
        AssetCreateRequest mockAssetCreateRequest = new AssetCreateRequest();
        mockAssetCreateRequest.setName("test");
        mockAssetCreateRequest.setPrice(BigDecimal.valueOf(250));
        mockAssetCreateRequest.setIsDefault(true);

        //When
        Mockito.doNothing().when(assetService).create(Mockito.any(AssetCreateRequest.class));

        //Then
        mockMvc.perform(post(BASE_PATH + "/asset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockAssetCreateRequest)))
                .andExpect(status().isOk());

        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .create(Mockito.any(AssetCreateRequest.class));

    }

    @Test
    public void givenAssetCreateRequestWithMissingFields_whenCreateAsset_thenBadRequestResponse() throws Exception {

        //Given
        AssetCreateRequest invalidRequest = new AssetCreateRequest();
        invalidRequest.setName("");

        //When
        mockMvc.perform(post(BASE_PATH + "/asset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAssetServiceThrowsException_whenCreateAsset_thenInternalServerErrorResponse() throws Exception {

        //Given
        AssetCreateRequest mockAssetCreateRequest = new AssetCreateRequest();
        mockAssetCreateRequest.setName("test");
        mockAssetCreateRequest.setPrice(BigDecimal.valueOf(250));
        mockAssetCreateRequest.setIsDefault(true);

        //When
        Mockito.doThrow(new RuntimeException("Unexpected Error")).when(assetService)
                .create(Mockito.any(AssetCreateRequest.class));

        //Then
        mockMvc.perform(post(BASE_PATH + "/asset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockAssetCreateRequest)))
                .andExpect(status().isInternalServerError());
    }

    /**
     * findById
     * {@link AssetController#findById(Long)}
     */
    @Test
    public void givenValidId_whenFindAssetById_thenReturnAssetResponse() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        AssetResponse mockAssetResponse = AssetResponse.builder()
                .id(10L)
                .name("test")
                .price(BigDecimal.valueOf(250))
                .isDefault(true)
                .build();

        Mockito.when(assetService.findById(mockId)).thenReturn(mockAssetResponse);

        //Then
        mockMvc.perform(get(BASE_PATH + "/asset/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(mockId))
                .andExpect(jsonPath("$.response.name").value("test"))
                .andExpect(jsonPath("$.response.price").value(250))
                .andExpect(jsonPath("$.response.isDefault").value(true));

        //Verify
        Mockito.verify(assetService, Mockito.times(1)).findById(mockId);
    }

    @Test
    public void givenNonAssetId_whenNotFoundById_thenReturnNotFoundException() throws Exception {

        //Given
        Long nonAssetId = 999L;

        //When
        Mockito.when(assetService.findById(nonAssetId))
                .thenThrow(new AssetNotFoundException(nonAssetId));

        //Then
        mockMvc.perform(get(BASE_PATH + "/asset/{id}", nonAssetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .findById(nonAssetId);
    }

    @Test
    public void givenInvalidAssetId_whenNotFoundById_thenReturnBadRequest() throws Exception {

        //Given
        String invalidAssetId = "ukhd-3521";

        //Then
        mockMvc.perform(get(BASE_PATH + "/asset/{id}", invalidAssetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //Verify
        Mockito.verify(assetService, Mockito.never())
                .findById(Mockito.any());
    }

    @Test
    public void givenServerError_whenFindById_thenReturnInternalServerError() throws Exception {

        //Given
        Long mockId = 1L;

        //When
        Mockito.when(assetService.findById(mockId))
                .thenThrow(new RuntimeException("Unexpected error"));

        //Then
        mockMvc.perform(get(BASE_PATH + "/asset/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }

    /**
     * findSummaryAll()
     * {@link AssetService#findSummaryAll()}
     */
    @Test
    public void whenCallAllSummaryAsset_thenReturnAssetsSummaryResponse() throws Exception {

        //Given
        List<AssetsSummaryResponse> mockAssetsSummaryResponse =
                List.of(AssetsSummaryResponse.builder()
                                .id(11L)
                                .name("test1")
                                .build(),
                        AssetsSummaryResponse.builder()
                                .id(12L)
                                .name("test2")
                                .build(),
                        AssetsSummaryResponse.builder()
                                .id(13L)
                                .name("test3")
                                .build()
                );

        //When
        Mockito.when(assetService.findSummaryAll()).thenReturn(mockAssetsSummaryResponse);

        //Then
        mockMvc.perform(get(BASE_PATH + "/assets/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.response").isArray())
                .andDo(print());

        //Verify
        Mockito.verify(assetService, Mockito.times(1)).findSummaryAll();

    }

    @Test
    public void givenNonAssets_whenNotFoundSummaryAll_thenReturnEmptyList() throws Exception {

        //Given
        List<AssetsSummaryResponse> emptyList = Collections.emptyList();

        //When
        Mockito.when(assetService.findSummaryAll())
                .thenReturn(emptyList);

        //Then
        mockMvc.perform(get(BASE_PATH + "/assets/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response").isEmpty());

        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .findSummaryAll();
    }

    @Test
    public void whenFindSummaryAllIsCalledAndTheServiceFails_henReturnInternalServerError() throws Exception {

        //When
        Mockito.when(assetService.findSummaryAll())
                .thenThrow(new RuntimeException("Beklenmeyen bir hata oluştu"));

        //Then
        mockMvc.perform(get(BASE_PATH + "/assets/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.isSuccess").value(false));

        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .findSummaryAll();

    }

    /**
     * findAll()
     * {@link AssetController#findAll(String, BigDecimal, BigDecimal, Boolean, int, int, String, Sort.Direction)}
     */
    @Test
    public void givenFilteringParameters_whenCalledService_thenReturnFilteredAssetsResponseSuccessfully() throws Exception {

        //Given
        int page = 0;
        int size = 10;
        String property = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        Sort sort = Sort.by(direction, property);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        List<AssetsResponse> mockAssetsResponse = getAssetsResponse();

        Page<AssetsResponse> mockAssetsPage =
                new PageImpl<>(mockAssetsResponse, pageRequest, mockAssetsResponse.size());

        Mockito.when(assetService.findAll(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(mockAssetsPage);

        //Then
        mockMvc.perform(get(BASE_PATH + "/assets")
                        .param("page", "0")
                        .param("size", "10")
                        .param("property", "name")
                        .param("direction", Sort.Direction.ASC.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andDo(print());

        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .findAll(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any());
    }

    @Test
    public void givenNameFilter_whenFindAll_thenReturnFilteredAssetsAsNameField() throws Exception {

        //Given
        int page = 0;
        int size = 10;
        String property = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        Sort sort = Sort.by(direction, property);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        List<AssetsResponse> mockAssetsResponse = getAssetsResponse();

        Page<AssetsResponse> mockAssetsPage =
                new PageImpl<>(mockAssetsResponse, pageRequest, mockAssetsResponse.size());

        Mockito.when(assetService.findAll(Mockito.eq("test"), Mockito.any(), Mockito.any(),
                        Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(mockAssetsPage);

        //Then
        mockMvc.perform(get(BASE_PATH + "/assets")
                        .param("name", "test")
                        .param("page", "0")
                        .param("size", "10")
                        .param("property", "name")
                        .param("direction", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content[0].name").value(Matchers.matchesPattern(".*test.*")));


        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .findAll(Mockito.eq("test"), Mockito.any(), Mockito.any(),
                        Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any());
    }

    private static List<AssetsResponse> getAssetsResponse() {
        return List.of(AssetsResponse.builder()
                        .id(10L)
                        .name("test1")
                        .price(BigDecimal.valueOf(300))
                        .isDefault(true)
                        .build(),
                AssetsResponse.builder()
                        .id(11L)
                        .name("test2")
                        .price(BigDecimal.valueOf(500))
                        .isDefault(true)
                        .build(),
                AssetsResponse.builder()
                        .id(12L)
                        .name("test3")
                        .price(BigDecimal.valueOf(800))
                        .isDefault(true)
                        .build()
        );
    }

}