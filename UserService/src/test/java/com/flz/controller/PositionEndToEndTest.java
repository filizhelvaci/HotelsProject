package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.cleaner.PositionTestCleaner;
import com.flz.model.Position;
import com.flz.model.request.PositionCreateRequest;
import com.flz.port.PositionReadPort;
import com.flz.port.PositionSavePort;
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
    private PositionSavePort positionSavePort;

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

}
