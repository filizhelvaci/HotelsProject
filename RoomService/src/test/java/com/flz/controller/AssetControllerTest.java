package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.model.request.AssetCreateRequest;
import com.flz.service.AssetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        Mockito.verify(assetService, Mockito.times(1)).create(Mockito.any(AssetCreateRequest.class));

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

}