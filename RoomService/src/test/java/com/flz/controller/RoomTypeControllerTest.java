package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.exception.RoomTypeNotFoundException;
import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.model.response.RoomTypeResponse;
import com.flz.model.response.RoomTypesResponse;
import com.flz.model.response.RoomTypesSummaryResponse;
import com.flz.service.RoomTypeService;
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

@WebMvcTest(RoomTypeController.class)
class RoomTypeControllerTest extends BaseTest {

    @MockBean
    RoomTypeService roomTypeService;

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * /room-types
     * findAll()
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

        Page<RoomTypesResponse> mockRoomTypePage = new PageImpl<>(mockRoomTypeResponse, pageRequest, 2);

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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-types")
                        .param("page", String.valueOf(mockPage))
                        .param("pageSize", String.valueOf(mockPageSize))
                        .param("property", mockProperty)
                        .param("direction", mockDirection.name())
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].id").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].size").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].personCount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageable.pageNumber", Matchers.equalTo(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalElements", Matchers.equalTo(2)))
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

        Page<RoomTypesResponse> mockRoomTypesPage =
                new PageImpl<>(mockRoomTypesResponse, pageRequest, mockRoomTypesResponse.size());

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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-types")
                        .param("name", mockName)
                        .param("page", String.valueOf(mockPage))
                        .param("pageSize", String.valueOf(mockPageSize))
                        .param("property", mockProperty)
                        .param("direction", mockDirection.name())
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].id").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].name", Matchers.everyItem(Matchers.containsString("test"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].size").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].personCount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageable.pageNumber", Matchers.equalTo(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalElements", Matchers.equalTo(2)))
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

        Page<RoomTypesResponse> mockRoomTypesPage =
                new PageImpl<>(mockRoomTypesResponse, pageRequest, mockRoomTypesResponse.size());

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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-types")
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].id").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].size").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].personCount").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.pageable.pageNumber", Matchers.equalTo(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.totalElements", Matchers.equalTo(2)));

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

    /**
     * /room-types/summary
     * findSummaryAll()
     * {@link RoomTypeController#findSummaryAll()}
     */
    @Test
    public void whenCallAllSummaryRoomType_thenReturnRoomTypesSummaryResponse() throws Exception {

        //Given
        List<RoomTypesSummaryResponse> mockRoomTypesSummaryResponse =
                List.of(RoomTypesSummaryResponse.builder()
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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-types/summary")
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name").value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()", Matchers.equalTo(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].name", Matchers.everyItem(Matchers.matchesPattern(".*test.*"))));

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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-types/summary")
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isEmpty());

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findSummaryAll();
    }

    @Test
    public void whenFindSummaryAllIsCalledInRoomTypeServiceAndTheServiceFails_thenReturnInternalServerError() throws Exception {

        //When
        Mockito.when(roomTypeService.findSummaryAll())
                .thenThrow(new RuntimeException("An unexpected error occurred"));

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-types/summary")
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false));

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findSummaryAll();
    }

    /**
     * /room-type/{id}
     * findById()
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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-type/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id").value(mockId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.assets").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.name").value("testRoomType"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.price").value(2500))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.size").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.personCount").value(2));

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1)).findById(mockId);
    }

    @Test
    public void givenNonRoomTypeId_whenNotFoundById_thenReturnNotFoundException() throws Exception {

        //Given
        Long mockNonId = 999L;

        //When
        Mockito.when(roomTypeService.findById(mockNonId))
                .thenThrow(new RoomTypeNotFoundException(mockNonId));

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-type/{id}", mockNonId)
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false));

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findById(mockNonId);
    }

    @Test
    public void givenInvalidRoomTypeId_whenNotFoundById_thenReturnBadRequest() throws Exception {

        //Given
        String mockInvalidId = "ukhd-3521";

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-type/{id}", mockInvalidId)
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false));

        //Verify
        Mockito.verify(roomTypeService, Mockito.never())
                .findById(Mockito.any());
    }

    @Test
    public void givenServerError_whenFindById_thenReturnInternalServerError() throws Exception {

        //Given
        Long mockId = 1L;

        //When
        Mockito.when(roomTypeService.findById(mockId))
                .thenThrow(new RuntimeException("Unexpected error"));

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get(BASE_PATH + "/room-type/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false));

    }

    /**
     * /room-type
     * create()
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
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post(BASE_PATH + "/room-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));


        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .create(Mockito.any(RoomTypeCreateRequest.class));

    }

    @Test
    public void givenRoomTypeCreateRequestWithMissingFields_whenCreateRoomType_thenBadRequestResponse() throws Exception {

        //Given
        RoomTypeCreateRequest mockInvalidRequest = new RoomTypeCreateRequest();
        mockInvalidRequest.setName("");

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post(BASE_PATH + "/room-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockInvalidRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false));
    }

    @Test
    public void givenRoomTypeServiceThrowsException_whenCreateRoomType_thenInternalServerErrorResponse() throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();
        roomTypeCreate(mockRoomTypeCreateRequest);

        //When
        Mockito.doThrow(new RuntimeException("Unexpected Error")).when(roomTypeService)
                .create(Mockito.any(RoomTypeCreateRequest.class));

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.post(BASE_PATH + "/room-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(false));
    }

    /**
     * /room-type/{id}
     * update()
     * {@link RoomTypeController#update(Long, RoomTypeUpdateRequest)}
     */
    @Test
    public void givenValidIdAndRoomTypeRequest_whenFindRoomTypeById_thenUpdateRoomTypeSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        RoomTypeUpdateRequest mockroomTypeUpdateRequest = new RoomTypeUpdateRequest();
        roomTypeUpdate(mockroomTypeUpdateRequest);

        //When
        Mockito.doNothing().when(roomTypeService).update(mockId, mockroomTypeUpdateRequest);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.put(BASE_PATH + "/room-type/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockroomTypeUpdateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(RoomTypeUpdateRequest.class));

    }

    @Test
    void givenNonValidId_whenCalledRoomTypeWithNonById_thenReturnsRoomTypeNotFoundException() throws Exception {

        //Given
        Long mockNonId = 999L;

        RoomTypeUpdateRequest mockroomTypeUpdateRequest = new RoomTypeUpdateRequest();
        roomTypeUpdate(mockroomTypeUpdateRequest);

        //When
        Mockito.doThrow(new RoomTypeNotFoundException(mockNonId))
                .when(roomTypeService).update(mockNonId, mockroomTypeUpdateRequest);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.put(BASE_PATH + "/room-type/{id}", mockNonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockroomTypeUpdateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(RoomTypeUpdateRequest.class));

    }

    /**
     * /room-type/{id}
     * delete
     * {@link RoomTypeController#delete(Long)}
     */
    @Test
    void givenValidId_whenCalledRoomTypeServiceDelete_thenDeleteRoomTypeSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        Mockito.doNothing().when(roomTypeService).delete(mockId);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.delete(BASE_PATH + "/room-type/{id}", mockId);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));
    }

    @Test
    void givenNonRoomTypeId_whenCalledRoomTypeServiceDelete_thenReturnRoomTypeNotFoundException() throws Exception {

        //Given
        Long mockId = 99L;

        //When
        Mockito.doThrow(new RoomTypeNotFoundException(mockId))
                .when(roomTypeService).delete(mockId);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.delete(BASE_PATH + "/room-type/{id}", mockId);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenInValidId_whenCalledRoomTypeDelete_thenReturnBadRequest() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.delete(BASE_PATH + "/room-type/{id}", "hahahah");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

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