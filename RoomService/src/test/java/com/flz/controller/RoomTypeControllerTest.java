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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomTypeController.class)
class RoomTypeControllerTest extends BaseTest {

    @MockBean
    RoomTypeService roomTypeService;

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * /room-types
     * findAll()
     * {@link RoomTypeController#findAll(String, BigDecimal, BigDecimal, Integer, Integer, int, int, String, Sort.Direction)}
     */
    @Test
    public void givenFilteringParameters_whenCalledRoomTypeService_thenReturnFilteredRoomTypesResponseSuccessfully() throws Exception {

        //Given
        int page = 0;
        int pageSize = 10;
        String property = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        Sort sort = Sort.by(direction, property);
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);

        List<RoomTypesResponse> mockRoomTypeResponse = List.of(RoomTypesResponse.builder()
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

        Page<RoomTypesResponse> mockRoomTypePage = new PageImpl<>(mockRoomTypeResponse, pageRequest, 2);

        Mockito.when(roomTypeService.findAll(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any())).thenReturn(mockRoomTypePage);

        //Then
        mockMvc.perform(get(BASE_PATH + "/room-types")
                        .param("page", "0")
                        .param("pageSize", "10")
                        .param("property", "name")
                        .param("direction", Sort.Direction.ASC.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andDo(print());

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findAll(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any());
    }

    @Test
    public void givenNameFilter_whenFindAllMethodCalled_thenReturnFilteredRoomTypesAsNameField() throws Exception {

        //Given
        int page = 0;
        int pageSize = 10;
        String property = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        Sort sort = Sort.by(direction, property);
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);

        List<RoomTypesResponse> mockRoomTypesResponse = List.of(RoomTypesResponse.builder()
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

        Page<RoomTypesResponse> mockRoomTypesPage =
                new PageImpl<>(mockRoomTypesResponse, pageRequest, mockRoomTypesResponse.size());

        Mockito.when(roomTypeService.findAll(Mockito.eq("test"), Mockito.any(), Mockito.any(), Mockito.any(),
                        Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(mockRoomTypesPage);

        //Then
        mockMvc.perform(get(BASE_PATH + "/room-types")
                        .param("name", "test")
                        .param("page", "0")
                        .param("pageSize", "10")
                        .param("property", "name")
                        .param("direction", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content[0].name").value(Matchers.matchesPattern(".*test.*")));


        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findAll(Mockito.eq("test"), Mockito.any(), Mockito.any(), Mockito.any(),
                        Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any());
    }

    @Test
    public void givenPriceRangeFilter_whenFindAllMethodCalled_thenReturnFilteredRoomTypesAsBetweenMinPriceAndMaxPrice() throws Exception {

        //Given
        int page = 0;
        int pageSize = 10;
        String property = "name";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        Sort sort = Sort.by(direction, property);
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);

        List<RoomTypesResponse> mockRoomTypesResponse = List.of(RoomTypesResponse.builder()
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

        Page<RoomTypesResponse> mockRoomTypesPage =
                new PageImpl<>(mockRoomTypesResponse, pageRequest, mockRoomTypesResponse.size());

        Mockito.when(roomTypeService.findAll(Mockito.any(), Mockito.eq(BigDecimal.valueOf(1000)),
                        Mockito.eq(BigDecimal.valueOf(5000)), Mockito.any(), Mockito.any(), Mockito.anyInt(),
                        Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(mockRoomTypesPage);

        //Then
        mockMvc.perform(get(BASE_PATH + "/room-types")
                        .param("minPrice", "1000")
                        .param("maxPrice", "5000")
                        .param("page", "0")
                        .param("pageSize", "10")
                        .param("property", "name")
                        .param("direction", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.content[0].price").value(2000));

        // Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findAll(Mockito.any(), Mockito.eq(BigDecimal.valueOf(1000)), Mockito.eq(BigDecimal.valueOf(5000)),
                        Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any());

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
        Mockito.when(roomTypeService.findSummaryAll()).thenReturn(mockRoomTypesSummaryResponse);

        //Then
        mockMvc.perform(get(BASE_PATH + "/room-types/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.response").isArray())
                .andDo(print());

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1)).findSummaryAll();

    }

    @Test
    public void whenNotFoundSummaryAllRoomTypesCalled_thenReturnEmptyList() throws Exception {

        //When
        List<RoomTypesSummaryResponse> emptyList = Collections.emptyList();

        Mockito.when(roomTypeService.findSummaryAll())
                .thenReturn(emptyList);

        //Then
        mockMvc.perform(get(BASE_PATH + "/room-types/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response").isEmpty());

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
        mockMvc.perform(get(BASE_PATH + "/room-types/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.isSuccess").value(false));

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

        Mockito.when(roomTypeService.findById(mockId)).thenReturn(mockRoomTypeResponse);

        //Then
        mockMvc.perform(get(BASE_PATH + "/room-type/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(mockId))
                .andExpect(jsonPath("$.response.name").value("testRoomType"))
                .andExpect(jsonPath("$.response.price").value(2500))
                .andExpect(jsonPath("$.response.size").value(50))
                .andExpect(jsonPath("$.response.personCount").value(2));

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1)).findById(mockId);
    }

    @Test
    public void givenNonRoomTypeId_whenNotFoundById_thenReturnNotFoundException() throws Exception {

        //Given
        Long nonRoomTypeId = 999L;

        //When
        Mockito.when(roomTypeService.findById(nonRoomTypeId))
                .thenThrow(new RoomTypeNotFoundException(nonRoomTypeId));

        //Then
        mockMvc.perform(get(BASE_PATH + "/room-type/{id}", nonRoomTypeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .findById(nonRoomTypeId);
    }

    @Test
    public void givenInvalidRoomTypeId_whenNotFoundById_thenReturnBadRequest() throws Exception {

        //Given
        String invalidId = "ukhd-3521";

        //Then
        mockMvc.perform(get(BASE_PATH + "/room-type/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

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
        mockMvc.perform(get(BASE_PATH + "/room-type/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

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
        mockRoomTypeCreateRequest.setName("testRoomType");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setPersonCount(2);
        mockRoomTypeCreateRequest.setDescription("This description for Test was written !");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 2L, 3L));

        //When
        Mockito.doNothing().when(roomTypeService).create(mockRoomTypeCreateRequest);

        //Then
        mockMvc.perform(post(BASE_PATH + "/room-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest)))
                .andExpect(status().isOk());

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .create(Mockito.any(RoomTypeCreateRequest.class));

    }

    @Test
    public void givenRoomTypeCreateRequestWithMissingFields_whenCreateRoomType_thenBadRequestResponse() throws Exception {

        //Given
        RoomTypeCreateRequest invalidRequest = new RoomTypeCreateRequest();
        invalidRequest.setName("");

        //Then
        mockMvc.perform(post(BASE_PATH + "/room-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenRoomTypeServiceThrowsException_whenCreateRoomType_thenInternalServerErrorResponse() throws Exception {

        //Given
        RoomTypeCreateRequest mockRoomTypeCreateRequest = new RoomTypeCreateRequest();
        mockRoomTypeCreateRequest.setName("testRoomType");
        mockRoomTypeCreateRequest.setPrice(BigDecimal.valueOf(2500));
        mockRoomTypeCreateRequest.setSize(50);
        mockRoomTypeCreateRequest.setPersonCount(2);
        mockRoomTypeCreateRequest.setDescription("This description for Test was written !");
        mockRoomTypeCreateRequest.setAssetIds(List.of(1L, 2L, 3L));

        //When
        Mockito.doThrow(new RuntimeException("Unexpected Error")).when(roomTypeService)
                .create(Mockito.any(RoomTypeCreateRequest.class));

        //Then
        mockMvc.perform(post(BASE_PATH + "/room-type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRoomTypeCreateRequest)))
                .andExpect(status().isInternalServerError());
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
        mockroomTypeUpdateRequest.setName("testUpdateRoomType");
        mockroomTypeUpdateRequest.setPrice(BigDecimal.valueOf(1500));
        mockroomTypeUpdateRequest.setSize(35);
        mockroomTypeUpdateRequest.setPersonCount(2);
        mockroomTypeUpdateRequest.setDescription("This description for Test was written !");
        mockroomTypeUpdateRequest.setAssetIds(List.of(1L, 2L, 3L));

        //When
        Mockito.doNothing().when(roomTypeService).update(mockId, mockroomTypeUpdateRequest);

        //Then
        mockMvc.perform(put(BASE_PATH + "/room-type/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockroomTypeUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andDo(print());

        //Verify
        Mockito.verify(roomTypeService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(RoomTypeUpdateRequest.class));

    }

    @Test
    void givenNonValidId_whenCalledRoomTypeWithNonById_thenReturnsRoomTypeNotFoundException() throws Exception {

        //Given
        Long mockNonId = 999L;

        RoomTypeUpdateRequest mockroomTypeUpdateRequest = new RoomTypeUpdateRequest();
        mockroomTypeUpdateRequest.setName("testUpdateRoomType");
        mockroomTypeUpdateRequest.setPrice(BigDecimal.valueOf(1500));
        mockroomTypeUpdateRequest.setSize(35);
        mockroomTypeUpdateRequest.setPersonCount(2);
        mockroomTypeUpdateRequest.setDescription("This description for Test was written !");
        mockroomTypeUpdateRequest.setAssetIds(List.of(1L, 2L, 3L));

        //When
        Mockito.doThrow(new RoomTypeNotFoundException(mockNonId))
                .when(roomTypeService).update(mockNonId, mockroomTypeUpdateRequest);

        //Then
        mockMvc.perform(put(BASE_PATH + "/room-type/{id}", mockNonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockroomTypeUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andDo(print());

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
        mockMvc.perform(delete(BASE_PATH + "/room-type/{id}", mockId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true));
    }

    @Test
    void givenNonRoomTypeId_whenCalledRoomTypeServiceDelete_thenReturnRoomTypeNotFoundException() throws Exception {

        //Given
        Long mockId = 99L;

        //When
        Mockito.doThrow(new RoomTypeNotFoundException(mockId))
                .when(roomTypeService).delete(mockId);

        //Then
        mockMvc.perform(delete(BASE_PATH + "/room-type/{id}", mockId))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenInValidId_whenCalledRoomTypeDelete_thenReturnBadRequest() throws Exception {

        //Then
        mockMvc.perform(delete(BASE_PATH + "/room-type/{id}", "hahahah"))
                .andExpect(status().isBadRequest());

    }


}