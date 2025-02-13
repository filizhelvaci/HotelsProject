package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.model.request.RoomTypeCreateRequest;
import com.flz.model.request.RoomTypeUpdateRequest;
import com.flz.service.RoomTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    /**
     *  /room-types/summary
     *  findSummaryAll()
     *  {@link RoomTypeController#findSummaryAll()}
     */

    /**
     * /room-type/{id}
     * findById()
     * {@link RoomTypeController#findById(Long)}
     */

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

    /**
     * /room-type/{id}
     * delete
     * {@link RoomTypeController#delete(Long)}
     */
}