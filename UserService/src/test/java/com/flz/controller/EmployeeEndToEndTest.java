package com.flz.controller;

import com.flz.BaseEndToEndTest;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.EmployeeOld;
import com.flz.model.EmployeeOldExperience;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.EmployeeExperienceToResponseMapper;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.EmployeeUpdateRequest;
import com.flz.model.response.EmployeeDetailsResponse;
import com.flz.port.DepartmentTestPort;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.port.EmployeeOldExperienceReadPort;
import com.flz.port.EmployeeOldTestPort;
import com.flz.port.EmployeeReadPort;
import com.flz.port.EmployeeSavePort;
import com.flz.port.EmployeeTestPort;
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
import java.util.Optional;

class EmployeeEndToEndTest extends BaseEndToEndTest {

    @Autowired
    private EmployeeReadPort employeeReadPort;

    @Autowired
    private EmployeeSavePort employeeSavePort;

    @Autowired
    private EmployeeTestPort employeeTestPort;

    @Autowired
    private EmployeeOldTestPort employeeOldTestPort;

    @Autowired
    private PositionTestPort positionTestPort;

    @Autowired
    private DepartmentTestPort departmentTestPort;

    @Autowired
    private EmployeeExperienceSavePort employeeExperienceSavePort;

    @Autowired
    private EmployeeExperienceReadPort employeeExperienceReadPort;

    @Autowired
    private EmployeeOldExperienceReadPort employeeOldExperienceReadPort;

    private final EmployeeExperienceToResponseMapper
            employeeExperienceToResponseMapper = EmployeeExperienceToResponseMapper.INSTANCE;

    private static final String BASE_PATH = "/api/v1";


    @Test
    void whenFindByIdEmployeeWithExperiences_thenReturnEmployeeVerifyContent() throws Exception {

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

        Employee manager = employeeSavePort.save(Employee.builder()
                .id(5L)
                .identityNumber("98765432109")
                .firstName("Eric")
                .lastName("Smith")
                .gender(Gender.MALE)
                .nationality("USA")
                .address("New York")
                .birthDate(LocalDate.parse("1997-10-01"))
                .phoneNumber("05328529674")
                .build());

        Department department = departmentTestPort.save(Department.builder()
                .status(DepartmentStatus.ACTIVE)
                .name("Department 1")
                .manager(manager)
                .build());

        Position position = positionTestPort.save(Position.builder()
                .id(10L)
                .name("Developer")
                .status(PositionStatus.ACTIVE)
                .department(department)
                .build());

        Position position2 = positionTestPort.save(Position.builder()
                .id(11L)
                .name("Team Leader")
                .status(PositionStatus.ACTIVE)
                .department(department)
                .build());

        EmployeeExperience employeeExperience = EmployeeExperience.builder()
                .salary(BigDecimal.valueOf(10000))
                .startDate(LocalDate.parse("2000-10-01"))
                .endDate(LocalDate.parse("2001-10-31"))
                .position(position)
                .employee(savedEmployee)
                .build();
        employeeExperienceSavePort.save(employeeExperience);

        EmployeeExperience employeeExperience2 = EmployeeExperience.builder()
                .salary(BigDecimal.valueOf(20000))
                .startDate(LocalDate.parse("2002-10-01"))
                .position(position2)
                .employee(savedEmployee)
                .build();
        employeeExperienceSavePort.save(employeeExperience2);

        //Given
        Long employeeId = savedEmployee.getId();

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
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.lastName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.identityNumber")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.phoneNumber")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.address")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.birthDate")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.gender")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.gender")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.employee.nationality")
                        .value("TR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences.length()")
                        .value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].salary")
                        .value(10000.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].startDate")
                        .value("2000-10-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].endDate")
                        .value("2001-10-31"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].positionName")
                        .value("Developer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].departmentName")
                        .value("Department 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[0].managerName")
                        .value("Eric Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].salary")
                        .value(20000.00))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].startDate")
                        .value("2002-10-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].endDate")
                        .doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].positionName")
                        .value("Team Leader"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].departmentName")
                        .value("Department 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.experiences[1].managerName")
                        .value("Eric Smith"));

        //Verify
        Employee employeeFromDatabase = employeeReadPort.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        EmployeeDetailsResponse employeeDetailsResponse = EmployeeDetailsResponse.builder()
                .employee(employeeFromDatabase)
                .experiences(employeeExperienceToResponseMapper.map(employeeExperienceReadPort.findAllByEmployeeId(employeeId)))
                .build();

        Assertions.assertNotNull(employeeDetailsResponse);
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences());
        Assertions.assertEquals(savedEmployee.getFirstName(), employeeDetailsResponse.getEmployee().getFirstName());
        Assertions.assertEquals(savedEmployee.getLastName(), employeeDetailsResponse.getEmployee().getLastName());
        Assertions.assertEquals(savedEmployee.getIdentityNumber(), employeeDetailsResponse.getEmployee().getIdentityNumber());
        Assertions.assertEquals(savedEmployee.getPhoneNumber(), employeeDetailsResponse.getEmployee().getPhoneNumber());
        Assertions.assertEquals(savedEmployee.getAddress(), employeeDetailsResponse.getEmployee().getAddress());
        Assertions.assertEquals(savedEmployee.getBirthDate(), employeeDetailsResponse.getEmployee().getBirthDate());
        Assertions.assertEquals(savedEmployee.getGender(), employeeDetailsResponse.getEmployee().getGender());
        Assertions.assertEquals(savedEmployee.getNationality(), employeeDetailsResponse.getEmployee().getNationality());

        Assertions.assertEquals(employeeExperience.getPosition().getName(),
                employeeDetailsResponse.getExperiences().get(0).getPositionName());
        Assertions.assertEquals(employeeExperience.getPosition().getDepartment().getName(),
                employeeDetailsResponse.getExperiences().get(0).getDepartmentName());
        Assertions.assertEquals(employeeExperience.getStartDate(),
                employeeDetailsResponse.getExperiences().get(0).getStartDate());
        Assertions.assertEquals(employeeExperience.getEndDate(),
                employeeDetailsResponse.getExperiences().get(0).getEndDate());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(0).getManagerName());

        Assertions.assertEquals(employeeExperience2.getPosition().getName(),
                employeeDetailsResponse.getExperiences().get(1).getPositionName());
        Assertions.assertEquals(employeeExperience2.getPosition().getDepartment().getName(),
                employeeDetailsResponse.getExperiences().get(0).getDepartmentName());
        Assertions.assertEquals(employeeExperience2.getStartDate(),
                employeeDetailsResponse.getExperiences().get(1).getStartDate());
        Assertions.assertEquals(employeeExperience2.getEndDate(),
                employeeDetailsResponse.getExperiences().get(1).getEndDate());
        Assertions.assertNotNull(employeeDetailsResponse.getExperiences().get(1).getManagerName());

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].firstName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].lastName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].identityNumber")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].phoneNumber")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].address")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].birthDate")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].gender")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].nationality")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdAt")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdBy")
                        .exists());

        //Then
        List<Employee> employeeList = employeeReadPort.findAll(1, 10);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].firstName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].lastName")
                        .isString());

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

        //Initialize
        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Emma")
                .lastName("Sweet")
                .identityNumber("98712132109")
                .phoneNumber("05558525574")
                .address("New York")
                .birthDate(LocalDate.parse("1995-10-01"))
                .gender(Gender.FEMALE)
                .nationality("UK")
                .build());

        Department department = departmentTestPort.save(Department.builder()
                .status(DepartmentStatus.ACTIVE)
                .name("DepartmentIT")
                .manager(manager)
                .build());

        Position position = positionTestPort.save(Position.builder()
                .name("Go Developer")
                .status(PositionStatus.ACTIVE)
                .department(department)
                .build());

        //Given
        EmployeeCreateRequest createRequest = EmployeeCreateRequest.builder()
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
                .positionId(position.getId())
                .departmentId(department.getId())
                .startDate(LocalDate.parse("2025-10-01"))
                .build();

        //When
        String endpoint = BASE_PATH + "/employee";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest));

        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Employee actualEmployee = employeeTestPort.findByIdentityNumber("25996700777");
        Position actualPosition = positionTestPort.findByName("Go Developer");
        List<EmployeeExperience> employeeExperiences = employeeExperienceReadPort.findAllByEmployeeId(actualEmployee.getId());

        Assertions.assertNotNull(actualEmployee);
        Assertions.assertNotNull(actualEmployee.getFirstName());
        Assertions.assertNotNull(actualEmployee.getLastName());
        Assertions.assertNotNull(actualEmployee.getId());
        Assertions.assertNotNull(actualEmployee.getPhoneNumber());
        Assertions.assertNotNull(actualEmployee.getEmail());
        Assertions.assertNotNull(actualEmployee.getAddress());
        Assertions.assertNotNull(actualEmployee.getBirthDate());
        Assertions.assertEquals(createRequest.getFirstName(), actualEmployee.getFirstName());
        Assertions.assertEquals(createRequest.getLastName(), actualEmployee.getLastName());
        Assertions.assertEquals(createRequest.getIdentityNumber(), actualEmployee.getIdentityNumber());
        Assertions.assertEquals(createRequest.getEmail(), actualEmployee.getEmail());
        Assertions.assertEquals(createRequest.getPhoneNumber(), actualEmployee.getPhoneNumber());
        Assertions.assertEquals(createRequest.getAddress(), actualEmployee.getAddress());
        Assertions.assertEquals(createRequest.getBirthDate(), actualEmployee.getBirthDate());
        Assertions.assertEquals(createRequest.getGender(), actualEmployee.getGender());
        Assertions.assertEquals(createRequest.getNationality(), actualEmployee.getNationality());
        Assertions.assertEquals(employeeExperiences.get(0).getEmployee(), actualEmployee);
        Assertions.assertEquals(createRequest.getPositionId(), actualPosition.getId());
        Assertions.assertEquals(position.getName(), actualPosition.getName());
        Assertions.assertEquals(position.getDepartment().getName(),
                actualPosition.getDepartment().getName());
        Assertions.assertEquals(position.getDepartment().getManager().getPhoneNumber(),
                actualPosition.getDepartment().getManager().getPhoneNumber());
        Assertions.assertEquals(position.getStatus(), actualPosition.getStatus());
        Assertions.assertEquals(position.getDepartment().getStatus(),
                actualPosition.getDepartment().getStatus());

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

        EmployeeUpdateRequest updateRequest = EmployeeUpdateRequest.builder()
                .firstName("Mahmut")
                .lastName("Korur")
                .identityNumber("12345543219")
                .email("mahmut@gmail.com")
                .phoneNumber("05327776543")
                .address("Adana")
                .birthDate(LocalDate.parse("2000-10-01"))
                .gender(Gender.MALE)
                .nationality("Almanya")
                .build();

        //Given
        Long employeeId = savedEmployee.getId();

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
        Assertions.assertEquals(updateRequest.getFirstName(), updatedEmployee.get().getFirstName());
        Assertions.assertEquals(updateRequest.getLastName(), updatedEmployee.get().getLastName());
        Assertions.assertEquals(updateRequest.getIdentityNumber(), updatedEmployee.get().getIdentityNumber());
        Assertions.assertEquals(updateRequest.getEmail(), updatedEmployee.get().getEmail());
        Assertions.assertEquals(updateRequest.getPhoneNumber(), updatedEmployee.get().getPhoneNumber());
        Assertions.assertEquals(updateRequest.getAddress(), updatedEmployee.get().getAddress());
        Assertions.assertEquals(updateRequest.getBirthDate(), updatedEmployee.get().getBirthDate());
        Assertions.assertEquals(updateRequest.getGender(), updatedEmployee.get().getGender());
        Assertions.assertEquals(updateRequest.getNationality(), updatedEmployee.get().getNationality());

        Assertions.assertNotEquals(savedEmployee.getFirstName(), updatedEmployee.get().getFirstName());
        Assertions.assertNotEquals(savedEmployee.getLastName(), updatedEmployee.get().getLastName());
        Assertions.assertNotEquals(savedEmployee.getIdentityNumber(), updatedEmployee.get().getIdentityNumber());
        Assertions.assertNotEquals(savedEmployee.getEmail(), updatedEmployee.get().getEmail());
        Assertions.assertNotEquals(savedEmployee.getPhoneNumber(), updatedEmployee.get().getPhoneNumber());
        Assertions.assertNotEquals(savedEmployee.getAddress(), updatedEmployee.get().getAddress());
        Assertions.assertEquals(savedEmployee.getBirthDate(), updatedEmployee.get().getBirthDate());
        Assertions.assertEquals(savedEmployee.getGender(), updatedEmployee.get().getGender());
        Assertions.assertNotEquals(savedEmployee.getNationality(), updatedEmployee.get().getNationality());

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
                .birthDate(LocalDate.of(2000, 2, 20))
                .gender(Gender.MALE)
                .nationality("TR")
                .build();
        Employee savedEmployee = employeeSavePort.save(employee);

        Employee manager = employeeSavePort.save(Employee.builder()
                .identityNumber("91265532109")
                .firstName("Emma")
                .lastName("Sawyer")
                .gender(Gender.FEMALE)
                .nationality("USA")
                .address("New York")
                .birthDate(LocalDate.parse("2000-07-01"))
                .phoneNumber("05328521174")
                .build());

        Department department = departmentTestPort.save(
                Department.builder()
                        .name("Siber Security")
                        .manager(manager)
                        .status(DepartmentStatus.ACTIVE)
                        .build());

        Position position = positionTestPort.save(Position.builder()
                .name("Graphic Designer")
                .department(department)
                .status(PositionStatus.ACTIVE)
                .build());

        Position position2 = positionTestPort.save(Position.builder()
                .name("FrontEnd Team Leader")
                .department(department)
                .status(PositionStatus.ACTIVE)
                .build());

        EmployeeExperience employeeExperience = EmployeeExperience.builder()
                .salary(BigDecimal.valueOf(10000))
                .startDate(LocalDate.parse("2005-10-01"))
                .endDate(LocalDate.parse("2007-10-20"))
                .position(position)
                .employee(savedEmployee)
                .build();
        employeeExperienceSavePort.save(employeeExperience);

        EmployeeExperience employeeExperience2 = EmployeeExperience.builder()
                .salary(BigDecimal.valueOf(20000))
                .startDate(LocalDate.parse("2007-10-30"))
                .position(position2)
                .employee(savedEmployee)
                .build();
        employeeExperienceSavePort.save(employeeExperience2);

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
        EmployeeOld employeeOldSaved = employeeOldTestPort.findByIdentityNumber("99988877111");
        List<EmployeeOldExperience> employeeOldExperiences = employeeOldExperienceReadPort.findAllByEmployeeOldId(employeeOldSaved.getId());
        Optional<Employee> deletedEmployee = employeeReadPort.findById(employeeId);
        List<EmployeeExperience> employeeExperiences = employeeExperienceReadPort.findAllByEmployeeId(employeeId);

        Assertions.assertTrue(deletedEmployee.isEmpty());
        Assertions.assertTrue(employeeExperiences.isEmpty());

        Assertions.assertNotNull(employeeOldSaved);
        Assertions.assertEquals(savedEmployee.getFirstName(), employeeOldSaved.getFirstName());
        Assertions.assertEquals(savedEmployee.getLastName(), employeeOldSaved.getLastName());
        Assertions.assertEquals(savedEmployee.getIdentityNumber(), employeeOldSaved.getIdentityNumber());
        Assertions.assertEquals(savedEmployee.getPhoneNumber(), employeeOldSaved.getPhoneNumber());
        Assertions.assertEquals(savedEmployee.getAddress(), employeeOldSaved.getAddress());
        Assertions.assertEquals(savedEmployee.getBirthDate(), employeeOldSaved.getBirthDate());

        Assertions.assertEquals(employeeExperience.getPosition().getName(), employeeOldExperiences.get(0)
                .getPosition().getName());
        Assertions.assertEquals(employeeExperience.getPosition().getDepartment().getName(),
                employeeOldExperiences.get(0).getPosition().getDepartment().getName());
        Assertions.assertEquals(employeeExperience.getPosition().getStatus(),
                employeeOldExperiences.get(0).getPosition().getStatus());
        Assertions.assertEquals(employeeExperience.getPosition().getDepartment().getManager().getIdentityNumber(),
                employeeOldExperiences.get(0).getPosition().getDepartment().getManager().getIdentityNumber());
        Assertions.assertEquals(employeeExperience.getPosition().getDepartment().getManager().getPhoneNumber(),
                employeeOldExperiences.get(0).getPosition().getDepartment().getManager().getPhoneNumber());

        Assertions.assertEquals(employeeExperience2.getPosition().getName(),
                employeeOldExperiences.get(1).getPosition().getName());
        Assertions.assertEquals(employeeExperience2.getPosition().getDepartment().getName(),
                employeeOldExperiences.get(1).getPosition().getDepartment().getName());
        Assertions.assertEquals(employeeExperience2.getPosition().getStatus(),
                employeeOldExperiences.get(1).getPosition().getStatus());
        Assertions.assertEquals(employeeExperience2.getPosition().getDepartment().getManager().getIdentityNumber(),
                employeeOldExperiences.get(1).getPosition().getDepartment().getManager().getIdentityNumber());
        Assertions.assertEquals(employeeExperience2.getPosition().getDepartment().getManager().getPhoneNumber(),
                employeeOldExperiences.get(1).getPosition().getDepartment().getManager().getPhoneNumber());

    }

}
