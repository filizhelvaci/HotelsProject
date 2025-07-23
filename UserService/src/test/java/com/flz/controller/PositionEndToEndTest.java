package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.cleaner.PositionTestCleaner;
import com.flz.model.Position;
import com.flz.model.request.PositionCreateRequest;
import com.flz.model.request.PositionUpdateRequest;
import com.flz.port.PositionReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class PositionEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PositionReadPort positionReadPort;

    @Autowired
    private PositionTestCleaner testCleaner;

    @BeforeEach
    void cleanBeforeTest() {
        testCleaner.cleanTestPositions();
    }

    private static final String BASE_PATH = "/api/v1";

    @Test
    void givenCreateRequest_whenCreatePosition_thenReturnSuccess() throws Exception {

        //Given
        PositionCreateRequest createRequest = PositionCreateRequest.builder()
                .name("test - request")
                .departmentId(1L)
                .build();

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        Thread.sleep(5000);

        List<Position> positions = positionReadPort.findSummaryAll();
        Position createdPosition = positions.stream()
                .filter(d -> d.getName()
                        .equals("test - request"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Created position not found"));

        Long positionId = createdPosition.getId();

        //Then
        Optional<Position> position = positionReadPort.findById(positionId);
        Assertions.assertTrue(position.isPresent());
        Assertions.assertNotNull(position.get()
                .getCreatedBy());
        Assertions.assertNotNull(position.get()
                .getCreatedAt());
        Assertions.assertNotNull(position.get()
                .getName());
        Assertions.assertNotNull(position.get()
                .getDepartment());
        Assertions.assertNotNull(position.get()
                .getId());
        Assertions.assertEquals(createdPosition.getName(),
                position.get()
                        .getName());
        Assertions.assertEquals(createdPosition.getDepartment()
                        .getId(),
                position.get()
                        .getDepartment()
                        .getId());
        Assertions.assertEquals(createdPosition.getDepartment()
                        .getName(),
                position.get()
                        .getDepartment()
                        .getName());
        Assertions.assertEquals(createdPosition.getCreatedAt(),
                position.get()
                        .getCreatedAt());
        Assertions.assertEquals(createdPosition.getCreatedBy(),
                position.get()
                        .getCreatedBy());
        Assertions.assertEquals(createdPosition.getStatus(),
                position.get()
                        .getStatus());
    }

    @Test
    void givenUpdateRequest_whenUpdatePosition_thenReturnSuccess() throws Exception {

        //Given
        PositionCreateRequest createRequest = PositionCreateRequest.builder()
                .name("test - request")
                .departmentId(2L)
                .build();

        MockHttpServletRequestBuilder createRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        mockMvc.perform(createRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        Thread.sleep(5000);

        List<Position> positions = positionReadPort.findSummaryAll();
        Position createdPosition = positions.stream()
                .filter(d -> d.getName()
                        .equals("test - request"))
                .findFirst()
                .orElseThrow();

        Long positionId = createdPosition.getId();

        //When
        PositionUpdateRequest updateRequest = PositionUpdateRequest.builder()
                .name("test - updated")
                .departmentId(1L)
                .build();

        MockHttpServletRequestBuilder updateRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/position/" + positionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequest));

        mockMvc.perform(updateRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Then
        Optional<Position> position = positionReadPort.findById(positionId);
        Assertions.assertTrue(position.isPresent());
        Assertions.assertNotNull(position.get());
        Assertions.assertNotNull(position.get()
                .getName());
        Assertions.assertNotNull(position.get()
                .getDepartment());
        Assertions.assertNotNull(position.get()
                .getId());
        Assertions.assertNotNull(position.get()
                .getCreatedBy());
        Assertions.assertNotNull(position.get()
                .getCreatedAt());
        Assertions.assertNotNull(position.get()
                .getStatus());
        Assertions.assertEquals(position.get()
                .getId(), positionId);
        Assertions.assertEquals(position.get()
                        .getName(),
                updateRequest.getName());
        Assertions.assertEquals(position.get()
                        .getDepartment()
                        .getId(),
                updateRequest.getDepartmentId());
    }

    @Test
    void givenPositionId_whenDeletePosition_thenSoftDeleted() throws Exception {

        //Given
        PositionCreateRequest createRequest = PositionCreateRequest.builder()
                .name("test - request")
                .departmentId(2L)
                .build();

        MockHttpServletRequestBuilder createRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        mockMvc.perform(createRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        Thread.sleep(5000);

        List<Position> positions = positionReadPort.findSummaryAll();
        Position createdPosition = positions.stream()
                .filter(d -> d.getName()
                        .equals("test - request"))
                .findFirst()
                .orElseThrow();

        Long positionId = createdPosition.getId();

        //When
        MockHttpServletRequestBuilder deleteRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/position/" + positionId);

        mockMvc.perform(deleteRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Then
        Optional<Position> deletedPosition = positionReadPort.findById(positionId);

        Assertions.assertTrue(deletedPosition.isPresent());
        Assertions.assertEquals(deletedPosition.get()
                .getId(), positionId);
        Assertions.assertNotEquals(deletedPosition.get()
                .getStatus(), createdPosition.getStatus());
        Assertions.assertNotNull(deletedPosition.get()
                .getCreatedAt());
        Assertions.assertNotNull(deletedPosition.get()
                .getCreatedBy());
    }

    @Test
    void whenFindAllPositions_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
                .param("page", "1")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].status")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .value("Genel Müdür"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].status")
                        .value("ACTIVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdBy")
                        .value("Admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdAt")
                        .isNotEmpty());

        //Then
        List<Position> positions = positionReadPort.findAll(1, 10);
        Assertions.assertNotNull(positions);
        Assertions.assertFalse(positions.isEmpty());
        Assertions.assertNotNull(positions.get(0)
                .getName());
        Assertions.assertNotNull(positions.get(0)
                .getStatus());
        Assertions.assertNotNull(positions.get(0)
                .getCreatedAt());
        Assertions.assertNotNull(positions.get(0)
                .getCreatedBy());
        Assertions.assertNotNull(positions.get(0)
                .getId());

    }

    @Test
    void whenFindSummaryAllPositions_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions/summary")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .value("Genel Müdür"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber());

        //Then
        List<Position> positions = positionReadPort.findSummaryAll();
        Assertions.assertNotNull(positions);
        Assertions.assertFalse(positions.isEmpty());
        Assertions.assertNotNull(positions.get(0)
                .getName());
        Assertions.assertNotNull(positions.get(0)
                .getId());
    }

}
