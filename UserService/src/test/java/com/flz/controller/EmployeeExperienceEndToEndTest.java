package com.flz.controller;

import com.flz.BaseEndToEndTest;
import com.flz.model.EmployeeExperience;
import com.flz.model.request.EmployeeExperienceCreateRequest;
import com.flz.port.EmployeeExperienceReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

class EmployeeExperienceEndToEndTest extends BaseEndToEndTest {

    private static final String BASE_PATH = "/api/v1";
    @Autowired
    private EmployeeExperienceReadPort employeeExperienceReadPort;

    @Test
    void givenCreateRequest_whenCreateEmployeeExperience_thenReturnSuccess() throws Exception {

        //Given
        EmployeeExperienceCreateRequest createRequest = EmployeeExperienceCreateRequest.builder()
                .salary(BigDecimal.valueOf(50000))
                .positionId(5L)
                .supervisorId(2L)
                .startDate(LocalDate.now().plusDays(5))
                .build();

        Long employeeId = 10L;

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Optional<EmployeeExperience> employeeExperience = employeeExperienceReadPort
                .findTopByEmployeeIdOrderByStartDateDesc(employeeId);

        Assertions.assertTrue(employeeExperience.isPresent());
        Assertions.assertEquals(createRequest.getPositionId(),
                employeeExperience.get().getPosition().getId());
        Assertions.assertEquals(createRequest.getSupervisorId(),
                employeeExperience.get().getSupervisor().getId());
        Assertions.assertEquals(createRequest.getStartDate(),
                employeeExperience.get().getStartDate());
        Assertions.assertNotNull(employeeExperience.get().getCreatedAt());
        Assertions.assertNotNull(employeeExperience.get().getCreatedBy());

    }
}
