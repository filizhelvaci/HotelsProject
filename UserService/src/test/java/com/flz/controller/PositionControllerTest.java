package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.model.Position;
import com.flz.model.enums.PositionStatus;
import com.flz.model.request.PositionCreateRequest;
import com.flz.model.request.PositionUpdateRequest;
import com.flz.model.response.PositionSummaryResponse;
import com.flz.service.PositionReadService;
import com.flz.service.PositionWriteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
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
class PositionControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PositionWriteService positionWriteService;

    @MockBean
    PositionReadService positionReadService;

    private static final String BASE_PATH = "/api/v1";

    /**
     * {@link PositionController#findAll(com.flz.model.request.PageRequest)}
     */
    @Test
    void givenPageAndPageSize_whenCalledAllPosition_thenReturnAllPositionsSuccessfully() throws Exception {

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

    @ParameterizedTest
    @ValueSource(ints = {
            0,
            16,
            -1,
            100
    })
    void givenInvalidPageSize_whenFindAllPositions_thenReturnBadRequestError(Integer invalidPageSize) throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
                .param("page", "1")
                .param("pageSize", invalidPageSize.toString())
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
    void givenNullPageSize_whenFindAllPositions_thenReturnBadRequest() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
                .param("page", "1")
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

    @ParameterizedTest
    @ValueSource(ints = {
            0,
            -1,
            -100
    })
    void givenInvalidPage_whenFindAllPositions_thenReturnBadRequestError(Integer invalidPage) throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
                .param("page", invalidPage.toString())
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

    private static List<Position> getPositions() {
        return List.of(
                Position.builder()
                        .id(11L)
                        .name("TEST1")
                        .status(PositionStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("testAdmin")
                        .build(),
                Position.builder()
                        .id(12L)
                        .name("TEST2")
                        .status(PositionStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("testAdmin")
                        .build(),
                Position.builder()
                        .id(13L)
                        .name("TEST3")
                        .status(PositionStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("testAdmin")
                        .build());
    }

    @Test
    void givenValidPageAndPageSize_whenCalledAllPositions_thenReturnEmptyList() throws Exception {

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
    void whenCalledAllSummaryPosition_thenReturnPositionsSummaryResponse() throws Exception {

        //Initialize
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
    void givenNullPage_whenFindAllPositions_thenReturnBadRequest() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
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

    /**
     * {@link PositionController#create(PositionCreateRequest)}
     */
    @Test
    void givenValidPositionCreateRequest_whenPositionCreated_thenReturnSuccess() throws Exception {

        //Given
        PositionCreateRequest mockCreateRequest = PositionCreateRequest.builder()
                .name("TestName")
                .departmentId(2L)
                .build();

        //When
        Mockito.doNothing()
                .when(positionWriteService)
                .create(Mockito.any(PositionCreateRequest.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .doesNotExist());

        //Verify
        Mockito.verify(positionWriteService, Mockito.times(1))
                .create(Mockito.any(PositionCreateRequest.class));

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "a",
            "One morning, when Gregor Samsa woke from troubled dreams, " +
                    "he found himself transformed in his bed int"
    })
    void givenInvalidName_whenCreatePosition_thenReturnBadRequest(String invalidName) throws Exception {

        //Given
        PositionCreateRequest invalidRequest = PositionCreateRequest.builder()
                .name(invalidName)
                .departmentId(2L)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verify
        Mockito.verify(positionWriteService, Mockito.never())
                .create(Mockito.any(PositionCreateRequest.class));

    }

    @Test
    void whenNotFoundPositionsSummaryAll_thenReturnEmptyList() throws Exception {

        //Initialize
        List<PositionSummaryResponse> emptyList = Collections.emptyList();

        //When
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



    /**
     * {@link PositionController#update(Long, PositionUpdateRequest)}
     */
    @Test
    void givenValidIdAndPositionUpdateRequest_whenFindPositionById_thenUpdatePositionSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        PositionUpdateRequest mockPositionUpdateRequest = PositionUpdateRequest.builder()
                .name("TestName")
                .departmentId(3L)
                .build();

        //When
        Mockito.doNothing()
                .when(positionWriteService)
                .update(mockId, mockPositionUpdateRequest);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/position/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockPositionUpdateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(positionWriteService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(PositionUpdateRequest.class));

    }

    @ParameterizedTest
    @ValueSource(longs = {
            0L,
            -1L,
            -100L
    })
    void givenInvalidId_whenUpdatePosition_thenReturnBadRequest(Long invalidId) throws Exception {

        //Given
        PositionUpdateRequest mockRequest = PositionUpdateRequest.builder()
                .name("ValidTestName")
                .departmentId(5L)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BASE_PATH + "/position/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));


        //Verify
        Mockito.verify(positionWriteService, Mockito.never())
                .update(Mockito.any(), Mockito.any(PositionUpdateRequest.class));

    }

    @Test
    void givenNullId_whenUpdatePosition_thenReturnInternalServerError() throws Exception {

        //Given
        PositionUpdateRequest mockRequest = PositionUpdateRequest.builder()
                .name("ValidTestName")
                .departmentId(3L)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BASE_PATH + "/position/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(positionWriteService, Mockito.never())
                .update(Mockito.any(), Mockito.any(PositionUpdateRequest.class));

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "a",
            "One morning, when Gregor Samsa woke from troubled dreams, " +
                    "he found himself transformed in his bed int"
    })
    void givenInvalidName_whenUpdatePosition_thenReturnBadRequest(String invalidName) throws Exception {

        //Given
        PositionUpdateRequest mockRequest = PositionUpdateRequest.builder()
                .name(invalidName)
                .departmentId(5L)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BASE_PATH + "/position/{id}", 10L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest));
        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verify
        Mockito.verify(positionWriteService, Mockito.never())
                .update(Mockito.any(), Mockito.any(PositionUpdateRequest.class));


    }

    @ParameterizedTest
    @ValueSource(longs = {
            0L,
            -1L,
            -100L
    })
    void givenInvalidDepartmentId_whenUpdatePosition_thenReturnBadRequest(Long invalidDepartmentId) throws Exception {

        //Given
        PositionUpdateRequest mockRequest = PositionUpdateRequest.builder()
                .name("ValidTestName")
                .departmentId(invalidDepartmentId)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BASE_PATH + "/position/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));


        //Verify
        Mockito.verify(positionWriteService, Mockito.never())
                .update(Mockito.any(), Mockito.any(PositionUpdateRequest.class));

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {
            0L,
            -1L,
            -50L
    })
    void givenInvalidDepartmentId_whenCreatePosition_thenReturnBadRequest(Long invalidDepartmentId) throws Exception {

        //Given
        PositionCreateRequest invalidRequest = PositionCreateRequest.builder()
                .name("ValidName")
                .departmentId(invalidDepartmentId)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verify
        Mockito.verify(positionWriteService, Mockito.never())
                .create(Mockito.any(PositionCreateRequest.class));
    }

    /**
     * {@link PositionController#delete(Long)}
     */
    @Test
    void givenValidId_whenCalledDeletePosition_thenDoPositionStatusDeleted() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        Mockito.doNothing()
                .when(positionWriteService)
                .delete(mockId);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/position/{id}", mockId);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(positionWriteService, Mockito.times(1))
                .delete(Mockito.anyLong());

    }

    @ParameterizedTest
    @ValueSource(longs = {
            0L,
            -1L,
            -100L
    })
    void givenInValidId_whenCalledDeleteForPosition_thenReturnBadRequest(Long invalidId) throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/position/{id}", invalidId);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(positionWriteService, Mockito.never())
                .delete(Mockito.anyLong());

    }

    @Test
    void givenNullDepartmentId_whenUpdatePosition_thenReturnInternalServerError() throws Exception {

        //Given
        PositionUpdateRequest mockRequest = PositionUpdateRequest.builder()
                .name("ValidTestName")
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BASE_PATH + "/position/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(positionWriteService, Mockito.never())
                .update(Mockito.any(), Mockito.any(PositionUpdateRequest.class));

    }

}
