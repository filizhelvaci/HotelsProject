package com.flz.controller;

import com.flz.BaseTest;
import com.flz.model.Employee;
import com.flz.model.enums.Gender;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.response.EmployeeSummaryResponse;
import com.flz.service.EmployeeReadService;
import com.flz.service.EmployeeWriteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest extends BaseTest {

    @MockBean
    EmployeeReadService employeeReadService;

    @MockBean
    EmployeeWriteService employeeWriteService;

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * {@link EmployeeController#findAll(com.flz.model.request.PageRequest)}
     */
    @Test
    public void givenPageAndPageSize_whenCalledAllEmployee_thenReturnAllEmployeesSuccessfully() throws Exception {

        //Given
        List<Employee> mockEmployees = getEmployees();

        //When
        Mockito.when(employeeReadService.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(mockEmployees);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees")
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
                        .value(mockEmployees.size()))
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
        Mockito.verify(employeeReadService, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void whenPageSizeDifferentThanValidValueInEmployeeFindAll_thenReturnBadRequestError() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees")
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
        Mockito.verify(employeeReadService, Mockito.never())
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void whenPageDifferentThanValidValueInEmployeeFindAll_thenReturnBadRequestError() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees")
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
        Mockito.verify(employeeReadService, Mockito.never())
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void givenValidPageAndPageSize_whenCalledAllEmployees_thenReturnEmptyList() throws Exception {

        //Given
        Integer mockPageSize = 10;
        Integer mockPage = 1;

        List<Employee> emptyEmployees = List.of();

        //When
        Mockito.when(employeeReadService.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(emptyEmployees);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees")
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
        Mockito.verify(employeeReadService, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    /**
     * {@link EmployeeController#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryEmployee_thenReturnEmployeesSummaryResponse() throws Exception {

        //Given
        List<EmployeeSummaryResponse> mockEmployeeSummaryResponse = List.of(
                EmployeeSummaryResponse.builder()
                        .id(11L)
                        .firstName("Joe")
                        .lastName("Doe")
                        .build(),
                EmployeeSummaryResponse.builder()
                        .id(12L)
                        .firstName("John")
                        .lastName("Smith")
                        .build(),
                EmployeeSummaryResponse.builder()
                        .id(13L)
                        .firstName("Jane")
                        .lastName("Jones")
                        .build()
        );

        //When
        Mockito.when(employeeReadService.findSummaryAll())
                .thenReturn(mockEmployeeSummaryResponse);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees/summary")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].firstName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].lastName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(employeeReadService, Mockito.times(1))
                .findSummaryAll();
    }

    @Test
    public void whenNotFoundEmployeesSummaryAll_thenReturnEmptyList() throws Exception {

        //When
        List<EmployeeSummaryResponse> emptyList = Collections.emptyList();

        Mockito.when(employeeReadService.findSummaryAll())
                .thenReturn(emptyList);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees/summary")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isEmpty());

        //Verify
        Mockito.verify(employeeReadService, Mockito.times(1))
                .findSummaryAll();
    }

    /**
     * {@link EmployeeController#create(EmployeeCreateRequest)}
     */
    @Test
    void givenValidEmployeeCreateRequest_whenEmployeeCreated_thenReturnSuccess() throws Exception {

        //Given
        EmployeeCreateRequest mockCreateRequest = EmployeeCreateRequest.builder()
                .identityNumber("25996700465")
                .firstName("Ali")
                .lastName("Semih")
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.parse("2000-10-01"))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.parse("2025-10-01"))
                .build();

        //When
        Mockito.doNothing()
                .when(employeeWriteService)
                .create(Mockito.any(EmployeeCreateRequest.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
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
        Mockito.verify(employeeWriteService, Mockito.times(1))
                .create(Mockito.any(EmployeeCreateRequest.class));

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "a",
            "One morning, when Gregor Samsa woke from troubled dre"
    })
    void givenInvalidFirstNameCreateRequest_whenCreateEmployee_thenReturnBadRequest(String invalidName) throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName(invalidName)
                .lastName("Semih")
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "a",
            "One morning, when Gregor Samsa woke from troubled dre"
    })
    void givenInvalidLastNameCreateRequest_whenCreateEmployee_thenReturnBadRequest(String invalidName) throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName(invalidName)
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "1234567890",
            "01234567890",
            "111111111111",
            "abcdefg1234"
    })
    void givenInvalidIdentityNumberCreateRequest_whenCreateEmployee_thenReturnBadRequest(String invalidName) throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber(invalidName)
                .firstName("Ali")
                .lastName("Semih")
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "abc",
            "abc@",
            "abc@com",
            "abc@example.",
            "abc@.com",
            "abc@exa mple.com",
            "abc@exam_ple.com",
            "abc@example.toolongtld"
    })
    void givenInvalidEmailCreateRequest_whenCreateEmployee_thenReturnBadRequest(String invalidName) throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Ali")
                .lastName("Semih")
                .phoneNumber("05332221133")
                .email(invalidName)
                .address("Bursa")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "532221133",
            "+905322211",
            "0533222113344",
            "abc5322211",
            "0533-222-1133",
            "0533 222 1133",
            "++90532221133"
    })
    void givenInvalidPhoneNumberInRequest_whenCreateEmployee_thenReturnBadRequest(String invalidValue) throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Ali")
                .lastName("Semih")
                .phoneNumber(invalidValue)
                .email("alisemih@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "a",
            "One morning, when Gregor Samsa woke from troubled dreams, " +
                    "he found himself transformed in his bed into a horrible vermin. " +
                    "He lay on his armour-like back, and if he lifted his head a little " +
                    "he could see his brown belly, slightly domed and divided by arches."
    })
    void givenInvalidAddressCreateRequest_whenCreateEmployee_thenReturnBadRequest(String invalidName) throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Yılmaz")
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address(invalidName)
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @Test
    void givenInvalidBirthDateCreateRequest_whenCreateEmployee_thenReturnBadRequest() throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Yılmaz")
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.now()
                        .plusDays(1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @Test
    void givenNullBirthDateCreateRequest_whenCreateEmployee_thenReturnBadRequest() throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Yılmaz")
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address("Bursa")
                .birthDate(null)
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @Test
    void givenNullGenderCreateRequest_whenCreateEmployee_thenReturnBadRequest() throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Yılmaz")
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(2000, 9, 1))
                .gender(null)
                .nationality("TC")
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "a",
            "One morning, when Gregor Samsa woke from troubled dreams, " +
                    "he found himself transformed in his bed into a horrible vermin. " +
                    "He lay on his armour-like back, and if he lifted his head a little " +
                    "he could see his brown belly, slightly domed and divided by arches."
    })
    void givenInvalidNationalityCreateRequest_whenCreateEmployee_thenReturnBadRequest(String invalidName) throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Yılmaz")
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .nationality(invalidName)
                .salary(BigDecimal.valueOf(20000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidSalaries")
    void givenInvalidSalaryCreateRequest_whenCreateEmployee_thenReturnBadRequest(BigDecimal invalidSalary) throws Exception {

        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Yılmaz")
                .phoneNumber("05332221133")
                .email("alisemih@gmail.com")
                .address("Test Mahallesi")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(invalidSalary)
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    private static Stream<BigDecimal> provideInvalidSalaries() {
        return Stream.of(
                new BigDecimal("-1"),
                new BigDecimal("0"),
                new BigDecimal("10000000.01")
        );
    }

    @Test
    void givenNullSalaryCreateRequest_whenCreateEmployee_thenReturnBadRequest() throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Ay")
                .phoneNumber("05332221133")
                .email("semihh@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(2000, 9, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(null)
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @Test
    void givenNullPositionIdCreateRequest_whenCreateEmployee_thenReturnBadRequest() throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Ay")
                .phoneNumber("05332221133")
                .email("semihh@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(2000, 9, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(25000))
                .positionId(null)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @Test
    void givenNullDepartmentIdCreateRequest_whenCreateEmployee_thenReturnBadRequest() throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Ay")
                .phoneNumber("05332221133")
                .email("semihh@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(2000, 9, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(25000))
                .positionId(1L)
                .departmentId(null)
                .supervisorId(2L)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @Test
    void givenNullSupervisorIdCreateRequest_whenCreateEmployee_thenReturnBadRequest() throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Ay")
                .phoneNumber("05332221133")
                .email("semihh@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(2000, 9, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(25000))
                .positionId(3L)
                .departmentId(5L)
                .supervisorId(null)
                .startDate(LocalDate.of(2025, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @Test
    void givenNullStartDateCreateRequest_whenCreateEmployee_thenReturnBadRequest() throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Ay")
                .phoneNumber("05332221133")
                .email("semihh@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(2000, 9, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(25000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(null)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    @Test
    void givenInvalidStartDateCreateRequest_whenCreateEmployee_thenReturnBadRequest() throws Exception {

        //Given
        EmployeeCreateRequest invalidRequest = EmployeeCreateRequest.builder()
                .identityNumber("1111111111111")
                .firstName("Semih")
                .lastName("Ay")
                .phoneNumber("05332221133")
                .email("semihh@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.of(2000, 9, 1))
                .gender(Gender.MALE)
                .nationality("TC")
                .salary(BigDecimal.valueOf(25000))
                .positionId(1L)
                .departmentId(3L)
                .supervisorId(2L)
                .startDate(LocalDate.of(2020, 9, 1))
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());

        //Verify
        Mockito.verify(employeeWriteService, Mockito.never())
                .create(Mockito.any(EmployeeCreateRequest.class));
    }

    private static List<Employee> getEmployees() {
        return List.of(
                Employee.builder()
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
                Employee.builder()
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
                Employee.builder()
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

}