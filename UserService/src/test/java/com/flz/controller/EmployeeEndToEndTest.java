package com.flz.controller;

import com.flz.BaseEndToEndTest;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.enums.Gender;
import com.flz.model.mapper.EmployeeExperienceToResponseMapper;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.EmployeeUpdateRequest;
import com.flz.model.response.EmployeeDetailsResponse;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeExperienceSavePort;
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

    private final EmployeeExperienceToResponseMapper employeeExperienceToResponseMapper = EmployeeExperienceToResponseMapper.INSTANCE;
    @Autowired
    private EmployeeExperienceSavePort employeeExperienceSavePort;
    @Autowired
    private EmployeeExperienceReadPort employeeExperienceReadPort;

    private static final String BASE_PATH = "/api/v1";

    @Test
    void whenFindByIdEmployeewWithExperiences_thenReturnEmployeeVerifyContent() throws Exception {

        //Initialize
        Employee employee = Employee.builder()
                .firstName("Cemal")
                .lastName("Sureyya")
                .identityNumber("55591119999")
                .email("cemil@test.com")
                .phoneNumber("055531234567")
                .address("Ankara")
                .birthDate(LocalDate.parse("2000-10-01"))
                .gender(Gender.MALE)
                .nationality("TR")
                .build();
        Employee savedEmployee = employeeSavePort.save(employee);

        //Given
        Long employeeId = savedEmployee.getId();

        Position position = Position.builder()
                .id(10L)
                .name("Developer")
                .build();

        Position position2 = Position.builder()
                .id(11L)
                .name("Team Leader")
                .build();

        Employee supervisor = Employee.builder()
                .id(5L)
                .identityNumber("98765432109")
                .firstName("Eric")
                .lastName("Smith")
                .gender(Gender.MALE)
                .nationality("USA")
                .address("New York")
                .phoneNumber("05328529674")
                .build();


        EmployeeExperience employeeExperience = EmployeeExperience.builder()
                .salary(BigDecimal.valueOf(10000))
                .startDate(LocalDate.parse("2000-10-01"))
                .endDate(LocalDate.parse("2001-10-31"))
                .position(position)
                .employee(savedEmployee)
                .supervisor(supervisor)
                .build();
        employeeExperienceSavePort.save(employeeExperience);

        EmployeeExperience employeeExperience2 = EmployeeExperience.builder()
                .salary(BigDecimal.valueOf(20000))
                .startDate(LocalDate.parse("2002-10-01"))
                .position(position2)
                .employee(savedEmployee)
                .supervisor(supervisor)
                .build();
        employeeExperienceSavePort.save(employeeExperience2);

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/employee/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON);

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.id")
                        .value(employeeId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.firstName")
                        .value("Cemal"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.lastName")
                        .value("Sureyya"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.identityNumber")
                        .value("55591119999"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.email")
                        .value("cemil@test.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.phoneNumber")
                        .value("055531234567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.address")
                        .value("Ankara"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.birthDate")
                        .value("2000-10-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.gender")
                        .value("MALE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.nationality")
                        .value("TR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences.length()")
                        .value(2))

                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].id")
                        .value(16))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].salary")
                        .value(10000.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].startDate")
                        .value("2000-10-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].endDate")
                        .value("2001-10-31"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].positionId")
                        .value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].positionName")
                        .value("Konsiyerj"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].supervisorId")
                        .value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].supervisorName")
                        .value("Mustafa Şahin"))

                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].id")
                        .value(17))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].salary")
                        .value(20000.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].startDate")
                        .value("2002-10-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].endDate")
                        .doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].positionId")
                        .value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].positionName")
                        .value("Kat Görevlisi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].supervisorId")
                        .value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].supervisorName")
                        .value("Mustafa Şahin"));

        //Verify
        Employee employeeFromDatabase = employeeReadPort.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        EmployeeDetailsResponse employeeDetailsResponse = EmployeeDetailsResponse.builder()
                .employee(employeeFromDatabase)
                .experiences(employeeExperienceToResponseMapper.map(employeeExperienceReadPort.findAllByEmployeeId(employeeId)))
                .build();

        Assertions.assertNotNull(employeeDetailsResponse);
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences());
        Assertions.assertNotNull(employeeDetailsResponse.getEmployee().getFirstName());
        Assertions.assertNotNull(employeeDetailsResponse.getEmployee().getLastName());
        Assertions.assertNotNull(employeeDetailsResponse.getEmployee().getIdentityNumber());
        Assertions.assertNotNull(employeeDetailsResponse.getEmployee().getPhoneNumber());
        Assertions.assertNotNull(employeeDetailsResponse.getEmployee().getBirthDate());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(0).getId());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(0).getStartDate());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(0).getEndDate());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(0).getPositionId());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(0).getSupervisorName());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(1).getStartDate());
        Assertions.assertNull(employeeDetailsResponse.getExperiences().get(1).getEndDate());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(1).getPositionName());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(1).getSupervisorName());
        Assertions.assertEquals(savedEmployee.getIdentityNumber(), employeeFromDatabase.getIdentityNumber());
        Assertions.assertEquals(savedEmployee.getEmail(), employeeFromDatabase.getEmail());
        Assertions.assertEquals(savedEmployee.getPhoneNumber(), employeeFromDatabase.getPhoneNumber());
        Assertions.assertEquals(savedEmployee.getBirthDate(), employeeFromDatabase.getBirthDate());

    }

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

        //Initialize
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
        Employee savedEmployee = employeeSavePort.save(employee);

        //Given
        Long employeeId = savedEmployee.getId();

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
        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(BASE_PATH + "/employee/" + employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
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
        Employee savedEmployee = employeeSavePort.save(employee);

        //Given
        Long employeeId = savedEmployee.getId();

        //When
        MockHttpServletRequestBuilder deleteRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/employee/" + employeeId);

        //Then
        mockMvc.perform(deleteRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Optional<Employee> deletedEmployee = employeeReadPort.findById(employeeId);

        Assertions.assertFalse(deletedEmployee.isPresent());

    }
}
