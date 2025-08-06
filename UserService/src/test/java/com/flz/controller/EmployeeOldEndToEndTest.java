package com.flz.controller;

import com.flz.BaseEndToEndTest;
import com.flz.model.EmployeeOld;
import com.flz.model.enums.Gender;
import com.flz.port.EmployeeOldReadPort;
import com.flz.port.EmployeeOldSavePort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

class EmployeeOldEndToEndTest extends BaseEndToEndTest {

    private static final String BASE_PATH = "/api/v1";
    @Autowired
    private EmployeeOldReadPort employeeOldReadPort;
    @Autowired
    private EmployeeOldSavePort employeeOldSavePort;

    @Test
    void whenFindByIdEmployeeOld_thenReturnEmployeeOldVerifyContent() throws Exception {

        //Initialize
        EmployeeOld employee = EmployeeOld.builder()
                .firstName("Atilla")
                .lastName("Ilhan")
                .identityNumber("5228529674")
                .email("atillaIlhan@test.com")
                .phoneNumber("05324111415")
                .address("Aksaray")
                .birthDate(LocalDate.parse("1956-10-01"))
                .gender(Gender.MALE)
                .nationality("TR")
                .build();

        EmployeeOld savedEmployee = employeeOldSavePort.save(employee);

        //Given
        Long id = savedEmployee.getId();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/employee-old/" + id)
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.firstName")
                        .value("Atilla"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.lastName")
                        .value("Ilhan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.identityNumber")
                        .value("5228529674"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.email")
                        .value("atillaIlhan@test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.phoneNumber")
                        .value("05324111415"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.address")
                        .value("Aksaray"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.birthDate")
                        .value("1956-10-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.gender")
                        .value("MALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.nationality")
                        .value("TR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences.length()")
                        .value(0));

    }

}
