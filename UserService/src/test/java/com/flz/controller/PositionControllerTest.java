package com.flz.controller;

import com.flz.model.Position;
import com.flz.model.enums.PositionStatus;
import com.flz.model.response.PositionSummaryResponse;
import com.flz.service.PositionReadService;
import com.flz.service.PositionWriteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@WebMvcTest(PositionController.class)
class PositionControllerTest {

    @MockBean
    PositionWriteService positionWriteService;

    @MockBean
    PositionReadService positionReadService;

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * {@link PositionController#findAll(com.flz.model.request.PageRequest)}
     */
    @Test
    public void givenPageAndPageSize_whenCalledAllPosition_thenReturnAllPositionsSuccessfully() throws Exception {

        //Given
        List<Position> mockPositions = getPositions();

        //When
        Mockito.when(positionReadService.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(mockPositions);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
                .param("page", "1")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()")
                        .value(mockPositions.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].status")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(positionReadService, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void whenPageSizeDifferentThanValidValueInFindAll_thenReturnBadRequestError() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
                .param("page", "1")
                .param("pageSize", "-10")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(positionReadService, Mockito.never())
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void whenPageDifferentThanValidValueInFindAll_thenReturnBadRequestError() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
                .param("page", "-1")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(positionReadService, Mockito.never())
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void givenValidPageAndPageSize_whenCalledAllPositions_thenReturnEmptyList() throws Exception {

        //Given
        Integer mockPageSize = 10;
        Integer mockPage = 1;

        List<Position> emptyPositions = List.of();

        //When
        Mockito.when(positionReadService.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(emptyPositions);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
                .param("page", String.valueOf(mockPage))
                .param("pageSize", String.valueOf(mockPageSize))
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(request).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()")
                        .value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        // Verify
        Mockito.verify(positionReadService, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    /**
     * {@link PositionController#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryPosition_thenReturnPositionsSummaryResponse() throws Exception {

        //Given
        List<PositionSummaryResponse> mockPositionsSummaryResponse = List.of(
                PositionSummaryResponse.builder()
                        .id(11L)
                        .name("TestName1")
                        .build(),
                PositionSummaryResponse.builder()
                        .id(12L)
                        .name("TestName2")
                        .build(),
                PositionSummaryResponse.builder()
                        .id(13L)
                        .name("TestName3")
                        .build()
        );

        //When
        Mockito.when(positionReadService.findSummaryAll())
                .thenReturn(mockPositionsSummaryResponse);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions/summary")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(positionReadService, Mockito.times(1))
                .findSummaryAll();
    }

    @Test
    public void whenNotFoundPositionsSummaryAll_thenReturnEmptyList() throws Exception {

        //When
        List<PositionSummaryResponse> emptyList = Collections.emptyList();

        Mockito.when(positionReadService.findSummaryAll())
                .thenReturn(emptyList);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions/summary")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
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
        Mockito.verify(positionReadService, Mockito.times(1))
                .findSummaryAll();
    }



    private static List<Position> getPositions() {
        return List.of(
                Position.builder()
                        .id(11L)
                        .name("TEST1")
                        .status(PositionStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdUser("testAdmin")
                        .build(),
                Position.builder()
                        .id(12L)
                        .name("TEST2")
                        .status(PositionStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdUser("testAdmin")
                        .build(),
                Position.builder()
                        .id(13L)
                        .name("TEST3")
                        .status(PositionStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdUser("testAdmin")
                        .build());
    }

}