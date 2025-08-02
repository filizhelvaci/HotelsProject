package com.flz.controller;

import com.flz.BaseTest;
import com.flz.model.request.EmployeeExperienceCreateRequest;
import com.flz.service.EmployeeExperienceWriteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
import java.time.LocalDate;
import java.util.stream.Stream;


@WebMvcTest(EmployeeExperienceController.class)
class EmployeeExperienceControllerTest extends BaseTest {

    @MockBean
    EmployeeExperienceWriteService employeeExperienceWriteService;

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * {@link EmployeeExperienceController#create(Long, EmployeeExperienceCreateRequest)}
     */
    @Test
    void givenValidEmployeeExperienceCreateRequest_whenEmployeeExperienceCreated_thenReturnSuccess() throws Exception {

        //Given
        Long mockId = 1L;

        EmployeeExperienceCreateRequest mockCreateRequest = EmployeeExperienceCreateRequest.builder()
                .salary(BigDecimal.valueOf(10000))
                .positionId(10L)
                .supervisorId(20L)
                .startDate(LocalDate.of(2025, 10, 2))
                .build();

        //When
        Mockito.doNothing()
                .when(employeeExperienceWriteService)
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .doesNotExist());

        //Verify
        Mockito.verify(employeeExperienceWriteService, Mockito.times(1))
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));

    }

    @Test
    void givenInvalidStartDateCreateRequest_whenCreateEmployeeExperience_thenReturnBadRequest() throws Exception {

        //Given
        Long mockId = 1L;
        LocalDate mockStartDate = LocalDate.of(2010, 10, 2);

        EmployeeExperienceCreateRequest mockCreateRequest = EmployeeExperienceCreateRequest.builder()
                .salary(BigDecimal.valueOf(10000))
                .positionId(10L)
                .supervisorId(20L)
                .startDate(mockStartDate)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeExperienceWriteService, Mockito.never())
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSalaries")
    void givenInvalidSalaryCreateRequest_whenCreateEmployeeExperience_thenReturnBadRequest(BigDecimal invalidSalary) throws Exception {

        //Given
        Long mockId = 1L;

        EmployeeExperienceCreateRequest mockCreateRequest = EmployeeExperienceCreateRequest.builder()
                .salary(invalidSalary)
                .positionId(10L)
                .supervisorId(20L)
                .startDate(LocalDate.now())
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeExperienceWriteService, Mockito.never())
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));
    }

    private static Stream<BigDecimal> provideInvalidSalaries() {
        return Stream.of(
                new BigDecimal("-1"),
                new BigDecimal("100000000")
        );
    }

    @Test
    void givenNullSalaryCreateRequest_whenCreateEmployeeExperience_thenReturnBadRequest() throws Exception {

        //Given
        Long mockId = 1L;

        EmployeeExperienceCreateRequest mockCreateRequest = EmployeeExperienceCreateRequest.builder()
                .salary(null)
                .positionId(10L)
                .supervisorId(20L)
                .startDate(LocalDate.now())
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeExperienceWriteService, Mockito.never())
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));
    }

    @Test
    void givenNullPositionIdCreateRequest_whenCreateEmployeeExperience_thenReturnBadRequest() throws Exception {

        //Given
        Long mockId = 1L;

        EmployeeExperienceCreateRequest mockCreateRequest = EmployeeExperienceCreateRequest.builder()
                .salary(BigDecimal.valueOf(10000))
                .positionId(null)
                .supervisorId(20L)
                .startDate(LocalDate.now())
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeExperienceWriteService, Mockito.never())
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));
    }

    @Test
    void givenInvalidPositionIdCreateRequest_whenCreateEmployeeExperience_thenReturnBadRequest() throws Exception {

        //Given
        Long mockId = 1L;

        EmployeeExperienceCreateRequest mockCreateRequest = EmployeeExperienceCreateRequest.builder()
                .salary(BigDecimal.valueOf(10000))
                .positionId(-10L)
                .supervisorId(20L)
                .startDate(LocalDate.now())
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeExperienceWriteService, Mockito.never())
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));
    }

    @Test
    void givenNullSupervisorIdCreateRequest_whenCreateEmployeeExperience_thenReturnBadRequest() throws Exception {

        //Given
        Long mockId = 1L;

        EmployeeExperienceCreateRequest mockCreateRequest = EmployeeExperienceCreateRequest.builder()
                .salary(BigDecimal.valueOf(10000))
                .positionId(10L)
                .supervisorId(null)
                .startDate(LocalDate.now())
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeExperienceWriteService, Mockito.never())
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));
    }

    @Test
    void givenInvalidSupervisorIdCreateRequest_whenCreateEmployeeExperience_thenReturnBadRequest() throws Exception {

        //Given
        Long mockId = 1L;

        EmployeeExperienceCreateRequest mockCreateRequest = EmployeeExperienceCreateRequest.builder()
                .salary(BigDecimal.valueOf(10000))
                .positionId(10L)
                .supervisorId(-20L)
                .startDate(LocalDate.now())
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeExperienceWriteService, Mockito.never())
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));
    }

    @Test
    void givenNullStartDateCreateRequest_whenCreateEmployeeExperience_thenReturnBadRequest() throws Exception {

        //Given
        Long mockId = 1L;

        EmployeeExperienceCreateRequest mockCreateRequest = EmployeeExperienceCreateRequest.builder()
                .salary(BigDecimal.valueOf(10000))
                .positionId(10L)
                .supervisorId(20L)
                .startDate(null)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeExperienceWriteService, Mockito.never())
                .create(Mockito.anyLong(), Mockito.any(EmployeeExperienceCreateRequest.class));
    }
}