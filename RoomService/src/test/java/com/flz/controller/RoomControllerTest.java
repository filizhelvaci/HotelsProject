package com.flz.controller;

import com.flz.BaseTest;
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

}