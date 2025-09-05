package com.flz.controller;

import com.flz.BaseEndToEndTest;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.EmployeeOld;
import com.flz.model.EmployeeOldExperience;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.EmployeeOldExperienceToResponseMapper;
import com.flz.model.response.EmployeeOldDetailsResponse;
import com.flz.port.DepartmentTestPort;
import com.flz.port.EmployeeOldExperienceReadPort;
import com.flz.port.EmployeeOldExperienceSavePort;
import com.flz.port.EmployeeOldReadPort;
import com.flz.port.EmployeeOldSavePort;
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

class EmployeeOldEndToEndTest extends BaseEndToEndTest {

    @Autowired
    private EmployeeOldReadPort employeeOldReadPort;

    @Autowired
    private EmployeeOldSavePort employeeOldSavePort;

    @Autowired
    private DepartmentTestPort departmentTestPort;

    @Autowired
    private PositionTestPort positionTestPort;

    @Autowired
    private EmployeeSavePort employeeSavePort;

    @Autowired
    private EmployeeOldExperienceSavePort employeeOldExperienceSavePort;

    @Autowired
    private EmployeeOldExperienceReadPort employeeOldExperienceReadPort;

    private static final String BASE_PATH = "/api/v1";
    private final EmployeeOldExperienceToResponseMapper
            employeeOldExperienceToResponseMapper = EmployeeOldExperienceToResponseMapper.INSTANCE;


    @Test
    void whenFindByIdEmployeeOld_thenReturnEmployeeOldVerifyContent() throws Exception {

        //Initialize
        EmployeeOld savedEmployee = employeeOldSavePort.save(EmployeeOld.builder()
                .firstName("Atilla")
                .lastName("Ilhan")
                .identityNumber("5228529674")
                .email("atillaIlhan@test.com")
                .phoneNumber("05324111415")
                .address("Aksaray")
                .birthDate(LocalDate.parse("1978-10-01"))
                .gender(Gender.MALE)
                .nationality("TR")
                .build());

        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Emma")
                .lastName("Corner")
                .identityNumber("5465465485465")
                .phoneNumber("4285098529674")
                .address("Paris")
                .birthDate(LocalDate.parse("1976-10-01"))
                .gender(Gender.FEMALE)
                .nationality("France")
                .build());

        Department department = departmentTestPort.save(Department.builder()
                .name("Siber Suçlar")
                .manager(manager)
                .status(DepartmentStatus.ACTIVE)
                .build());

        Position position = positionTestPort.save(Position.builder()
                .status(PositionStatus.ACTIVE)
                .name("DevOps Engineer")
                .department(department)
                .build());

        Position position2 = positionTestPort.save(Position.builder()
                .status(PositionStatus.ACTIVE)
                .name("DevOps Team Leader")
                .department(department)
                .build());

        EmployeeOldExperience employeeOldExperience1 = EmployeeOldExperience.builder()
                .salary(BigDecimal.valueOf(10000))
                .startDate(LocalDate.parse("2000-10-01"))
                .endDate(LocalDate.parse("2001-10-31"))
                .position(position)
                .employeeOld(savedEmployee)
                .build();

        EmployeeOldExperience employeeOldExperience2 = EmployeeOldExperience.builder()
                .salary(BigDecimal.valueOf(20000))
                .startDate(LocalDate.parse("2002-10-01"))
                .endDate(LocalDate.parse("2003-10-25"))
                .position(position2)
                .employeeOld(savedEmployee)
                .build();

        List<EmployeeOldExperience> employeeOldExperiences = List.of(employeeOldExperience1, employeeOldExperience2);

        List<EmployeeOldExperience> savedEmployeeOldExperiences = employeeOldExperienceSavePort.saveAll(employeeOldExperiences);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.id")
                        .isNumber())
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
                        .value("1978-10-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.gender")
                        .value("MALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employeeOld.nationality")
                        .value("TR"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences.length()")
                        .value(2))

                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].id")
                        .isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].salary")
                        .value(10000.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].startDate")
                        .value("2000-10-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].endDate")
                        .value("2001-10-31"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].positionName")
                        .value("DevOps Engineer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].departmentName")
                        .value("Siber Suçlar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].managerName")
                        .value("Emma Corner"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].id")
                        .isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].salary")
                        .value(20000.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].startDate")
                        .value("2002-10-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].endDate")
                        .value("2003-10-25"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].positionName")
                        .value("DevOps Team Leader"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].departmentName")
                        .value("Siber Suçlar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].managerName")
                        .value("Emma Corner"));

        //Verify
        EmployeeOld employeeFromDatabase = employeeOldReadPort.findById(id)
                .orElseThrow(() -> new RuntimeException("EmployeeOld not found with id: " + id));

        EmployeeOldDetailsResponse employeeDetailsResponse = EmployeeOldDetailsResponse.builder()
                .employeeOld(employeeFromDatabase)
                .experiences(employeeOldExperienceToResponseMapper.map(employeeOldExperienceReadPort.findAllByEmployeeOldId(id)))
                .build();

        Assertions.assertNotNull(employeeDetailsResponse.getEmployeeOld());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences());

        Assertions.assertEquals(savedEmployee.getFirstName(),
                employeeDetailsResponse.getEmployeeOld().getFirstName());
        Assertions.assertEquals(savedEmployee.getLastName(),
                employeeDetailsResponse.getEmployeeOld().getLastName());
        Assertions.assertEquals(savedEmployee.getIdentityNumber(),
                employeeDetailsResponse.getEmployeeOld().getIdentityNumber());
        Assertions.assertEquals(savedEmployee.getPhoneNumber(),
                employeeDetailsResponse.getEmployeeOld().getPhoneNumber());
        Assertions.assertEquals(savedEmployee.getBirthDate(),
                employeeDetailsResponse.getEmployeeOld().getBirthDate());

        Assertions.assertEquals(savedEmployeeOldExperiences.get(0).getEmployeeOld().getId(),
                employeeDetailsResponse.getEmployeeOld().getId());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(0).getSalary());
        Assertions.assertEquals(savedEmployeeOldExperiences.get(0).getStartDate(),
                employeeDetailsResponse.getExperiences().get(0).getStartDate());
        Assertions.assertEquals(savedEmployeeOldExperiences.get(0).getEndDate(),
                employeeDetailsResponse.getExperiences().get(0).getEndDate());
        Assertions.assertEquals(savedEmployeeOldExperiences.get(0).getPosition().getName(),
                employeeDetailsResponse.getExperiences().get(0).getPositionName());
        Assertions.assertEquals(savedEmployeeOldExperiences.get(0).getPosition().getDepartment().getName(),
                employeeDetailsResponse.getExperiences().get(0).getDepartmentName());
        Assertions.assertEquals("Emma Corner",
                employeeDetailsResponse.getExperiences().get(0).getManagerName());

        Assertions.assertEquals(savedEmployeeOldExperiences.get(1).getEmployeeOld().getId(),
                employeeDetailsResponse.getEmployeeOld().getId());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(1).getSalary());
        Assertions.assertEquals(savedEmployeeOldExperiences.get(1).getStartDate(),
                employeeDetailsResponse.getExperiences().get(1).getStartDate());
        Assertions.assertEquals(savedEmployeeOldExperiences.get(1).getEndDate(),
                employeeDetailsResponse.getExperiences().get(1).getEndDate());
        Assertions.assertEquals(savedEmployeeOldExperiences.get(1).getPosition().getName(),
                employeeDetailsResponse.getExperiences().get(1).getPositionName());
        Assertions.assertEquals(savedEmployeeOldExperiences.get(1).getPosition().getDepartment().getName(),
                employeeDetailsResponse.getExperiences().get(1).getDepartmentName());
        Assertions.assertEquals("Emma Corner",
                employeeDetailsResponse.getExperiences().get(1).getManagerName());

    }


    @Test
    void whenFindAllEmployeeOlds_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/employees-old")
                .param("page", "1")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON);
        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true))

                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].firstName").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].lastName").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].identityNumber").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].phoneNumber").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].address").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].birthDate").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].gender").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].nationality").isNotEmpty())

                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].firstName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].lastName").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].identityNumber").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].phoneNumber").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].address").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].birthDate").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].gender").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].nationality").exists())

                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].createdAt").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[*].createdBy").isNotEmpty());

        //Then
        List<EmployeeOld> employeeList = employeeOldReadPort.findAll(1, 10);

        Assertions.assertNotNull(employeeList);
        Assertions.assertFalse(employeeList.isEmpty());
        Assertions.assertNotNull(employeeList.get(0).getId());
        Assertions.assertNotNull(employeeList.get(0).getFirstName());
        Assertions.assertNotNull(employeeList.get(0).getLastName());
        Assertions.assertNotNull(employeeList.get(0).getIdentityNumber());
        Assertions.assertNotNull(employeeList.get(0).getPhoneNumber());
        Assertions.assertNotNull(employeeList.get(0).getAddress());
        Assertions.assertNotNull(employeeList.get(0).getBirthDate());
        Assertions.assertNotNull(employeeList.get(0).getGender());
        Assertions.assertNotNull(employeeList.get(0).getNationality());
        Assertions.assertNotNull(employeeList.get(0).getCreatedAt());
        Assertions.assertNotNull(employeeList.get(0).getCreatedBy());

    }

}
