package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseEndToEndTest;
import com.flz.model.Department;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentTestPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

class DepartmentEndToEndTest extends BaseEndToEndTest {

    @Autowired
    private DepartmentReadPort departmentReadPort;

    @Autowired
    private DepartmentTestPort departmentTestPort;

    private static final String BASE_PATH = "/api/v1";

    @Test
    void givenCreateRequest_whenCreateDepartment_thenReturnSuccess() throws Exception {

        //Given
        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
                .name("Otopark Bakım")
                .build();

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Department createdDepartment = departmentTestPort.findByName("Otopark Bakım");

        Assertions.assertNotNull(createdDepartment);
        Assertions.assertNotNull(createdDepartment.getId());
        Assertions.assertNotNull(createdDepartment.getName());
        Assertions.assertNotNull(createdDepartment.getStatus());
        Assertions.assertEquals(createdDepartment.getName(), createRequest.getName());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, createdDepartment.getStatus());

    }


    @Test
    void givenUpdateRequest_whenUpdateDepartment_thenReturnSuccess() throws Exception {

        //Initialize
        Department departmentSaved = departmentTestPort.save(
                Department.builder()
                        .name("Kat Güvenlik")
                        .status(DepartmentStatus.ACTIVE)
                        .build());

        DepartmentUpdateRequest updateRequest = DepartmentUpdateRequest.builder()
                .name("Kat Güvenlik Departmani")
                .build();

        //Given
        Long departmentId = departmentSaved.getId();

        //When
        MockHttpServletRequestBuilder updateRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/department/" + departmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequest));

        //Then
        mockMvc.perform(updateRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Department department = departmentTestPort.findByName(updateRequest.getName());

        Assertions.assertNotNull(department);
        Assertions.assertNotNull(department.getId());
        Assertions.assertNotNull(department.getName());
        Assertions.assertNotNull(department.getStatus());
        Assertions.assertEquals(department.getName(), updateRequest.getName());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, department.getStatus());

    }


    @Test
    void givenDepartmentId_whenDeleteDepartment_thenSoftDeleted() throws Exception {

        //Initialize
        Department departmentSaved = departmentTestPort.save(
                Department.builder()
                        .name("Teras Güvenlik")
                        .status(DepartmentStatus.ACTIVE)
                        .build());

        //Given
        Long departmentId = departmentSaved.getId();

        //When
        MockHttpServletRequestBuilder deleteRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/department/" + departmentId);

        //Then
        mockMvc.perform(deleteRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Optional<Department> deletedDepartment = departmentReadPort.findById(departmentId);

        Assertions.assertTrue(deletedDepartment.isPresent());
        Assertions.assertEquals(departmentId, deletedDepartment.get().getId());
        Assertions.assertEquals(departmentSaved.getName(), deletedDepartment.get().getName());
        Assertions.assertNotEquals(departmentSaved.getStatus(), deletedDepartment.get().getStatus());
        Assertions.assertEquals(DepartmentStatus.DELETED, deletedDepartment.get().getStatus());
        Assertions.assertNotNull(deletedDepartment.get().getCreatedAt());
        Assertions.assertNotNull(deletedDepartment.get().getCreatedBy());

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
