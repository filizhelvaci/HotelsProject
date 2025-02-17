package com.flz.controller;

import com.flz.BaseTest;
import com.flz.model.response.RoomTypeAvailabilityResponse;
import com.flz.service.RoomTypeAvailabilityService;
import org.hamcrest.Matchers;
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

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(RoomTypeAvailabilityController.class)
class RoomTypeAvailabilityControllerTest extends BaseTest {

    @MockBean
    RoomTypeAvailabilityService roomTypeAvailabilityService;

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_URL = "/api/v1";

    /**
     * {@link RoomTypeAvailabilityController#findRoomTypesAvailability()}
     */
    @Test
    void whenCalledAllRoomTypes_thenReturnGetRoomTypeAvailabilitySituation() throws Exception {

        //When
        List<RoomTypeAvailabilityResponse> mockRoomTypeAvailabilityResponses = List.of(
                RoomTypeAvailabilityResponse.builder()
                        .id(10L)
                        .name("test1RoomTypeAvailability")
                        .price(BigDecimal.valueOf(1500))
                        .personCount(3)
                        .size(55)
                        .isAvailability(true)
                        .build(),
                RoomTypeAvailabilityResponse.builder()
                        .id(11L)
                        .name("test2RoomTypeAvailability")
                        .price(BigDecimal.valueOf(2500))
                        .personCount(5)
                        .size(75)
                        .isAvailability(true)
                        .build(),
                RoomTypeAvailabilityResponse.builder()
                        .id(12L)
                        .name("test3RoomTypeAvailability")
                        .price(BigDecimal.valueOf(3500))
                        .personCount(6)
                        .size(85)
                        .isAvailability(true)
                        .build()
        );

        Mockito.when(roomTypeAvailabilityService.findRoomTypesAvailability()).thenReturn(mockRoomTypeAvailabilityResponses);

        //Then
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/RoomTypeAvailability")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()", Matchers.equalTo(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].name", Matchers.everyItem(Matchers.matchesPattern(".*test.*"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].size", Matchers.everyItem(Matchers.allOf(Matchers.greaterThan(20), Matchers.lessThan(300)))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].personCount", Matchers.everyItem(Matchers.allOf(Matchers.greaterThan(1), Matchers.lessThan(15)))))
        ;

        //Verify
        Mockito.verify(roomTypeAvailabilityService, Mockito.times(1)).findRoomTypesAvailability();
    }

}