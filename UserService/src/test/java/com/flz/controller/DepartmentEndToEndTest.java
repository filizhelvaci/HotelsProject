package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.model.Department;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
import org.junit.jupiter.api.Assertions;
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

@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentReadPort departmentReadPort;

    @Autowired
    private DepartmentSavePort departmentSavePort;

    private static final String BASE_PATH = "/api/v1";

    @Test
    void givenCreateRequest_whenCreateDepartment_thenReturnSuccess() throws Exception {

        //
        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
                .name("Finance")
                .build();

        //Then
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

        boolean isExistsDepartment = departmentReadPort.existsByName(createRequest.getName());


        //Verify
        Assertions.assertTrue(isExistsDepartment);

    }

    @Test
    void givenUpdateRequest_whenUpdateDepartment_thenReturnSuccess() throws Exception {

        //Given
        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
                .name("Reklam")
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
                        .equals("Reklam"))
                .findFirst()
                .orElseThrow();

        Long departmentId = createdDepartment.getId();

        //When
        DepartmentUpdateRequest updateRequest = DepartmentUpdateRequest.builder()
                .name("Updated Finance")
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
        boolean isOldNameStillExists = departmentReadPort.existsByName("Reklam");
        boolean isUpdatedNameExists = departmentReadPort.existsByName("Updated Finance");

        Assertions.assertFalse(isOldNameStillExists);
        Assertions.assertTrue(isUpdatedNameExists);
    }


}