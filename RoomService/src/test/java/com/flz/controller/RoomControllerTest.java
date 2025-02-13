package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.exception.RoomNotFoundException;
import com.flz.model.enums.RoomStatus;
import com.flz.model.request.RoomCreateRequest;
import com.flz.model.request.RoomUpdateRequest;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.service.RoomService;
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

@WebMvcTest(RoomController.class)
class RoomControllerTest extends BaseTest {

    @MockBean
    RoomService roomService;

    @Autowired
    private MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * findAll()
     * {@link AssetController#findAll(String, BigDecimal, BigDecimal, Boolean, int, int, String, Sort.Direction)}
     */
    @Test
    public void givenFilteringParameters_whenCalledFindAllRoomWithParameters_thenReturnPageRoomsResponseSuccessfully() throws Exception {

        //Given
        int page = 0;
        int size = 10;
        String property = "id";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        Sort sort = Sort.by(direction, property);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        RoomsResponse.Type roomType = RoomsResponse.Type.builder().id(10L).name("Standart Room").build();

        List<RoomsResponse> mockRoomsResponse = getRoomsResponse(roomType);

        Page<RoomsResponse> mockRoomsPage = new PageImpl<>(mockRoomsResponse, pageRequest, mockRoomsResponse.size());

        Mockito.when(roomService.findAll(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(mockRoomsPage);

        //Then
        mockMvc.perform(get(BASE_PATH + "/rooms")
                        .param("page", "0")
                        .param("size", "10")
                        .param("property", "id")
                        .param("direction", Sort.Direction.ASC.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andDo(print());

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .findAll(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                        Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any());
    }

    private static List<RoomsResponse> getRoomsResponse(RoomsResponse.Type roomType) {
        return List.of(RoomsResponse.builder()
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

    @Test
    public void givenFloorFilter_whenFindAllRoomAsFilter_thenReturnRoomsResponse() throws Exception {

        //Given
        int page = 0;
        int size = 10;
        String property = "id";
        Sort.Direction direction = Sort.Direction.ASC;

        //When
        Sort sort = Sort.by(direction, property);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        RoomsResponse.Type roomType =
                RoomsResponse.Type.builder().id(20L).name("Standart Room").build();

        List<RoomsResponse> mockRoomsResponse = getRoomsResponse(roomType);

        Page<RoomsResponse> mockRoomsPage =
                new PageImpl<>(mockRoomsResponse, pageRequest, mockRoomsResponse.size());

        Mockito.when(roomService.findAll(Mockito.any(), Mockito.eq(1), Mockito.any(),
                        Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any()))
                .thenReturn(mockRoomsPage);

        //Then
        mockMvc.perform(get(BASE_PATH + "/rooms")
                        .param("floor", "1")
                        .param("page", "0")
                        .param("size", "10")
                        .param("property", "id")
                        .param("direction", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.response.content[0].floor").value(1))
                .andDo(print());


        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .findAll(Mockito.any(), Mockito.eq(1), Mockito.any(),
                        Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any(), Mockito.any());
    }

    /**
     * findSummaryAll()
     * {@link RoomService#findSummaryAll()}
     */
    @Test
    public void whenCallAllSummaryRoom_thenReturnRoomsSummaryResponse() throws Exception {

        //Given
        List<RoomsSummaryResponse> mockRoomsSummaryResponse =
                List.of(RoomsSummaryResponse.builder()
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
        Mockito.when(roomService.findSummaryAll()).thenReturn(mockRoomsSummaryResponse);

        //Then
        mockMvc.perform(get(BASE_PATH + "/rooms/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.response").isArray())
                .andDo(print());

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
        mockMvc.perform(get(BASE_PATH + "/rooms/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.response").isArray())
                .andExpect(jsonPath("$.response").isEmpty());

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .findSummaryAll();
    }

    @Test
    public void whenFindSummaryAllIsCalledAndTheRoomServiceFails_thenReturnInternalServerError() throws Exception {

        //When
        Mockito.when(roomService.findSummaryAll())
                .thenThrow(new RuntimeException("An unexpected error occurred"));

        //Then
        mockMvc.perform(get(BASE_PATH + "/rooms/summary")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.isSuccess").value(false));

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .findSummaryAll();

    }

    /**
     * findById()
     * {@link RoomController#findById(Long)}
     */
    @Test
    public void givenValidId_whenFindRoomById_thenReturnRoomResponse() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        RoomResponse.Type roomType = RoomResponse.Type.builder().id(10L).name("Standart Room").build();

        RoomResponse mockRoomResponse = RoomResponse.builder()
                .id(10L)
                .number(105)
                .floor(1)
                .status(RoomStatus.EMPTY)
                .type(roomType)
                .build();

        Mockito.when(roomService.findById(mockId)).thenReturn(mockRoomResponse);

        //Then
        mockMvc.perform(get(BASE_PATH + "/room/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(10L))
                .andExpect(jsonPath("$.response.number").value(105))
                .andExpect(jsonPath("$.response.status").value("EMPTY"));

        //Verify
        Mockito.verify(roomService, Mockito.times(1)).findById(mockId);
    }

    @Test
    public void givenNonRoomId_whenNotFoundRoomById_thenReturnRoomNotFoundException() throws Exception {

        //Given
        Long nonId = 999L;

        //When
        Mockito.when(roomService.findById(nonId))
                .thenThrow(new RoomNotFoundException(nonId));

        //Then
        mockMvc.perform(get(BASE_PATH + "/room/{id}", nonId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .findById(nonId);
    }

    @Test
    public void givenInvalidRoomId_whenNotFoundRoomById_thenReturnBadRequest() throws Exception {

        //Given
        String invalidId = "ukhd21";

        //Then
        mockMvc.perform(get(BASE_PATH + "/room/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //Verify
        Mockito.verify(roomService, Mockito.never())
                .findById(Mockito.any());
    }

    @Test
    public void givenValidId_whenTakeServiceError_thenReturnInternalServerError() throws Exception {

        //Given
        Long mockId = 1L;

        //When
        Mockito.when(roomService.findById(mockId))
                .thenThrow(new RuntimeException("Unexpected error"));

        //Then
        mockMvc.perform(get(BASE_PATH + "/room/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

    }

    /**
     * Create()
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
        mockMvc.perform(post(BASE_PATH + "/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRoomCreateRequest)))
                .andExpect(status().isOk());

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .create(Mockito.any(RoomCreateRequest.class));

    }

    @Test
    public void givenRoomCreateRequestWithMissingFields_whenCreateRoom_thenReturnBadRequest() throws Exception {

        //Given
        RoomCreateRequest invalidRequest = new RoomCreateRequest();
        invalidRequest.setFloor(2);

        //When
        mockMvc.perform(post(BASE_PATH + "/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenRoomCreateRequest_whenRoomServiceTakeError_thenReturnInternalServerError() throws Exception {

        //Given
        RoomCreateRequest mockRoomCreateRequest = new RoomCreateRequest();
        mockRoomCreateRequest.setNumber(202);
        mockRoomCreateRequest.setFloor(2);
        mockRoomCreateRequest.setRoomTypeId(2L);
        mockRoomCreateRequest.setStatus(RoomStatus.EMPTY);

        //When
        Mockito.doThrow(new RuntimeException("Unexpected Error")).when(roomService)
                .create(Mockito.any(RoomCreateRequest.class));

        //Then
        mockMvc.perform(post(BASE_PATH + "/asset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRoomCreateRequest)))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Update()
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
        mockMvc.perform(put(BASE_PATH + "/room/{id}", mockId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRoomUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andDo(print());

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(RoomUpdateRequest.class));

    }

    @Test
    void givenNonId_whenCalledRoomWithNonById_thenReturnsRoomNotFoundException() throws Exception {

        //Given
        Long NonId = 999L;

        RoomUpdateRequest mockRoomUpdateRequest = new RoomUpdateRequest();
        mockRoomUpdateRequest.setNumber(302);
        mockRoomUpdateRequest.setFloor(3);
        mockRoomUpdateRequest.setRoomTypeId(2L);
        mockRoomUpdateRequest.setStatus(RoomStatus.FULL);

        //When
        Mockito.doThrow(new RoomNotFoundException(NonId))
                .when(roomService).update(NonId, mockRoomUpdateRequest);

        //Then
        mockMvc.perform(put(BASE_PATH + "/room/{id}", NonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRoomUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andDo(print());

        //Verify
        Mockito.verify(roomService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(RoomUpdateRequest.class));

    }

    /**
     * delete()
     * {@link RoomController#delete(Long)}
     */
    @Test
    void givenValidId_whenCalledDeleteRoom_thenDeleteSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        Mockito.doNothing().when(roomService).delete(mockId);

        //Then
        mockMvc.perform(delete(BASE_PATH + "/room/{id}", mockId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isSuccess").value(true));
    }

    @Test
    void givenNonId_whenCalledDeleteRoom_thenReturnRoomNotFoundException() throws Exception {

        //Given
        Long mockId = 99L;

        //When
        Mockito.doThrow(new RoomNotFoundException(mockId))
                .when(roomService).delete(mockId);

        //Then
        mockMvc.perform(delete(BASE_PATH + "/room/{id}", mockId))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenInValidId_whenCalledDeleteForRoom_thenReturnBadRequest() throws Exception {

        //Then
        mockMvc.perform(delete(BASE_PATH + "/room/{id}", "lkjhg"))
                .andExpect(status().isBadRequest());

    }

}