package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.model.request.AssetCreateRequest;
import com.flz.model.request.AssetUpdateRequest;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@WebMvcTest(AssetController.class)
class AssetControllerTest extends BaseTest {

    @MockBean
    AssetService assetService;

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * {@link AssetController#findAll(String, BigDecimal, BigDecimal, Boolean, int, int, String, Sort.Direction)}
     */
    @Test
    public void givenFilteringParameters_whenCalledService_thenReturnFilteredAssetsResponseSuccessfully() throws Exception {

        //Given
        int mockPage = 0;
        int mockSize = 10;
        String mockProperty = "id";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        Sort mockSort = Sort.by(mockDirection, mockProperty);
        PageRequest pageRequest = PageRequest
                .of(mockPage, mockSize, mockSort);

        List<AssetsResponse> mockAssetsResponse = getAssetsResponse();

        Page<AssetsResponse> mockAssetsPage = new PageImpl<>
                (mockAssetsResponse, pageRequest, mockAssetsResponse.size());

        Mockito.when(assetService.findAll(
                        Mockito.nullable(String.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(Boolean.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                ))
                .thenReturn(mockAssetsPage);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/assets")
                .param("page", String.valueOf(mockPage))
                .param("size", String.valueOf(mockSize))
                .param("property", mockProperty)
                .param("direction", mockDirection.name())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].id")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].name")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].price")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].isDefault")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].isDefault", Matchers
                        .everyItem(Matchers.isA(Boolean.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));


        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .findAll(
                        Mockito.nullable(String.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(Boolean.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class));
    }

    @Test
    public void givenNameFilter_whenFindAll_thenReturnFilteredAssetsAsNameField() throws Exception {

        //Given

        String mockName = "test";
        int mockPage = 0;
        int mockSize = 10;
        String mockProperty = "id";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        Sort mockSort = Sort.by(mockDirection, mockProperty);
        PageRequest pageRequest = PageRequest.of(mockPage, mockSize, mockSort);

        List<AssetsResponse> mockAssetsResponse = getAssetsResponse();

        Page<AssetsResponse> mockAssetsPage = new PageImpl<>
                (mockAssetsResponse, pageRequest, mockAssetsResponse.size());

        Mockito.when(assetService.findAll(
                        Mockito.anyString(),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(Boolean.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                ))
                .thenReturn(mockAssetsPage);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/assets")
                .param("name", mockName)
                .param("page", String.valueOf(mockPage))
                .param("size", String.valueOf(mockSize))
                .param("property", mockProperty)
                .param("direction", mockDirection.name())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].id")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].name")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].name", Matchers
                        .everyItem(Matchers.containsString(mockName))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].price")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].isDefault")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].isDefault", Matchers
                        .everyItem(Matchers.isA(Boolean.class))));

        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .findAll(
                        Mockito.anyString(),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(Boolean.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class));
    }

    @Test
    public void givenPriceRangeFilter_whenFindAll_thenReturnFilteredAssetsAsBetweenMinPriceAndMaxPrice() throws Exception {

        //Given
        BigDecimal mockMinPrice = BigDecimal.valueOf(1000);
        BigDecimal mockMaxPrice = BigDecimal.valueOf(2500);
        int mockPage = 0;
        int mockSize = 10;
        String mockProperty = "name";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        Sort mockSort = Sort.by(mockDirection, mockProperty);
        PageRequest pageRequest = PageRequest.of(mockPage, mockSize, mockSort);

        List<AssetsResponse> mockAssetsResponse = getAssetsResponse();

        Page<AssetsResponse> mockAssetsPage = new PageImpl<>
                (mockAssetsResponse, pageRequest, mockAssetsResponse.size());

        Mockito.when(assetService.findAll(
                        Mockito.nullable(String.class),
                        Mockito.any(BigDecimal.class),
                        Mockito.any(BigDecimal.class),
                        Mockito.nullable(Boolean.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                ))
                .thenReturn(mockAssetsPage);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/assets")
                .param("minPrice", String.valueOf(mockMinPrice))
                .param("maxPrice", String.valueOf(mockMaxPrice))
                .param("page", "0")
                .param("size", "10")
                .param("property", "name")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].id")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].name")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].isDefault")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].price")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].isDefault", Matchers
                        .everyItem(Matchers.isA(Boolean.class))));

        // Verify
        Mockito.verify(assetService, Mockito.times(1))
                .findAll(
                        Mockito.nullable(String.class),
                        Mockito.any(BigDecimal.class),
                        Mockito.any(BigDecimal.class),
                        Mockito.nullable(Boolean.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                );
    }

    @Test
    void whenMinPriceSmallerThanZero_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/assets")
                .param("minPrice", "-150")
                .param("page", "0")
                .param("size", "10")
                .param("property", "name")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));
    }

    @Test
    void whenMaxPriceSmallerThanZero_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/assets")
                .param("maxPrice", "-100")
                .param("page", "0")
                .param("size", "10")
                .param("property", "name")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));
    }

    @Test
    void whenNameFieldGreaterThanSizeMax_thenReturnBadRequestError() throws Exception {

        //Given
        String mockName = "Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                "when an unknown printer took a galley of type and scrambled it to make a type " +
                "specimen book. It has survived not only five centuries, but also the leap into " +
                "electronic typesetting, remaining essentially unchanged. It was popularised " +
                "in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, " +
                "and more recently with desktop publishing software like Aldus PageMaker including " +
                "versions of Lorem Ipsum.";

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/assets")
                .param("name", mockName)
                .param("page", "0")
                .param("size", "10")
                .param("property", "name")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));
    }

    @Test
    void whenPageSizePropertyDirectionDifferentThanValidValue_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/assets")
                .param("page", "-1")
                .param("size", "10")
                .param("property", "name")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));
    }

    /**
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
        Mockito.when(assetService.findSummaryAll())
                .thenReturn(mockAssetsSummaryResponse);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/assets/summary")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].name", Matchers
                        .everyItem(Matchers.matchesPattern(".*test.*"))));


        //Verify
        Mockito.verify(assetService, Mockito.times(1)).findSummaryAll();

    }

    @Test
    public void givenNonAssets_whenNotFoundSummaryAll_thenReturnEmptyList() throws Exception {

        //When
        List<AssetsSummaryResponse> emptyList = Collections.emptyList();

        Mockito.when(assetService.findSummaryAll()).thenReturn(emptyList);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/assets/summary")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isEmpty());

        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .findSummaryAll();
    }

    /**
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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/asset/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id")
                        .value(mockId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name")
                        .value("test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.price")
                        .value(250))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.isDefault")
                        .value(true));

        //Verify
        Mockito.verify(assetService, Mockito.times(1)).findById(mockId);
    }

    @Test
    public void givenInvalidAssetId_whenNotFoundById_thenReturnBadRequest() throws Exception {

        //Given
        String mockInvalidId = "bu bir invalidId";

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/asset/{id}", mockInvalidId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verify
        Mockito.verify(assetService, Mockito.never())
                .findById(Mockito.any());
    }

    /**
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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/asset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockAssetCreateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
        ;

        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .create(Mockito.any(AssetCreateRequest.class));

    }

    @Test
    public void givenInValidAssetCreateRequestField_whenAssetCreated_thenReturnBadRequestError() throws Exception {

        //Given
        AssetCreateRequest mockAssetCreateRequest = new AssetCreateRequest();
        mockAssetCreateRequest.setName("Kahve Seti");
        mockAssetCreateRequest.setPrice(BigDecimal.valueOf(-250));
        mockAssetCreateRequest.setIsDefault(true);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/asset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockAssetCreateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

    }

    @Test
    public void givenAssetCreateRequestWithMissingFields_whenCreateAsset_thenBadRequestResponse() throws Exception {

        //Given
        AssetCreateRequest mockInvalidRequest = new AssetCreateRequest();
        mockInvalidRequest.setName("");

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/asset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockInvalidRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * {@link AssetController#update(Long, AssetUpdateRequest)}
     */
    @Test
    public void givenValidIdAndAssetRequest_whenFindAssetById_thenUpdateAssetSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        AssetUpdateRequest mockAssetUpdateRequest = new AssetUpdateRequest();
        mockAssetUpdateRequest.setName("updateAsset");
        mockAssetUpdateRequest.setPrice(BigDecimal.valueOf(2000));
        mockAssetUpdateRequest.setIsDefault(false);

        //When
        Mockito.doNothing().when(assetService).update(mockId, mockAssetUpdateRequest);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/asset/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockAssetUpdateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(assetService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(AssetUpdateRequest.class));

    }

    @Test
    void givenInvalidId_whenCalledAssetWithByInvalidId_thenReturnsBadRequestError() throws Exception {

        //Given
        String mockInvalidId = "bu bir InvalidId";

        AssetUpdateRequest mockRequest = new AssetUpdateRequest();
        mockRequest.setName("updateAsset");
        mockRequest.setPrice(BigDecimal.valueOf(2000));
        mockRequest.setIsDefault(false);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/asset/{id}", mockInvalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(assetService, Mockito.never())
                .update(Mockito.any(), Mockito.any());
    }

    @Test
    void givenInvalidAssetUpdateRequestField_whenCalledAssetUpdate_thenReturnsBadRequestError() throws Exception {

        //Given
        Long mockId = 10L;

        AssetUpdateRequest mockRequest = new AssetUpdateRequest();
        mockRequest.setName("Kahve Seti");
        mockRequest.setPrice(BigDecimal.valueOf(-100));
        mockRequest.setIsDefault(false);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/asset/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(assetService, Mockito.never())
                .update(Mockito.any(), Mockito.any());
    }

    /**
     * {@link AssetController#delete(Long)}
     */
    @Test
    void givenValidId_whenCalledDelete_thenDeleteSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        Mockito.doNothing().when(assetService).delete(mockId);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/asset/{id}", mockId);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.time")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));
    }

    @Test
    void givenInValidId_whenCalledDeleteForAsset_thenReturnBadRequest() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/asset/{id}", "lkjhg");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * # Methodized Objects
     */
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