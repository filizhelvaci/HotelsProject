package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.model.response.DepartmentSummaryResponse;
import com.flz.service.DepartmentReadService;
import com.flz.service.DepartmentWriteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest extends BaseTest {

    @MockBean
    DepartmentWriteService departmentWriteService;

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
    public void whenCalledAllSummaryDepartment_thenReturnDepartmentSummaryResponse() throws Exception {

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

    /**
     * {@link DepartmentController#create(DepartmentCreateRequest)}
     */
    @Test
    void givenValidDepartmentCreateRequest_whenDepartmentCreated_thenReturnSuccess() throws Exception {

        //Given
        DepartmentCreateRequest mockCreateRequest = DepartmentCreateRequest.builder()
                .name("TestName")
                .build();

        //When
        Mockito.doNothing()
                .when(departmentWriteService)
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
        Mockito.verify(departmentWriteService, Mockito.times(1))
                .create(Mockito.any(DepartmentCreateRequest.class));

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "a",
            "One morning, when Gregor Samsa woke from troubled dre"
    })
    void givenInvalidCreateRequest_whenCreateDepartment_thenReturnBadRequest(String invalidName) throws Exception {

        //Given
        DepartmentCreateRequest invalidRequest = DepartmentCreateRequest.builder()
                .name(invalidName)
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        //Verify
        Mockito.verifyNoInteractions(departmentWriteService);
    }

    /**
     * {@link DepartmentController#update(Long, DepartmentUpdateRequest)}
     */
    @Test
    public void givenValidIdAndDepartmentUpdateRequest_whenFindDepartmentById_thenUpdateDepartmentSuccessfully() throws Exception {

        //Given
        Long mockId = 10L;

        DepartmentUpdateRequest mockDepartmentUpdateRequest = DepartmentUpdateRequest.builder()
                .name("TestName")
                .build();

        //When
        Mockito.doNothing().when(departmentWriteService).update(mockId, mockDepartmentUpdateRequest);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/department/{id}", mockId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockDepartmentUpdateRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(departmentWriteService, Mockito.times(1))
                .update(Mockito.any(), Mockito.any(DepartmentUpdateRequest.class));
    }

    @ParameterizedTest
    @ValueSource(longs = {
            0L,
            -1L,
            -100L
    })
    void givenInvalidId_whenUpdateDepartment_thenReturnBadRequest(Long invalidId) throws Exception {

        //Given
        DepartmentUpdateRequest mockRequest = DepartmentUpdateRequest.builder()
                .name("ValidTestName")
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BASE_PATH + "/department/{id}", invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));


        //Verify
        Mockito.verify(departmentWriteService, Mockito.never())
                .update(Mockito.any(), Mockito.any(DepartmentUpdateRequest.class));
    }

    @Test
    void givenNullId_whenUpdateDepartment_thenReturnInternalServerError() throws Exception {

        //Given
        DepartmentUpdateRequest mockRequest = DepartmentUpdateRequest.builder()
                .name("ValidTestName")
                .build();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BASE_PATH + "/department/null")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));
    }

    /**
     * {@link DepartmentController#delete(Long)}
     */
    @Test
    void givenValidId_whenCalledDeleteDepartment_thenDoDepartmentStatusDeleted() throws Exception {

        //Given
        Long mockId = 10L;

        //When
        Mockito.doNothing().when(departmentWriteService).delete(mockId);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/department/{id}", mockId);

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Mockito.verify(departmentWriteService, Mockito.times(1))
                .delete(Mockito.anyLong());
    }

    @Test
    void givenInValidId_whenCalledDeleteForDepartment_thenReturnBadRequest() throws Exception {

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/department/{id}", "lkjhg");

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(false));

        //Verify
        Mockito.verify(departmentWriteService, Mockito.never()).delete(Mockito.anyLong());
    }

    private static List<Department> getDepartments() {
        return List.of(
                Department.builder()
                        .id(11L)
                        .name("TEST1")
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("testAdmin")
                        .build(),
                Department.builder()
                        .id(12L)
                        .name("TEST2")
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("testAdmin")
                        .build(),
                Department.builder()
                        .id(13L)
                        .name("TEST3")
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("testAdmin")
                        .build());
    }
}