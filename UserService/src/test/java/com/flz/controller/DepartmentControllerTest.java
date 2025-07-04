package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.response.DepartmentSummaryResponse;
import com.flz.service.DepartmentCreateService;
import com.flz.service.DepartmentReadService;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest extends BaseTest {

    @MockBean
    DepartmentCreateService departmentCreateService;

    @MockBean
    DepartmentReadService departmentReadService;

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_PATH = "/api/v1";

    /**
     * {@link DepartmentController#findAll(com.flz.model.request.PageRequest)}
     */
    @Test
    public void givenPageAndPageSize_whenCalledAllDepartment_thenReturnAllDepartmentsSuccessfully() throws Exception {

        //Given
        List<Department> mockDepartments = getDepartments();

        //When
        Mockito.when(departmentReadService.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(mockDepartments);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments")
                .param("page", "1")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()")
                        .value(mockDepartments.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].status")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(departmentReadService, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void whenPageSizeDifferentThanValidValueInFindAll_thenReturnBadRequestError() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments")
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
        Mockito.verify(departmentReadService, Mockito.never())
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void whenPageDifferentThanValidValueInFindAll_thenReturnBadRequestError() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments")
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
        Mockito.verify(departmentReadService, Mockito.never())
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void givenValidPageAndPageSize_whenCalledAllDepartment_thenReturnEmptyList() throws Exception {

        //Given
        Integer mockPageSize = 10;
        Integer mockPage = 1;

        List<Department> emptyDepartments = List.of();

        //When
        Mockito.when(departmentReadService.findAll(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(emptyDepartments);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments")
                .param("page", String.valueOf(mockPage))
                .param("pageSize", String.valueOf(mockPageSize))
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()")
                        .value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        // Verify
        Mockito.verify(departmentReadService, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }

    /**
     * {@link DepartmentController#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryRoom_thenReturnRoomsSummaryResponse() throws Exception {

        //Given
        List<DepartmentSummaryResponse> mockDepartmentsSummaryResponse = List.of(
                DepartmentSummaryResponse.builder()
                        .id(11L)
                        .name("TestName1")
                        .build(),
                DepartmentSummaryResponse.builder()
                        .id(12L)
                        .name("TestName2")
                        .build(),
                DepartmentSummaryResponse.builder()
                        .id(13L)
                        .name("TestName3")
                        .build()
        );

        //When
        Mockito.when(departmentReadService.findSummaryAll())
                .thenReturn(mockDepartmentsSummaryResponse);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments/summary")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(departmentReadService, Mockito.times(1))
                .findSummaryAll();
    }

    @Test
    public void whenNotFoundDepartmentsSummaryAll_thenReturnEmptyList() throws Exception {

        //When
        List<DepartmentSummaryResponse> emptyList = Collections.emptyList();

        Mockito.when(departmentReadService.findSummaryAll())
                .thenReturn(emptyList);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments/summary")
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isEmpty());

        //Verify
        Mockito.verify(departmentReadService, Mockito.times(1))
                .findSummaryAll();
    }

    @Test
    void givenValidDepartmentCreateRequest_whenDepartmentCreated_thenReturnSuccess() throws Exception {

        //Given
        DepartmentCreateRequest mockCreateRequest = DepartmentCreateRequest.builder()
                .name("TestName")
                .build();

        //When
        Mockito.doNothing()
                .when(departmentCreateService)
                .create(Mockito.any(DepartmentCreateRequest.class));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockCreateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .doesNotExist());

        //Verify
        Mockito.verify(departmentCreateService, Mockito.times(1))
                .create(Mockito.any(DepartmentCreateRequest.class));

    }



    private static List<Department> getDepartments() {
        return List.of(
                Department.builder()
                        .id(11L)
                        .name("TEST1")
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdUser("testAdmin")
                        .build(),
                Department.builder()
                        .id(12L)
                        .name("TEST2")
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdUser("testAdmin")
                        .build(),
                Department.builder()
                        .id(13L)
                        .name("TEST3")
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdUser("testAdmin")
                        .build());
    }
}