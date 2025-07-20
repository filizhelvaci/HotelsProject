package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.model.Employee;
import com.flz.model.enums.Gender;
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

        List<EmployeeExperienceResponse> mockExperienceList = List.of(
                EmployeeExperienceResponse.builder()
                        .positionName("Developer")
                        .supervisorName("Ahmet Yilmaz")
                        .salary(BigDecimal.valueOf(55000))
                        .startDate(LocalDate.of(2022, 1, 1))
                        .build(),
                EmployeeExperienceResponse.builder()
                        .positionName("Product Owner")
                        .supervisorName("Mehmet Yildirim")
                        .salary(BigDecimal.valueOf(35000))
                        .startDate(LocalDate.of(2021, 1, 1))
                        .build()
        );

        //When
        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(mockEmployee));

        Mockito.when(employeeExperienceReadPort.findAllByEmployee_Id(mockId))
                .thenReturn(mockExperienceList);

        //Then
        EmployeeDetailsResponse response = employeeReadServiceImpl.findById(mockId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockEmployee, response.getEmployee());
        Assertions.assertEquals(2, response.getExperiences()
                .size());
        Assertions.assertEquals("Developer", response.getExperiences()
                .get(0)
                .getPositionName());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployee_Id(mockId);
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
                .findAllByEmployee_Id(invalidEmployeeId);
    }


    /**
     * {@link EmployeeReadServiceImpl#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryEmployee_thenReturnListOfEmployeesSummaryResponse() {

        //When
        List<EmployeeSummaryResponse> mockEmployeeSummaryResponse = List.of(
                EmployeeSummaryResponse.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Doe")
                        .build(),
                EmployeeSummaryResponse.builder()
                        .id(2L)
                        .firstName("Jane")
                        .lastName("Boe")
                        .build(),
                EmployeeSummaryResponse.builder()
                        .id(3L)
                        .firstName("Bob")
                        .lastName("Joe")
                        .build()
        );

        Mockito.when(employeeReadPort.findSummaryAll())
                .thenReturn(mockEmployeeSummaryResponse);

        //Then
        List<EmployeeSummaryResponse> result = employeeReadServiceImpl
                .findSummaryAll();
        Assertions.assertEquals(mockEmployeeSummaryResponse, result);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findSummaryAll();
    }


    @Test
    public void whenCalledAllSummaryEmployeeIfAllSummaryEntitiesIsEmpty_thenReturnEmptyList() {

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
    public void givenValidPagePageSize_whenCalledAllEmployee_thenReturnListAllOfEmployees() {

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
    public void givenValidPagePageSize_whenCalledAllEmployeeIfAllEmployeeIsEmpty_thenReturnEmptyList() {

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