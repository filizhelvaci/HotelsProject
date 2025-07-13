package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.cleaner.DepartmentTestCleaner;
import com.flz.model.Department;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
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
public class DepartmentEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentReadPort departmentReadPort;

    @Autowired
    private DepartmentSavePort departmentSavePort;

    @Autowired
    private DepartmentTestCleaner testCleaner;

    @BeforeEach
    void cleanBeforeTest() {
        testCleaner.cleanTestDepartments();
    }

    private static final String BASE_PATH = "/api/v1";

    @Test
    void givenCreateRequest_whenCreateDepartment_thenReturnSuccess() throws Exception {

        //Given
        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
                .name("test - temizlik")
                .build();

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Then
        boolean isExistsDepartment = departmentReadPort
                .existsByName(createRequest.getName());

        //Verify
        Assertions.assertTrue(isExistsDepartment);

    }

    @Test
    void givenUpdateRequest_whenUpdateDepartment_thenReturnSuccess() throws Exception {

        //Given
        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
                .name("test - Reklam")
                .build();

        MockHttpServletRequestBuilder createRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        mockMvc.perform(createRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        Thread.sleep(5000);

        List<Department> departments = departmentReadPort.findSummaryAll();
        Department createdDepartment = departments.stream()
                .filter(d -> d.getName()
                        .equals("test - Reklam"))
                .findFirst()
                .orElseThrow();

        Long departmentId = createdDepartment.getId();

        //When
        DepartmentUpdateRequest updateRequest = DepartmentUpdateRequest.builder()
                .name("test - updated")
                .build();

        MockHttpServletRequestBuilder updateRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/department/" + departmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequest));

        mockMvc.perform(updateRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Then
        boolean isOldNameStillExists = departmentReadPort.existsByName("test - Reklam");
        boolean isUpdatedNameExists = departmentReadPort.existsByName("test - updated");

        Assertions.assertFalse(isOldNameStillExists);
        Assertions.assertTrue(isUpdatedNameExists);
    }

    @Test
    void givenDepartmentId_whenDeleteDepartment_thenDeleted() throws Exception {

        //Given
        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
                .name("test - request")
                .build();

        MockHttpServletRequestBuilder createRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        mockMvc.perform(createRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        Thread.sleep(5000);

        List<Department> departments = departmentReadPort.findSummaryAll();
        Department createdDepartment = departments.stream()
                .filter(d -> d.getName()
                        .equals("test - request"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Created department not found"));

        Long departmentId = createdDepartment.getId();

        //When
        MockHttpServletRequestBuilder deleteRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/department/" + departmentId);

        mockMvc.perform(deleteRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Then
        Optional<Department> deletedDepartmentOpt = departmentReadPort.findById(departmentId);
        Assertions.assertEquals(deletedDepartmentOpt.get()
                .getName(), createdDepartment.getName());
        Assertions.assertTrue(deletedDepartmentOpt.get()
                .isDeleted());
        Assertions.assertEquals(deletedDepartmentOpt.get()
                .getId(), departmentId);
        Assertions.assertEquals(deletedDepartmentOpt.get()
                .getStatus(), DepartmentStatus.DELETED);
        Assertions.assertNotEquals(createdDepartment
                .getStatus(), DepartmentStatus.DELETED);
        Assertions.assertNotEquals(deletedDepartmentOpt.get()
                .getStatus(), createdDepartment.getStatus());
        Assertions.assertNotNull(deletedDepartmentOpt.get()
                .getCreatedAt());
        Assertions.assertNotNull(deletedDepartmentOpt.get()
                .getCreatedBy());
    }

    @Test
    void whenFindAllDepartments_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments")
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
                        .value("Ön Büro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].status")
                        .value("ACTIVE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdBy")
                        .value("Admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdAt")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].deleted")
                        .value(false));

        //Then
        List<Department> departments = departmentReadPort.findAll(1, 10);
        Assertions.assertNotNull(departments);
        Assertions.assertFalse(departments.isEmpty());
        Assertions.assertNotNull(departments.get(0)
                .getName());
        Assertions.assertNotNull(departments.get(0)
                .getStatus());
        Assertions.assertNotNull(departments.get(0)
                .getCreatedAt());
        Assertions.assertNotNull(departments.get(0)
                .getCreatedBy());
        Assertions.assertNotNull(departments.get(0)
                .getId());

    }

    @Test
    void whenFindSummaryAllDepartments_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments/summary")
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
                        .value("Ön Büro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber());

        //Then
        List<Department> departments = departmentReadPort.findSummaryAll();
        Assertions.assertNotNull(departments);
        Assertions.assertFalse(departments.isEmpty());
        Assertions.assertNotNull(departments.get(0)
                .getName());
        Assertions.assertNotNull(departments.get(0)
                .getId());
    }

}