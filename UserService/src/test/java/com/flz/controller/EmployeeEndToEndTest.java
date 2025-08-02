package com.flz.controller;

import com.flz.BaseEndToEndTest;
import com.flz.model.Employee;
import com.flz.model.enums.Gender;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.EmployeeUpdateRequest;
import com.flz.port.EmployeeReadPort;
import com.flz.port.EmployeeSavePort;
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
import java.util.List;
import java.util.Optional;

class EmployeeEndToEndTest extends BaseEndToEndTest {

    @Autowired
    private EmployeeReadPort employeeReadPort;

    @Autowired
    private EmployeeSavePort employeeSavePort;

    private static final String BASE_PATH = "/api/v1";

    @Test
    void whenFindAllEmployees_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees")
                .param("page", "1")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].firstName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].firstName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].lastName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].lastName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdAt")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdBy")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdBy")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdAt")
                        .isNotEmpty());

        //Then
        List<Employee> employeeList = employeeReadPort.findAll(1, 10);

        Assertions.assertNotNull(employeeList);
        Assertions.assertFalse(employeeList.isEmpty());
        Assertions.assertNotNull(employeeList.get(0).getFirstName());
        Assertions.assertNotNull(employeeList.get(0).getLastName());
        Assertions.assertNotNull(employeeList.get(0).getCreatedAt());
        Assertions.assertNotNull(employeeList.get(0).getCreatedBy());
        Assertions.assertNotNull(employeeList.get(0).getId());

    }

    @Test
    void whenFindSummaryAllEmployees_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees/summary")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].firstName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].lastName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].firstName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].lastName")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber());

        //Then
        List<Employee> employees = employeeReadPort.findSummaryAll();

        Assertions.assertNotNull(employees);
        Assertions.assertFalse(employees.isEmpty());
        Assertions.assertNotNull(employees.get(0).getFirstName());
        Assertions.assertNotNull(employees.get(0).getLastName());
        Assertions.assertNotNull(employees.get(0).getId());
    }

    @Test
    void givenCreateRequest_whenCreateEmployee_thenReturnSuccess() throws Exception {

        //Given
        EmployeeCreateRequest mockCreateRequest = EmployeeCreateRequest.builder()
                .identityNumber("25996700465")
                .firstName("test - Ali")
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
        String endpoint = BASE_PATH + "/employee";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));
    }

    @Test
    void givenUpdateRequest_whenUpdateEmployee_thenReturnSuccess() throws Exception {

        Employee employee = Employee.builder()
                .firstName("Test First Name")
                .lastName("Test Last Name")
                .identityNumber("99999999999")
                .email("old@test.com")
                .phoneNumber("05551234567")
                .address("Old Address")
                .birthDate(LocalDate.parse("2000-10-01"))
                .gender(Gender.MALE)
                .nationality("TR")
                .build();
        Optional<Employee> savedEmployee = employeeSavePort.save(employee);

        Long employeeId = savedEmployee.get().getId();


        EmployeeUpdateRequest updateRequest = EmployeeUpdateRequest.builder()
                .firstName("Updated Name")
                .lastName("Updated Last")
                .email("updated@test.com")
                .phoneNumber("05559876543")
                .address("Updated Address")
                .nationality("Updated Country")
                .identityNumber("1234569789")
                .birthDate(LocalDate.parse("2000-10-01"))
                .gender(Gender.MALE)
                .build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BASE_PATH + "/employee/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest));

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

    }

}
