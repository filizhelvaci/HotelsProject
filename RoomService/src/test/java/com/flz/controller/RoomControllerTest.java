package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.model.enums.RoomStatus;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.service.RoomService;
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

@WebMvcTest(RoomController.class)
class RoomControllerTest extends BaseTest {

    @MockBean
    RoomService roomService;

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * {@link AssetController#findAll(String, BigDecimal, BigDecimal, Boolean, int, int, String, Sort.Direction)}
     */
    @Test
    public void givenFilteringParameters_whenCalledFindAllRoomWithParameters_thenReturnPageRoomsResponseSuccessfully() throws Exception {

        //Given
        int mockPage = 0;
        int mockSize = 10;
        String mockProperty = "id";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        Sort mockSort = Sort.by(mockDirection, mockProperty);
        PageRequest pageRequest = PageRequest.of(mockPage, mockSize, mockSort);

        RoomsResponse.Type mockRoomType = RoomsResponse.Type
                .builder().id(10L).name("Standart Room").build();

        List<RoomsResponse> mockRoomsResponse = getRoomsResponse(mockRoomType);

        Page<RoomsResponse> mockRoomsPage = new PageImpl<>
                (mockRoomsResponse, pageRequest, mockRoomsResponse.size());

        Mockito.when(roomService.findAll(
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(RoomStatus.class),
                        Mockito.nullable(Long.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                ))
                .thenReturn(mockRoomsPage);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/rooms")
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].number")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].floor")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].status")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].type")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].type")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .findAll(
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(Integer.class),
                        Mockito.nullable(RoomStatus.class),
                        Mockito.nullable(Long.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class));
    }

    @Test
    public void givenFloorFilter_whenFindAllRoomAsFilter_thenReturnRoomsResponse() throws Exception {

        //Given
        Integer mockFloor = 1;
        int mockPage = 0;
        int mockSize = 10;
        String mockProperty = "id";
        Sort.Direction mockDirection = Sort.Direction.ASC;

        //When
        Sort mockSort = Sort.by(mockDirection, mockProperty);
        PageRequest pageRequest = PageRequest.of(mockPage, mockSize, mockSort);

        RoomsResponse.Type mockRoomType = RoomsResponse.Type
                .builder().id(20L).name("Standart Room").build();

        List<RoomsResponse> mockRoomsResponse = getRoomsResponse(mockRoomType);

        Page<RoomsResponse> mockRoomsPage = new PageImpl<>
                (mockRoomsResponse, pageRequest, mockRoomsResponse.size());

        Mockito.when(roomService.findAll(
                        Mockito.nullable(Integer.class),
                        Mockito.anyInt(),
                        Mockito.nullable(RoomStatus.class),
                        Mockito.nullable(Long.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class)
                ))
                .thenReturn(mockRoomsPage);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/rooms")
                .param("floor", String.valueOf(mockFloor))
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].id")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].number")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].floor")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].status")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].type")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].type")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.content[*].floor", Matchers
                        .everyItem(Matchers.equalTo(mockFloor))));


        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .findAll(
                        Mockito.nullable(Integer.class),
                        Mockito.anyInt(),
                        Mockito.nullable(RoomStatus.class),
                        Mockito.nullable(Long.class),
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(Sort.Direction.class));
    }

    /**
     * {@link RoomService#findSummaryAll()}
     */
    @Test
    public void whenCallAllSummaryRoom_thenReturnRoomsSummaryResponse() throws Exception {

        //Given
        List<RoomsSummaryResponse> mockRoomsSummaryResponse = List.of(
                RoomsSummaryResponse.builder()
                        .id(11L)
                        .number(101)
                        .build(),
                RoomsSummaryResponse.builder()
                        .id(12L)
                        .number(102)
                        .build(),
                RoomsSummaryResponse.builder()
                        .id(13L)
                        .number(103)
                        .build()
        );

        //When
        Mockito.when(roomService.findSummaryAll())
                .thenReturn(mockRoomsSummaryResponse);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/rooms/summary")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].number")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()")
                        .value(mockRoomsSummaryResponse.size()))

        ;

        //Verify
        Mockito.verify(roomService, Mockito.times(1)).findSummaryAll();

    }

    @Test
    public void givenNonRooms_whenNotFoundRoomsSummaryAll_thenReturnEmptyList() throws Exception {

        //When
        List<RoomsSummaryResponse> emptyList = Collections.emptyList();

        Mockito.when(roomService.findSummaryAll())
                .thenReturn(emptyList);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/rooms/summary")
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
        Mockito.verify(roomService, Mockito.times(1))
                .findSummaryAll();
    }

    /**
     * {@link RoomController#findById(Long)}
     */
    @Test
    public void givenValidId_whenFindRoomById_thenReturnRoomResponse() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        RoomResponse.Type mockRoomType = RoomResponse.Type
                .builder().id(10L).name("Standart Room").build();

        RoomResponse mockRoomResponse = RoomResponse.builder()
                .id(10L)
                .number(105)
                .floor(1)
                .status(RoomStatus.EMPTY)
                .type(mockRoomType)
                .build();

        Mockito.when(roomService.findById(mockId))
                .thenReturn(mockRoomResponse);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.id", Matchers
                        .equalTo(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.number")
                        .value(105))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.status")
                        .value("EMPTY"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.type")
                        .isMap());

        //Verify
        Mockito.verify(roomService, Mockito.times(1)).findById(mockId);
    }

    @Test
    public void givenInvalidRoomId_whenNotFoundRoomById_thenReturnBadRequest() throws Exception {

        //Given
        String mockInvalidId = "ukhd21";

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/room/{id}", mockInvalidId)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(roomService, Mockito.never())
                .findById(Mockito.any());
    }

    /**
     * {@link RoomController#create(RoomCreateRequest)}
     */
    @Test
    public void givenValidRoomCreateRequest_whenRoomCreated_thenSuccessRoomResponse() throws Exception {

        //Given
        RoomCreateRequest mockRoomCreateRequest = new RoomCreateRequest();
        mockRoomCreateRequest.setNumber(202);
        mockRoomCreateRequest.setFloor(2);
        mockRoomCreateRequest.setRoomTypeId(2L);
        mockRoomCreateRequest.setStatus(RoomStatus.EMPTY);

        //When
        Mockito.doNothing().when(roomService).create(mockRoomCreateRequest);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomCreateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .create(Mockito.any(RoomCreateRequest.class));

    }

    @Test
    public void givenRoomCreateRequestWithMissingFields_whenCreateRoom_thenReturnBadRequest() throws Exception {

        //Given
        RoomCreateRequest mockInvalidRequest = new RoomCreateRequest();
        mockInvalidRequest.setFloor(2);

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockInvalidRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));
    }

    /**
     * {@link RoomController#update(Long, RoomUpdateRequest)}
     */
    @Test
    public void givenValidIdAndRoomUpdateRequest_whenFindRoomById_thenUpdateRoomSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        RoomUpdateRequest mockRoomUpdateRequest = new RoomUpdateRequest();
        mockRoomUpdateRequest.setNumber(302);
        mockRoomUpdateRequest.setFloor(3);
        mockRoomUpdateRequest.setRoomTypeId(2L);
        mockRoomUpdateRequest.setStatus(RoomStatus.FULL);

        //When
        Mockito.doNothing().when(roomService).update(mockId, mockRoomUpdateRequest);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/room/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomUpdateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(RoomUpdateRequest.class));

    }

    @Test
    void givenInvalidId_whenCalledRoomWithByInvalidId_thenReturnsBadRequestError() throws Exception {

        //Given
        String mockInvalidId = "I am InvalidId";

        RoomUpdateRequest mockRoomUpdateRequest = new RoomUpdateRequest();
        mockRoomUpdateRequest.setNumber(302);
        mockRoomUpdateRequest.setFloor(3);
        mockRoomUpdateRequest.setRoomTypeId(2L);
        mockRoomUpdateRequest.setStatus(RoomStatus.FULL);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/room/{id}", mockInvalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRoomUpdateRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(roomService, Mockito.never())
                .update(Mockito.any(), Mockito.any(RoomUpdateRequest.class));

    }

    /**
     * {@link RoomController#delete(Long)}
     */
    @Test
    void givenValidId_whenCalledDeleteRoom_thenDeleteSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        Mockito.doNothing().when(roomService).delete(mockId);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/room/{id}", mockId);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));
    }

    @Test
    void givenInValidId_whenCalledDeleteForRoom_thenReturnBadRequest() throws Exception {

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/room/{id}", "lkjhg");

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

    }

    /**
     * # Methodized Objects
     */
    private static List<RoomsResponse> getRoomsResponse(RoomsResponse.Type roomType) {
        return List.of(
                RoomsResponse.builder()
                        .id(10L)
                        .number(101)
                        .floor(1)
                        .status(RoomStatus.EMPTY)
                        .type(roomType)
                        .build(),
                RoomsResponse.builder()
                        .id(11L)
                        .number(102)
                        .floor(1)
                        .status(RoomStatus.FULL)
                        .type(roomType)
                        .build()
        );
    }

}