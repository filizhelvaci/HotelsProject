package com.flz.controller;

import com.flz.BaseTest;
import com.flz.exception.RoomNotFoundException;
import com.flz.model.enums.RoomStatus;
import com.flz.model.response.RoomResponse;
import com.flz.model.response.RoomsSummaryResponse;
import com.flz.service.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}