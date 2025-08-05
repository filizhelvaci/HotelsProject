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
                .identityNumber("25996700777")
                .firstName("Ali Veli")
                .lastName("Semih")
                .phoneNumber("05052221133")
                .email("alivelisemih@gmail.com")
                .address("Bursa")
                .birthDate(LocalDate.parse("1999-10-01"))
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
                .firstName("Hasan")
                .lastName("Turan")
                .identityNumber("99991119999")
                .email("hasan@test.com")
                .phoneNumber("05051234567")
                .address("Ankara")
                .birthDate(LocalDate.parse("2000-10-01"))
                .gender(Gender.MALE)
                .nationality("TR")
                .build();
        Optional<Employee> savedEmployee = employeeSavePort.save(employee);

        Long employeeId = savedEmployee.orElseThrow().getId();

        EmployeeUpdateRequest updateRequest = EmployeeUpdateRequest.builder()
                .firstName("Mahmut")
                .lastName("Korur")
                .email("mahmut@gmail.com")
                .phoneNumber("05327776543")
                .address("Adana")
                .nationality("Almanya")
                .identityNumber("1234554321")
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

        Optional<Employee> updatedEmployee = employeeReadPort.findById(employeeId);

        Assertions.assertNotNull(updatedEmployee);
        Assertions.assertTrue(updatedEmployee.isPresent());
        Assertions.assertEquals(updatedEmployee.get().getIdentityNumber(),
                updateRequest.getIdentityNumber());
        Assertions.assertEquals(updatedEmployee.get().getFirstName(),
                updateRequest.getFirstName());
        Assertions.assertEquals(updatedEmployee.get().getLastName(),
                updateRequest.getLastName());
        Assertions.assertEquals(updatedEmployee.get().getAddress(),
                updateRequest.getAddress());
        Assertions.assertEquals(updatedEmployee.get().getEmail(),
                updateRequest.getEmail());

    }

    @Test
    void givenEmployeeId_whenDeleteEmployee_thenDeleted() throws Exception {

        //Initialize
        Employee employee = Employee.builder()
                .firstName("Bayram")
                .lastName("Seyran")
                .identityNumber("99988877111")
                .email("bayram@yahoo.com")
                .phoneNumber("05051231236")
                .address("Konya")
                .birthDate(LocalDate.parse("1999-10-01"))
                .gender(Gender.MALE)
                .nationality("TR")
                .build();
        Optional<Employee> savedEmployee = employeeSavePort.save(employee);

        //Given
        Long employeeId = savedEmployee.orElseThrow().getId();

        //When
        MockHttpServletRequestBuilder deleteRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/employee/" + employeeId);

        mockMvc.perform(deleteRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Then
        Optional<Employee> deletedEmployee = employeeReadPort.findById(employeeId);

        Assertions.assertFalse(deletedEmployee.isPresent());

    }
}
