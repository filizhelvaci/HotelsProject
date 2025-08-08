package com.flz.controller;

import com.flz.BaseTest;
import com.flz.exception.EmployeeOldNotFoundException;
import com.flz.model.EmployeeOld;
import com.flz.model.enums.Gender;
import com.flz.model.response.EmployeeOldDetailsResponse;
import com.flz.model.response.EmployeeOldExperienceResponse;
import com.flz.service.EmployeeOldReadService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@WebMvcTest(EmployeeOldController.class)
class EmployeeOldControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeOldReadService employeeOldReadService;

    private static final String BASE_PATH = "/api/v1";

    //Initialize
    private static List<EmployeeOld> getEmployeeOlds() {
        return List.of(
                EmployeeOld.builder()
                        .id(1L)
                        .firstName("test first name 1")
                        .lastName("test last name 1")
                        .address("test address 1")
                        .birthDate(LocalDate.parse("2000-01-01"))
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .email("test1@gmail.com")
                        .gender(Gender.FEMALE)
                        .nationality("TC")
                        .phoneNumber("05465321456")
                        .build(),
                EmployeeOld.builder()
                        .id(2L)
                        .firstName("test first name 2")
                        .lastName("test last name 2 ")
                        .address("test address 2")
                        .birthDate(LocalDate.parse("2000-02-02"))
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .email("test2@gmail.com")
                        .gender(Gender.FEMALE)
                        .nationality("TC")
                        .phoneNumber("05465321465")
                        .build(),
                EmployeeOld.builder()
                        .id(3L)
                        .firstName("test first name 3")
                        .lastName("test last name 3")
                        .address("test address 3")
                        .birthDate(LocalDate.parse("2000-03-03"))
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .email("test3@gmail.com")
                        .gender(Gender.FEMALE)
                        .nationality("TC")
                        .phoneNumber("05465321499")
                        .build());
    }

    /**
     * {@link EmployeeOldController#findById(Long)} ()}
     */
    @Test
    void givenValidId_whenCalledFindByIdEmployeeOld_thenReturnEmployeeOldDetailResponseSuccessful() throws Exception {

        //Given
        Long mockId = 1L;

        EmployeeOldDetailsResponse mockEmployeeOldDetailsResponse = EmployeeOldDetailsResponse.builder()
                .employeeOld(getEmployeeOld())
                .experiences(List.of(getEmployeeOldExperienceResponse()))
                .build();

        //When
        Mockito.when(employeeOldReadService.findById(mockId))
                .thenReturn(mockEmployeeOldDetailsResponse);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employee-old/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.firstName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.lastName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.phoneNumber")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.address")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.birthDate")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.gender")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[*].salary")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[*].startDate")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[*].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[*].positionName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[*].supervisorName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(employeeOldReadService, Mockito.times(1))
                .findById(Mockito.anyLong());

    }

    @Test
    void givenNonEmployeeOldId_whenCalledFindByIdEmployeeOld_thenReturnNotFoundStatusAndErrorMessage() throws Exception {

        //Given
        Long mockId = 999L;

        //When
        Mockito.when(employeeOldReadService.findById(mockId))
                .thenThrow(new EmployeeOldNotFoundException(mockId));

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employee-old/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .doesNotExist());


        //Verify
        Mockito.verify(employeeOldReadService, Mockito.times(1))
                .findById(Mockito.anyLong());

    }

    @Test
    void givenInvalidId_whenCalledFindByIdEmployeeOld_thenReturnBadRequest() throws Exception {

        //Given
        String invalidId = "abc";

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employee-old/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .doesNotExist());

        // Verify
        Mockito.verify(employeeOldReadService, Mockito.never())
                .findById(Mockito.anyLong());

    }

    /**
     * {@link EmployeeOldController#findAll(com.flz.model.request.PageRequest)}
     */
    @Test
    void givenPageAndPageSize_whenCalledAllEmployeeOld_thenReturnAllEmployeeOldsSuccessfully() throws Exception {

        //Given
        List<EmployeeOld> mockEmployeesOld = getEmployeeOlds();

        //When
        Mockito.when(employeeOldReadService.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(mockEmployeesOld);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees-old")
                .param("page", "1")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()")
                        .value(mockEmployeesOld.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].firstName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].lastName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].identityNumber")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].phoneNumber")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(employeeOldReadService, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());

    }

    @Test
    void whenPageSizeDifferentThanValidValueInEmployeeOldFindAll_thenReturnBadRequestError() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees-old")
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
        Mockito.verify(employeeOldReadService, Mockito.never())
                .findAll(Mockito.anyInt(), Mockito.anyInt());

    }

    @Test
    void whenPageDifferentThanValidValueInEmployeeOldFindAll_thenReturnBadRequestError() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees-old")
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
        Mockito.verify(employeeOldReadService, Mockito.never())
                .findAll(Mockito.anyInt(), Mockito.anyInt());

    }

    @Test
    void givenValidPageAndPageSize_whenCalledAllEmployeesOld_thenReturnEmptyList() throws Exception {

        //Given
        Integer mockPageSize = 10;
        Integer mockPage = 1;

        List<EmployeeOld> emptyEmployees = List.of();

        //When
        Mockito.when(employeeOldReadService.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(emptyEmployees);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees-old")
                .param("page", String.valueOf(mockPage))
                .param("pageSize", String.valueOf(mockPageSize))
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()")
                        .value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        // Verify
        Mockito.verify(employeeOldReadService, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());

    }

    private static EmployeeOld getEmployeeOld() {
        return EmployeeOld.builder()
                .id(1L)
                .firstName("test first name 1")
                .lastName("test last name 1")
                .address("test address 1")
                .birthDate(LocalDate.parse("2000-01-01"))
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .email("test1@gmail.com")
                .gender(Gender.FEMALE)
                .nationality("TC")
                .phoneNumber("05465321456")
                .build();
    }

    private static EmployeeOldExperienceResponse getEmployeeOldExperienceResponse() {

        return EmployeeOldExperienceResponse.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(100000))
                .startDate(LocalDate.now()
                        .plusDays(2))
                .endDate(LocalDate.now()
                        .plusMonths(6))
                .positionName("Test position")
                .departmentName("Test department")
                .supervisorName("Supervisor name")
                .build();
    }

}
