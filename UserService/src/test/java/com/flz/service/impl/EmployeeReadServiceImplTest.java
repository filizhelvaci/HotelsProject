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
    EmployeeToEmployeeSummaryResponseMapper employeeToEmployeeSummaryResponseMapper;

    @Mock
    EmployeeExperienceToResponseMapper employeeExperienceToResponseMapper;

    @InjectMocks
    EmployeeReadServiceImpl employeeReadServiceImpl;

    /**
     * {@link EmployeeReadServiceImpl#findById(Long)}
     */
    @Test
    void givenValidEmployeeId_whenFindByIdEmployee_thenReturnEmployeeDetailsResponseSuccessfully() {

        //Given
        Long mockId = 1L;

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

        EmployeeExperience empExp1 = EmployeeExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(55000))
                .startDate(LocalDate.of(2021, 11, 12))
                .endDate(LocalDate.of(2022, 11, 12))
                .position(Position.builder().id(20L).name("Kat Sorumlusu").department(Department.builder().id(18L).name("Düzen ve Tedarik Departmanı").build()).build())
                .employee(mockEmployee)
                .supervisor(Employee.builder().id(85L).firstName("Ayhan").lastName("Kaymaz").build())
                .build();

        EmployeeExperience empExp2 = EmployeeExperience.builder()
                .id(2L)
                .salary(BigDecimal.valueOf(75000))
                .startDate(LocalDate.of(2022, 11, 13))
                .endDate(LocalDate.of(2023, 11, 13))
                .position(Position.builder().id(28L).name("Departman Sorumlusu").department(Department.builder().id(18L).name("Düzen ve Tedarik Departmanı").build()).build())
                .employee(mockEmployee)
                .supervisor(Employee.builder().id(85L).firstName("Ayhan").lastName("Kaymaz").build())
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
                        .positionName(empExp1.getPosition().getName())
                        .salary(empExp1.getSalary())
                        .startDate(empExp1.getStartDate())
                        .endDate(empExp1.getEndDate())
                        .supervisorName(empExp1.getSupervisor().getFirstName() + " " + empExp1.getSupervisor().getLastName())
                        .build());

        Mockito.when(employeeExperienceToResponseMapper.map(empExp2))
                .thenReturn(EmployeeExperienceResponse.builder()
                        .id(empExp2.getId())
                        .positionName(empExp2.getPosition().getName())
                        .salary(empExp2.getSalary())
                        .startDate(empExp2.getStartDate())
                        .endDate(empExp2.getEndDate())
                        .supervisorName(empExp2.getSupervisor().getFirstName() + " " + empExp2.getSupervisor().getLastName())
                        .build());

        //Then
        EmployeeDetailsResponse response = employeeReadServiceImpl.findById(mockId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockEmployee, response.getEmployee());
        Assertions.assertEquals(2, response.getExperiences()
                .size());
        Assertions.assertEquals("Kat Sorumlusu", response.getExperiences()
                .get(0)
                .getPositionName());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeId(mockId);
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

        //When
        List<Employee> mockEmployees = List.of(
                Employee.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Doe")
                        .build(),
                Employee.builder()
                        .id(2L)
                        .firstName("Jane")
                        .lastName("Boe")
                        .build(),
                Employee.builder()
                        .id(3L)
                        .firstName("Bob")
                        .lastName("Joe")
                        .build()
        );

        List<EmployeeSummaryResponse> mockSummaryEmployees = employeeToEmployeeSummaryResponseMapper
                .map(mockEmployees);

        Mockito.when(employeeReadPort.findSummaryAll())
                .thenReturn(mockEmployees);

        //Then
        List<EmployeeSummaryResponse> response = employeeReadServiceImpl.findSummaryAll();
        Assertions.assertEquals(mockSummaryEmployees.size(), response.size());
        Assertions.assertNotNull(response);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findSummaryAll();
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
        Assertions.assertEquals(mockEmployees.size(), result.size());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());
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

}
