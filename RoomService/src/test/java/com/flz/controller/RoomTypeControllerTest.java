package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomTypesResponse;
import com.flz.model.response.RoomTypesSummaryResponse;
import com.flz.service.RoomTypeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(RoomTypeController.class)
class RoomTypeControllerTest extends BaseTest {

    @MockBean
    RoomTypeService roomTypeService;

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * {@link RoomTypeController#findAll(String, BigDecimal, BigDecimal, Integer, Integer, int, int, String, Sort.Direction)}
     */
    @Test
    public void givenFilteringParameters_whenCalledRoomTypeService_thenReturnFilteredRoomTypesResponseSuccessfully() throws Exception {

        //Given
        int mockPage = 0;
        int mockPageSize = 10;
        String mockProperty = "name";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        Sort mockSort = Sort.by(mockDirection, mockProperty);
        PageRequest pageRequest = PageRequest.of(mockPage, mockPageSize, mockSort);

        List<RoomTypesResponse> mockRoomTypeResponse = getRoomTypes();

        Page<RoomTypesResponse> mockRoomTypePage = new PageImpl<>
                (mockRoomTypeResponse, pageRequest, 2);

        Mockito.when(roomTypeService.findAll(
                        Mockito.nullable(String.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(Integer.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                ))
                .thenReturn(mockRoomTypePage);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("page", String.valueOf(mockPage))
                .param("pageSize", String.valueOf(mockPageSize))
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].price")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].size")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].personCount")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageable.pageNumber", Matchers
                        .equalTo(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalElements", Matchers
                        .equalTo(2)))
        ;

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findAll(
                        Mockito.nullable(String.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(Integer.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                );
    }

    @Test
    public void givenNameFilter_whenFindAllMethodCalled_thenReturnFilteredRoomTypesAsNameField() throws Exception {

        //Given
        String mockName = "test";
        int mockPage = 0;
        int mockPageSize = 10;
        String mockProperty = "name";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        Sort mockSort = Sort.by(mockDirection, mockProperty);
        PageRequest pageRequest = PageRequest.of(mockPage, mockPageSize, mockSort);

        List<RoomTypesResponse> mockRoomTypesResponse = getRoomTypes();

        Page<RoomTypesResponse> mockRoomTypesPage = new PageImpl<>
                (mockRoomTypesResponse, pageRequest, mockRoomTypesResponse.size());

        Mockito.when(roomTypeService.findAll(
                        Mockito.anyString(),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(Integer.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                ))
                .thenReturn(mockRoomTypesPage);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("name", mockName)
                .param("page", String.valueOf(mockPage))
                .param("pageSize", String.valueOf(mockPageSize))
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
                        .everyItem(Matchers.containsString("test"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].price")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].size")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].personCount")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageable.pageNumber", Matchers
                        .equalTo(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalElements", Matchers
                        .equalTo(2)))
        ;


        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findAll(
                        Mockito.anyString(),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(BigDecimal.class),
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(Integer.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class));
    }

    @Test
    public void givenPriceRangeFilter_whenFindAllMethodCalled_thenReturnFilteredRoomTypesAsBetweenMinPriceAndMaxPrice() throws Exception {

        //Given
        BigDecimal mockMinPrice = BigDecimal.valueOf(1000);
        BigDecimal mockMaxPrice = BigDecimal.valueOf(5000);
        int mockPage = 0;
        int mockPageSize = 10;
        String mockProperty = "name";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        Sort mockSort = Sort.by(mockDirection, mockProperty);
        PageRequest pageRequest = PageRequest.of(mockPage, mockPageSize, mockSort);

        List<RoomTypesResponse> mockRoomTypesResponse = getRoomTypes();

        Page<RoomTypesResponse> mockRoomTypesPage = new PageImpl<>
                (mockRoomTypesResponse, pageRequest, mockRoomTypesResponse.size());

        Mockito.when(roomTypeService.findAll(
                        Mockito.nullable(String.class),
                        Mockito.any(BigDecimal.class),
                        Mockito.any(BigDecimal.class),
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(Integer.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                ))
                .thenReturn(mockRoomTypesPage);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("minPrice", String.valueOf(mockMinPrice))
                .param("maxPrice", String.valueOf(mockMaxPrice))
                .param("page", String.valueOf(mockPage))
                .param("pageSize", String.valueOf(mockPageSize))
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].price")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].size")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].personCount")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageable.pageNumber", Matchers
                        .equalTo(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalElements", Matchers
                        .equalTo(2)));

        // Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findAll(
                        Mockito.nullable(String.class),
                        Mockito.any(BigDecimal.class),
                        Mockito.any(BigDecimal.class),
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(Integer.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                );
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
                .get(BASE_PATH + "/room-types")
                .param("name", mockName)
                .param("page", "0")
                .param("pageSize", "10")
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
    void whenMinPriceSmallerThanZero_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("minPrice", "-10")
                .param("page", "0")
                .param("pageSize", "10")
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
                .get(BASE_PATH + "/room-types")
                .param("maxPrice", "-10")
                .param("page", "0")
                .param("pageSize", "10")
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
    void whenPersonCountSmallerThanZeroOrGreaterThanMaxValue_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("personCount", "250")
                .param("page", "0")
                .param("pageSize", "10")
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
    void whenSizeSmallerThanZeroOrGreaterThanMaxValue_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("size", "1001")
                .param("page", "0")
                .param("pageSize", "10")
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
    void whenPageDifferentThanValidValue_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("page", "-10")
                .param("pageSize", "10")
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
    void whenSizeDifferentThanValidValue_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("page", "0")
                .param("pageSize", "-10")
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
    void whenPropertyDifferentThanValidValue_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("page", "0")
                .param("pageSize", "10")
                .param("property", "")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));
    }

    @Test
    void whenDirectionDifferentThanValidValue_thenReturnBadRequestError() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types")
                .param("page", "0")
                .param("pageSize", "10")
                .param("property", "name")
                .param("direction", "")
                .contentType(MediaType.APPLICATION_JSON);


        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));
    }


    /**
     * {@link RoomTypeController#findSummaryAll()}
     */
    @Test
    public void whenCallAllSummaryRoomType_thenReturnRoomTypesSummaryResponse() throws Exception {

        //Given
        List<RoomTypesSummaryResponse> mockRoomTypesSummaryResponse = List.of(
                RoomTypesSummaryResponse.builder()
                        .id(11L)
                        .name("test1")
                        .build(),
                RoomTypesSummaryResponse.builder()
                        .id(12L)
                        .name("test2")
                        .build(),
                RoomTypesSummaryResponse.builder()
                        .id(13L)
                        .name("test3")
                        .build()
        );

        //When
        Mockito.when(roomTypeService.findSummaryAll())
                .thenReturn(mockRoomTypesSummaryResponse);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types/summary")
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()", Matchers
                        .equalTo(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].name", Matchers
                        .everyItem(Matchers.matchesPattern(".*test.*"))));

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1)).findSummaryAll();

    }

    @Test
    public void whenNotFoundSummaryAllRoomTypesCalled_thenReturnEmptyList() throws Exception {

        //When
        List<RoomTypesSummaryResponse> mockEmptyList = Collections.emptyList();

        Mockito.when(roomTypeService.findSummaryAll())
                .thenReturn(mockEmptyList);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-types/summary")
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
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findSummaryAll();
    }

    /**
     * {@link RoomTypeController#findById(Long)}
     */
    @Test
    public void givenValidId_whenFindRoomTypeById_thenReturnRoomTypeResponse() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        List<RoomTypeResponse.Asset> mockAssets = List.of(
                RoomTypeResponse.Asset.builder().id(1L).name("Yatak Seti").build(),
                RoomTypeResponse.Asset.builder().id(2L).name("55 inç LCD TV").build(),
                RoomTypeResponse.Asset.builder().id(3L).name("Wifi").build(),
                RoomTypeResponse.Asset.builder().id(4L).name("Çay/kahve makinesi").build()
        );

        RoomTypeResponse mockRoomTypeResponse = RoomTypeResponse.builder()
                .id(10L)
                .name("testRoomType")
                .price(BigDecimal.valueOf(2500))
                .size(50)
                .personCount(2)
                .description("this is a description")
                .assets(mockAssets)
                .build();

        Mockito.when(roomTypeService.findById(mockId))
                .thenReturn(mockRoomTypeResponse);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-type/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id")
                        .value(mockId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.assets")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name")
                        .value("testRoomType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.price")
                        .value(2500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.size")
                        .value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.personCount")
                        .value(2));

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1)).findById(mockId);
    }

    @Test
    public void givenInvalidRoomTypeId_whenNotFoundById_thenReturnBadRequest() throws Exception {

        //Given
        String mockInvalidId = "ukhd-3521";

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room-type/{id}", mockInvalidId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(roomTypeService, Mockito.never())
                .findById(Mockito.any());
    }

    /**
     * {@link RoomTypeController#create(RoomTypeCreateRequest)}
     */
    @Test
    public void givenValidRoomTypeCreateRequest_whenRoomTypeCreated_thenSuccessResponse() throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();
        roomTypeCreate(mockRoomTypeCreateRequest);

        //When
        Mockito.doNothing().when(roomTypeService).create(mockRoomTypeCreateRequest);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));


        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .create(Mockito.any(RoomTypeCreateRequest.class));

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            "f",
            " ",
            "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed in"
    })
    void givenInvalidName_whenRoomTypeCreateRequest_thenBadRequestResponse(String invalidRequest) throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();
        mockRoomTypeCreateRequest.setName(invalidRequest);
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(1500));

        mockRoomTypeCreateRequest.setPersonCount(2);
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setDescription("this is a description");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeCreateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never()).create(Mockito.any());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "-1",
            "10000001"
    })
    void givenInvalidPrice_whenRoomTypeCreateRequest_thenBadRequestResponse(String invalidRequest) throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();

        if (invalidRequest != null) {
            mockRoomTypeCreateRequest.setPrice(new BigDecimal(invalidRequest));
        } else {
            mockRoomTypeCreateRequest.setPrice(null);
        }

        mockRoomTypeCreateRequest.setName("Delux Room");
        mockRoomTypeCreateRequest.setPersonCount(2);
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setDescription("this is a description");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeCreateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never()).create(Mockito.any());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1",
            "101"
    })
    void givenInvalidPersonCount_whenRoomTypeCreateRequest_thenBadRequestResponse(String invalidRequest) throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();

        mockRoomTypeCreateRequest.setName("Delux Room");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setPersonCount(Integer.valueOf(invalidRequest));
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setDescription("this is a description");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeCreateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never()).create(Mockito.any());
    }

    @Test
    public void givenRoomTypeCreateRequestWithNullPersonCount_whenCreateRoomType_thenBadRequestResponse() throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();
        mockRoomTypeCreateRequest.setName("Delux Room");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setPersonCount(null);
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setDescription("this is a description");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeCreateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never()).create(Mockito.any());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1",
            "1001"
    })
    void givenInvalidSize_whenRoomTypeCreateRequest_thenBadRequestResponse(String invalidRequest) throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();

        mockRoomTypeCreateRequest.setName("Delux Room");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setPersonCount(3);
        mockRoomTypeCreateRequest.setSize(Integer.valueOf(invalidRequest));
        mockRoomTypeCreateRequest.setDescription("this is a description");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeCreateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never()).create(Mockito.any());
    }

    @Test
    public void givenRoomTypeCreateRequestWithNullSize_whenCreateRoomType_thenBadRequestResponse() throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();
        mockRoomTypeCreateRequest.setName("Delux Room");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setPersonCount(3);
        mockRoomTypeCreateRequest.setSize(null);
        mockRoomTypeCreateRequest.setDescription("this is a description");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeCreateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never()).create(Mockito.any());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into " +
                    "a horrible vermin. He lay on his armour-like back, and if he lifted his head a little he could " +
                    "see his brown belly, slightly domed and divided by arches into stiff sections. The bedding was " +
                    "hardly able to cover it and seemed ready to slide off any moment. His many legs, pitifully thin " +
                    "compared with the size of the rest of him, waved about helplessly as he looked.What's happened " +
                    "to me? he thought.It wasn't a dream. His room, a proper human room although a little too small, " +
                    "lay peacefully between its four familiar walls. A collection of textile samples lay spread out " +
                    "on the table - Samsa was a travelling salesman - and above it there hung a picture that he had " +
                    "recently cut out of an illustrated magazine and housed in a nice, gilded frame.It showed a lady " +
                    "fitted out with a fur hat and fur boa who sat upright, raising a heavy fur muff that covered the" +
                    " whole of her lower arm towards the viewer. Gregor then turned "
    })
    void givenInvalidDescription_whenRoomTypeCreateRequest_thenBadRequestResponse(String invalidRequest) throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();

        mockRoomTypeCreateRequest.setName("Delux Room");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setPersonCount(3);
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setDescription(invalidRequest);
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeCreateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never()).create(Mockito.any());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            ""
    })
    void givenEmptyAssetsIdList_whenRoomTypeCreateRequest_thenBadRequestResponse(String invalidRequest) throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();

        mockRoomTypeCreateRequest.setName("Delux Room");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setPersonCount(3);
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setDescription("this is a description");

        List<Long> assetIds = null;
        if (invalidRequest != null) {
            assetIds = new ArrayList<>();
        }
        mockRoomTypeCreateRequest.setAssetIds(assetIds);

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room-type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeCreateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never()).create(Mockito.any());
    }

    /**
     * {@link RoomTypeController#update(Long, RoomTypeUpdateRequest)}
     */
    @Test
    public void givenValidIdAndRoomTypeUpdateRequest_whenFindRoomTypeById_thenUpdateRoomTypeSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        RoomTypeUpdateRequest mockroomTypeUpdateRequest = new RoomTypeUpdateRequest();
        roomTypeUpdate(mockroomTypeUpdateRequest);

        //When
        Mockito.doNothing().when(roomTypeService).update(mockId, mockroomTypeUpdateRequest);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/room-type/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockroomTypeUpdateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(RoomTypeUpdateRequest.class));

    }

    @Test
    void givenInValidId_whenCalledRoomTypeUpdateWithByInvalidId_thenReturnsBadRequestError() throws Exception {

        //Given
        String mockInvalidId = "uhjg";

        RoomTypeUpdateRequest mockroomTypeUpdateRequest = new RoomTypeUpdateRequest();
        roomTypeUpdate(mockroomTypeUpdateRequest);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/room-type/{id}", mockInvalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockroomTypeUpdateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(roomTypeService, Mockito.never())
                .update(Mockito.any(), Mockito.any(RoomTypeUpdateRequest.class));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            "f",
            " ",
            "One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a"
    })
    void givenInvalidName_whenRoomTypeUpdateRequest_thenBadRequestResponse(String invalidRequest) throws Exception {

        //Given
        Long mockId = 10L;

        RoomTypeUpdateRequest mockRoomTypeUpdateRequest = new RoomTypeUpdateRequest();
        mockRoomTypeUpdateRequest.setName(invalidRequest);
        mockRoomTypeUpdateRequest.setPrice(BigDecimal.valueOf(1500));

        mockRoomTypeUpdateRequest.setPersonCount(2);
        mockRoomTypeUpdateRequest.setSize(50);
        mockRoomTypeUpdateRequest.setDescription("this is a description");
        mockRoomTypeUpdateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/room-type/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeUpdateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeUpdateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never())
                .update(Mockito.any(), Mockito.any(RoomTypeUpdateRequest.class));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "-1",
            "100000001",
    })
    void givenInvalidPrice_whenRoomTypeUpdateRequest_thenBadRequestResponse(String invalidRequest) throws Exception {

        //Given
        Long mockId = 10L;

        RoomTypeUpdateRequest mockRoomTypeUpdateRequest = new RoomTypeUpdateRequest();

        if (invalidRequest != null) {
            mockRoomTypeUpdateRequest.setPrice(new BigDecimal(invalidRequest));
        } else {
            mockRoomTypeUpdateRequest.setPrice(null);
        }

        mockRoomTypeUpdateRequest.setName("Standart Room");
        mockRoomTypeUpdateRequest.setPersonCount(2);
        mockRoomTypeUpdateRequest.setSize(50);
        mockRoomTypeUpdateRequest.setDescription("this is a description");
        mockRoomTypeUpdateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/room-type/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeUpdateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeUpdateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never())
                .update(Mockito.any(), Mockito.any(RoomTypeUpdateRequest.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "-1",
            "101",
    })
    void givenInvalidPersonCount_whenRoomTypeUpdateRequest_thenBadRequestResponse(String invalidRequest) throws Exception {

        //Given
        Long mockId = 10L;

        RoomTypeUpdateRequest mockRoomTypeUpdateRequest = new RoomTypeUpdateRequest();
        mockRoomTypeUpdateRequest.setName("Standart Room");
        mockRoomTypeUpdateRequest.setPersonCount(Integer.valueOf(invalidRequest));
        mockRoomTypeUpdateRequest.setSize(50);
        mockRoomTypeUpdateRequest.setDescription("this is a description");
        mockRoomTypeUpdateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/room-type/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeUpdateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeUpdateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never())
                .update(Mockito.any(), Mockito.any(RoomTypeUpdateRequest.class));
    }

    @Test
    void givenNullPersonCount_whenRoomTypeUpdateRequest_thenBadRequestResponse() throws Exception {

        //Given
        Long mockId = 10L;

        RoomTypeUpdateRequest mockRoomTypeUpdateRequest = new RoomTypeUpdateRequest();
        mockRoomTypeUpdateRequest.setName("Standart Room");
        mockRoomTypeUpdateRequest.setPersonCount(null);
        mockRoomTypeUpdateRequest.setSize(50);
        mockRoomTypeUpdateRequest.setDescription("this is a description");
        mockRoomTypeUpdateRequest.setAssetIds(List.of(1L, 3L, 5L));

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/room-type/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomTypeUpdateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.field")
                        .value("roomTypeUpdateRequest"));

        // Verify
        Mockito.verify(roomTypeService, Mockito.never())
                .update(Mockito.any(), Mockito.any(RoomTypeUpdateRequest.class));
    }

    @ParameterizedTest
    @MethodSource("invalidRoomTypeUpdateRequests")
    void givenInvalidRoomTypeUpdateRequests_whenUpdateRoomType_thenBadRequestResponse(RoomTypeUpdateRequest invalidRequest) throws Exception {

        //Given
        long mockId = 10L;

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/room-type/" + mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false));
    }

    private static Stream<Arguments> invalidRoomTypeUpdateRequests() {
        return Stream.of(
                Arguments.of(new RoomTypeUpdateRequest(null, BigDecimal.valueOf(10000), 10, 200, "Valid Desc", List.of(1L))),
                Arguments.of(new RoomTypeUpdateRequest("R", BigDecimal.valueOf(10000), 10, 200, "Valid Desc", List.of(1L))),
                Arguments.of(new RoomTypeUpdateRequest("Valid Name", null, 10, 200, "Valid Desc", List.of(1L))),
                Arguments.of(new RoomTypeUpdateRequest("Valid Name", BigDecimal.valueOf(100000001), 10, 200, "Valid Desc", List.of(1L))),
                Arguments.of(new RoomTypeUpdateRequest("Valid Name", BigDecimal.valueOf(10000), null, 200, "Valid Desc", List.of(1L))),
                Arguments.of(new RoomTypeUpdateRequest("Valid Name", BigDecimal.valueOf(10000), 10, 200, "", List.of(1L))),
                Arguments.of(new RoomTypeUpdateRequest("Valid Name", BigDecimal.valueOf(10000), 10, 200, "Valid Desc", List.of()))
        );
    }

    /**
     * {@link RoomTypeController#delete(Long)}
     */
    @Test
    void givenValidId_whenCalledRoomTypeServiceDelete_thenDeleteRoomTypeSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        Mockito.doNothing().when(roomTypeService).delete(mockId);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/room-type/{id}", mockId);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));
    }

    @Test
    void givenInValidId_whenCalledRoomTypeDelete_thenReturnBadRequest() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/room-type/{id}", "I am InvalidId");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

    }

    /**
     * # Methodized Objects
     */
    private static void roomTypeCreate(RoomTypeCreateRequest mockRoomTypeCreateRequest) {
        mockRoomTypeCreateRequest.setName("testRoomType");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setPersonCount(2);
        mockRoomTypeCreateRequest.setDescription("This description for Test was written !");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 2L, 3L));
    }

    private static void roomTypeUpdate(RoomTypeUpdateRequest mockroomTypeUpdateRequest) {
        mockroomTypeUpdateRequest.setName("testUpdateRoomType");
        mockroomTypeUpdateRequest.setPrice(BigDecimal.valueOf(1500));
        mockroomTypeUpdateRequest.setSize(35);
        mockroomTypeUpdateRequest.setPersonCount(2);
        mockroomTypeUpdateRequest.setDescription("This description for Test was written !");
        mockroomTypeUpdateRequest.setAssetIds(List.of(1L, 2L, 3L));
    }

    private static List<RoomTypesResponse> getRoomTypes() {
        return List.of(RoomTypesResponse.builder()
                        .id(10L)
                        .name("test1 Room Type")
                        .size(50)
                        .personCount(2)
                        .price(BigDecimal.valueOf(2000))
                        .build(),
                RoomTypesResponse.builder()
                        .id(11L)
                        .name("test2 Room Type")
                        .size(60)
                        .personCount(3)
                        .price(BigDecimal.valueOf(3000))
                        .build()
        );
    }
}