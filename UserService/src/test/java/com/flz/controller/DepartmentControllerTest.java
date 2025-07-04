package com.flz.controller;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.enums.DepartmentStatus;
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.length()").value(mockDepartments.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].status").isNotEmpty());

        //Verify
        Mockito.verify(departmentReadService, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());
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