package com.flz.controller;

import com.flz.BaseEndToEndTest;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.enums.PositionStatus;
import com.flz.model.request.EmployeeExperienceCreateRequest;
import com.flz.port.DepartmentTestPort;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.port.EmployeeSavePort;
import com.flz.port.PositionTestPort;
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

class EmployeeExperienceEndToEndTest extends BaseEndToEndTest {

    @Autowired
    private EmployeeSavePort employeeSavePort;

    @Autowired
    private PositionTestPort positionTestPort;

    @Autowired
    private DepartmentTestPort departmentTestPort;

    @Autowired
    private EmployeeExperienceReadPort employeeExperienceReadPort;

    @Autowired
    private EmployeeExperienceSavePort employeeExperienceSavePort;

    private static final String BASE_PATH = "/api/v1";


    @Test
    void givenCreateRequest_whenCreateEmployeeExperience_thenReturnSuccess() throws Exception {

        //Initialize
        Employee employee = Employee.builder()
                .firstName("Hakan")
                .lastName("Sonar")
                .identityNumber("32188877111")
                .email("hakansnr@gmail.com")
                .phoneNumber("05458781236")
                .address("Adana")
                .birthDate(LocalDate.of(1997, 2, 20))
                .gender(Gender.MALE)
                .nationality("TR")
                .build();
        Employee savedEmployee = employeeSavePort.save(employee);

        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Salih")
                .lastName("Solmaz")
                .identityNumber("81269932119")
                .phoneNumber("05322589974")
                .address("Mardin")
                .birthDate(LocalDate.parse("1989-07-01"))
                .gender(Gender.MALE)
                .nationality("Turkey")
                .build());

        Department department = departmentTestPort.save(
                Department.builder()
                        .name("IT")
                        .manager(manager)
                        .status(DepartmentStatus.ACTIVE)
                        .build());

        Position position = positionTestPort.save(Position.builder()
                .name("DevOps Engineeer")
                .department(department)
                .status(PositionStatus.ACTIVE)
                .build());

        Position position2 = positionTestPort.save(Position.builder()
                .name("DevSecOps Engineer")
                .department(department)
                .status(PositionStatus.ACTIVE)
                .build());

        EmployeeExperience employeeExperience = EmployeeExperience.builder()
                .salary(BigDecimal.valueOf(10000))
                .startDate(LocalDate.parse("2024-10-01"))
                .position(position)
                .employee(savedEmployee)
                .build();
        employeeExperienceSavePort.save(employeeExperience);

        //Given
        EmployeeExperienceCreateRequest createRequest = EmployeeExperienceCreateRequest.builder()
                .salary(BigDecimal.valueOf(50000))
                .positionId(position2.getId())
                .startDate(LocalDate.now().plusDays(5))
                .build();

        Long employeeId = savedEmployee.getId();

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BASE_PATH + "/employee/{id}/experience", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        List<EmployeeExperience> employeeExperiences = employeeExperienceReadPort.findAllByEmployeeId(employeeId);

        Assertions.assertFalse(employeeExperiences.isEmpty());

        Assertions.assertNotNull(employeeExperiences.get(0));
        Assertions.assertNotNull(employeeExperiences.get(1));

        Assertions.assertNotNull(employeeExperiences.get(0).getSalary());
        Assertions.assertEquals(createRequest.getStartDate(),
                employeeExperiences.get(0).getStartDate());
        Assertions.assertEquals(createRequest.getPositionId(),
                employeeExperiences.get(0).getPosition().getId());

        Assertions.assertNull(employeeExperiences.get(0).getEndDate());

        Assertions.assertEquals(savedEmployee.getIdentityNumber(),
                employeeExperiences.get(0).getEmployee().getIdentityNumber());
        Assertions.assertEquals(savedEmployee.getFirstName(),
                employeeExperiences.get(0).getEmployee().getFirstName());
        Assertions.assertEquals(savedEmployee.getLastName(),
                employeeExperiences.get(0).getEmployee().getLastName());

        Assertions.assertNotNull(employeeExperiences.get(0).getCreatedAt());
        Assertions.assertNotNull(employeeExperiences.get(0).getCreatedBy());

        Assertions.assertNotNull(employeeExperiences.get(1).getSalary());
        Assertions.assertEquals(employeeExperience.getStartDate(),
                employeeExperiences.get(1).getStartDate());
        Assertions.assertNotNull(employeeExperiences.get(1).getEndDate());

        Assertions.assertEquals(employeeExperience.getPosition().getName(),
                employeeExperiences.get(1).getPosition().getName());
        Assertions.assertEquals(employeeExperience.getPosition().getStatus(),
                employeeExperiences.get(1).getPosition().getStatus());
        Assertions.assertEquals(employeeExperience.getPosition().getDepartment().getName(),
                employeeExperiences.get(1).getPosition().getDepartment().getName());
        Assertions.assertEquals(employeeExperience.getPosition().getDepartment().getManager().getIdentityNumber(),
                employeeExperiences.get(1).getPosition().getDepartment().getManager().getIdentityNumber());
        Assertions.assertEquals(employeeExperience.getEmployee().getIdentityNumber(),
                employeeExperiences.get(1).getEmployee().getIdentityNumber());
        Assertions.assertEquals(employeeExperience.getEmployee().getFirstName(),
                employeeExperiences.get(1).getEmployee().getFirstName());
        Assertions.assertEquals(employeeExperience.getEmployee().getLastName(),
                employeeExperiences.get(1).getEmployee().getLastName());

        Assertions.assertNotNull(employeeExperiences.get(1).getCreatedAt());
        Assertions.assertNotNull(employeeExperiences.get(1).getCreatedBy());

    }


}
