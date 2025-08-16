package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.enums.Gender;
import com.flz.model.mapper.EmployeeExperienceToResponseMapper;
import com.flz.model.mapper.EmployeeToEmployeeSummaryResponseMapper;
import com.flz.model.response.EmployeeDetailsResponse;
import com.flz.model.response.EmployeeExperienceResponse;
import com.flz.model.response.EmployeeSummaryResponse;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class EmployeeReadServiceImplTest extends BaseTest {

    @Mock
    EmployeeReadPort employeeReadPort;

    @Mock
    EmployeeExperienceReadPort employeeExperienceReadPort;

    @Mock
    EmployeeExperienceToResponseMapper employeeExperienceToResponseMapper;

    @InjectMocks
    EmployeeReadServiceImpl employeeReadServiceImpl;

    EmployeeToEmployeeSummaryResponseMapper
            employeeToEmployeeSummaryResponseMapper = EmployeeToEmployeeSummaryResponseMapper.INSTANCE;


    /**
     * {@link EmployeeReadServiceImpl#findById(Long)}
     */
    @Test
    void givenValidEmployeeId_whenFindByIdEmployee_thenReturnEmployeeDetailsResponseSuccessfully() {

        //Given
        Long mockId = 1L;

        //Initialize
        Employee mockEmployee = Employee.builder()
                .id(mockId)
                .firstName("Filiz")
                .lastName("Helvaci")
                .identityNumber("12345678901")
                .phoneNumber("05551231212")
                .address("Ankara")
                .email("filiz@example.com")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.of(1995, 1, 1))
                .nationality("TR")
                .build();

        Employee manager = Employee.builder()
                .firstName("Edip")
                .lastName("Cansever")
                .identityNumber("55885678901")
                .phoneNumber("05551234454")
                .address("Edirne")
                .email("edipcan@example.com")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1975, 2, 1))
                .nationality("TR")
                .build();

        EmployeeExperience empExp1 = EmployeeExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(55000))
                .startDate(LocalDate.of(2021, 11, 12))
                .endDate(LocalDate.of(2022, 11, 12))
                .position(Position.builder()
                        .id(20L)
                        .name("Kat Sorumlusu")
                        .department(Department.builder()
                                .id(18L)
                                .name("Düzen ve Tedarik Departmanı")
                                .manager(manager)
                                .build())
                        .build())
                .employee(mockEmployee)
                .build();

        EmployeeExperience empExp2 = EmployeeExperience.builder()
                .id(2L)
                .salary(BigDecimal.valueOf(75000))
                .startDate(LocalDate.of(2022, 11, 13))
                .endDate(LocalDate.of(2023, 11, 13))
                .position(Position.builder()
                        .id(28L)
                        .name("Departman Sorumlusu")
                        .department(Department.builder()
                                .id(18L)
                                .name("Düzen ve Tedarik Departmanı")
                                .manager(manager)
                                .build())
                        .build())
                .employee(mockEmployee)
                .build();

        List<EmployeeExperience> mockExperienceList = List.of(empExp1, empExp2);

        //When
        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(mockEmployee));

        Mockito.when(employeeExperienceReadPort.findAllByEmployeeId(mockId))
                .thenReturn(mockExperienceList);

        Mockito.when(employeeExperienceToResponseMapper.map(empExp1))
                .thenReturn(EmployeeExperienceResponse.builder()
                        .id(empExp1.getId())
                        .salary(empExp1.getSalary())
                        .startDate(empExp1.getStartDate())
                        .endDate(empExp1.getEndDate())
                        .positionName(empExp1.getPosition().getName())
                        .departmentName(empExp1.getPosition().getDepartment().getName())
                        .managerName(empExp1.getPosition().getDepartment().getManager().getFirstName() + " " +
                                empExp1.getPosition().getDepartment().getManager().getLastName())
                        .build());

        Mockito.when(employeeExperienceToResponseMapper.map(empExp2))
                .thenReturn(EmployeeExperienceResponse.builder()
                        .id(empExp2.getId())
                        .salary(empExp2.getSalary())
                        .startDate(empExp2.getStartDate())
                        .endDate(empExp2.getEndDate())
                        .positionName(empExp2.getPosition().getName())
                        .departmentName(empExp2.getPosition().getDepartment().getName())
                        .managerName(empExp2.getPosition().getDepartment().getManager().getFirstName() + " " +
                                empExp2.getPosition().getDepartment().getManager().getLastName())
                        .build());

        //Then
        EmployeeDetailsResponse response = employeeReadServiceImpl.findById(mockId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockEmployee, response.getEmployee());
        Assertions.assertEquals(2, response.getExperiences().size());
        Assertions.assertEquals(empExp1.getPosition().getName(), response.getExperiences().get(0).getPositionName());
        Assertions.assertEquals(empExp2.getPosition().getName(), response.getExperiences().get(1).getPositionName());
        Assertions.assertEquals(empExp1.getPosition().getDepartment().getName(), response.getExperiences().get(0).getDepartmentName());
        Assertions.assertEquals(empExp2.getPosition().getDepartment().getName(), response.getExperiences().get(1).getDepartmentName());
        Assertions.assertNotNull(empExp1.getPosition().getDepartment().getManager().getFirstName());
        Assertions.assertNotNull(empExp1.getPosition().getDepartment().getManager().getLastName());
        Assertions.assertNotNull(empExp2.getPosition().getDepartment().getManager().getFirstName());
        Assertions.assertNotNull(empExp2.getPosition().getDepartment().getManager().getLastName());
        Assertions.assertNotNull(response.getExperiences().get(0).getSalary());
        Assertions.assertNotNull(response.getExperiences().get(1).getSalary());
        Assertions.assertNotNull(response.getExperiences().get(0).getStartDate());
        Assertions.assertNotNull(response.getExperiences().get(1).getStartDate());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeId(mockId);

    }


    //Initialize
    private static List<Employee> getEmployees() {
        return List.of(
                Employee.builder()
                        .id(1L)
                        .firstName("test first name 1")
                        .lastName("test last name 1")
                        .address("test address 1")
                        .birthDate(LocalDate.parse("2000-01-01"))
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .email("test1@gmail.com")
                        .gender(Gender.FEMALE)
                        .nationality("TC")
                        .phoneNumber("05465321456")
                        .build(),
                Employee.builder()
                        .id(2L)
                        .firstName("test first name 2")
                        .lastName("test last name 2 ")
                        .address("test address 2")
                        .birthDate(LocalDate.parse("2000-02-02"))
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .email("test2@gmail.com")
                        .gender(Gender.FEMALE)
                        .nationality("TC")
                        .phoneNumber("05465321465")
                        .build(),
                Employee.builder()
                        .id(3L)
                        .firstName("test first name 3")
                        .lastName("test last name 3")
                        .address("test address 3")
                        .birthDate(LocalDate.parse("2000-03-03"))
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .email("test3@gmail.com")
                        .gender(Gender.FEMALE)
                        .nationality("TC")
                        .phoneNumber("05465321499")
                        .build());
    }

    @Test
    void givenInvalidEmployeeId_whenFindByIdEmployee_thenThrowEmployeeNotFoundException() {

        //Given
        Long invalidEmployeeId = 999L;

        //When
        Mockito.when(employeeReadPort.findById(invalidEmployeeId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeeReadServiceImpl.findById(invalidEmployeeId));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(invalidEmployeeId);
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .findAllByEmployeeId(invalidEmployeeId);

    }


    /**
     * {@link EmployeeReadServiceImpl#findSummaryAll()}
     */
    @Test
    void whenCalledAllSummaryEmployee_thenReturnListOfEmployeesSummaryResponse() {

        //Initialize
        List<Employee> mockEmployees = List.of(
                Employee.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Doe")
                        .gender(Gender.MALE)
                        .nationality("Italian")
                        .address("Ankara")
                        .build(),
                Employee.builder()
                        .id(2L)
                        .firstName("Jane")
                        .lastName("Boe")
                        .gender(Gender.FEMALE)
                        .nationality("German")
                        .address("Ankara")
                        .build(),
                Employee.builder()
                        .id(3L)
                        .firstName("Bob")
                        .lastName("Joe")
                        .gender(Gender.MALE)
                        .nationality("TC")
                        .address("Mersin")
                        .build()
        );

        List<EmployeeSummaryResponse> mockSummaryEmployees = employeeToEmployeeSummaryResponseMapper
                .map(mockEmployees);

        //When
        Mockito.when(employeeReadPort.findSummaryAll())
                .thenReturn(mockEmployees);

        //Then
        List<EmployeeSummaryResponse> response = employeeReadServiceImpl.findSummaryAll();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(mockSummaryEmployees);
        Assertions.assertEquals(mockSummaryEmployees.size(), response.size());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findSummaryAll();

    }

    @Test
    void givenValidEmployeeId_whenFindByIdEmployeeWithEmptyEmployeeExperienceList_thenReturnEmployeeDetailsResponseSuccessfully() {

        //Given
        Long mockId = 1L;

        //Initialize
        Employee mockEmployee = Employee.builder()
                .id(mockId)
                .firstName("Filiz")
                .lastName("Helvaci")
                .identityNumber("12345678901")
                .phoneNumber("05551231212")
                .address("Ankara")
                .email("filiz@example.com")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.of(1995, 1, 1))
                .nationality("TR")
                .build();

        List<EmployeeExperience> mockExperienceList = Collections.emptyList();

        //When
        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(mockEmployee));

        Mockito.when(employeeExperienceReadPort.findAllByEmployeeId(mockId))
                .thenReturn(mockExperienceList);

        //Then
        EmployeeDetailsResponse response = employeeReadServiceImpl.findById(mockId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockEmployee, response.getEmployee());
        Assertions.assertEquals(0, response.getExperiences().size());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeId(mockId);

    }

    /**
     * {@link EmployeeReadServiceImpl#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPagePageSize_whenCalledAllEmployee_thenReturnListAllOfEmployees() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        List<Employee> mockEmployees = getEmployees();

        Mockito.when(employeeReadPort.findAll(mockPage, mockPageSize))
                .thenReturn(mockEmployees);

        //Then
        List<Employee> result = employeeReadServiceImpl
                .findAll(mockPage, mockPageSize);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockEmployees.size(), result.size());
        Assertions.assertEquals(mockEmployees.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(mockEmployees.get(0).getFirstName(), result.get(0).getFirstName());
        Assertions.assertEquals(mockEmployees.get(0).getLastName(), result.get(0).getLastName());
        Assertions.assertEquals(mockEmployees.get(0).getGender(), result.get(0).getGender());
        Assertions.assertEquals(mockEmployees.get(0).getNationality(), result.get(0).getNationality());
        Assertions.assertEquals(mockEmployees.get(0).getAddress(), result.get(0).getAddress());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());

    }

    @Test
    void whenCalledAllSummaryEmployeeIfAllSummaryEntitiesIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(employeeReadPort.findSummaryAll())
                .thenReturn(Collections.emptyList());

        //Then
        List<EmployeeSummaryResponse> employeeSummaryResponses =
                employeeReadServiceImpl.findSummaryAll();

        Assertions.assertNotNull(employeeReadPort);
        Assertions.assertEquals(0, employeeSummaryResponses.size());
        Assertions.assertTrue(employeeSummaryResponses.isEmpty());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findSummaryAll();

    }

    @Test
    void givenValidPagePageSize_whenCalledAllEmployeeIfAllEmployeeIsEmpty_thenReturnEmptyList() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        Mockito.when(employeeReadPort.findAll(mockPage, mockPageSize))
                .thenReturn(Collections.emptyList());

        //Then
        List<Employee> result = employeeReadServiceImpl
                .findAll(mockPage, mockPageSize);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
        Assertions.assertTrue(result.isEmpty());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findAll(mockPage, mockPageSize);

    }

}
