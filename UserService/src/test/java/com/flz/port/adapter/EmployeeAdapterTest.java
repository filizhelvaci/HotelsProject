package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.Employee;
import com.flz.model.entity.EmployeeEntity;
import com.flz.model.enums.Gender;
import com.flz.model.response.EmployeeSummaryResponse;
import com.flz.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class EmployeeAdapterTest extends BaseTest {

    @InjectMocks
    EmployeeAdapter employeeAdapter;

    @Mock
    EmployeeRepository employeeRepository;


    /**
     * {@link EmployeeAdapter#findSummaryAll()}
     */
    @Test
    void whenCalledAllSummaryEmployee_thenReturnListOfEmployeeSummariesResponse() {

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
                        .lastName("Doe")
                        .build(),
                EmployeeSummaryResponse.builder()
                        .id(3L)
                        .firstName("Bob")
                        .lastName("Joe")
                        .build());

        Mockito.when(employeeRepository.findEmployeeSummaries())
                .thenReturn(mockEmployeeSummaryResponse);

        //Then
        List<EmployeeSummaryResponse> result = employeeAdapter.findSummaryAll();
        Assertions.assertEquals(mockEmployeeSummaryResponse.size(), result.size());

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findEmployeeSummaries();
    }


    @Test
    void whenCalledAllSummaryEmployeeIfEmployeeListIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(employeeRepository.findEmployeeSummaries())
                .thenReturn(Collections.emptyList());

        //Then
        List<EmployeeSummaryResponse> responses = employeeAdapter.findSummaryAll();

        Assertions.assertEquals(0, responses.size());
        Assertions.assertTrue(responses.isEmpty());

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findEmployeeSummaries();
    }

    /**
     * {@link EmployeeAdapter#findById(Long)}
     */
    @Test
    public void givenValidId_whenEmployeeEntityFoundAccordingById_thenReturnEmployee() {

        //Given
        Long mockId = 1L;

        //When
        Optional<EmployeeEntity> mockEmployeeEntity = Optional.of(getEmployeeEntity(mockId));

        Mockito.when(employeeRepository.findById(mockId))
                .thenReturn(mockEmployeeEntity);

        //Then
        Optional<Employee> employee = employeeAdapter.findById(mockId);

        Assertions.assertNotNull(employee);
        Assertions.assertTrue(employee.isPresent());
        Assertions.assertEquals(mockId, employee.get()
                .getId());
        Assertions.assertNotNull(employee.get()
                .getId());
        Assertions.assertNotNull(employee.get()
                .getFirstName());
        Assertions.assertNotNull(employee.get()
                .getLastName());
        Assertions.assertNotNull(employee.get()
                .getGender());
        Assertions.assertNotNull(employee.get()
                .getBirthDate());

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findById(mockId);
    }


    @Test
    public void givenValidId_whenEmployeeEntityNotFoundById_returnOptionalEmpty() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(employeeRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Optional<Employee> employee = employeeAdapter.findById(mockId);

        Assertions.assertFalse(employee.isPresent());

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findById(mockId);

    }

    private static EmployeeEntity getEmployeeEntity(Long id) {
        return EmployeeEntity.builder()
                .id(id)
                .address("test address")
                .firstName("test first name")
                .lastName("test last name")
                .birthDate(LocalDate.parse("2000-01-01"))
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .email("test@gmail.com")
                .gender(Gender.FEMALE)
                .nationality("TC")
                .phoneNumber("05465321456")
                .build();
    }

    private static List<EmployeeEntity> getEmployeeEntities() {
        return List.of(
                EmployeeEntity.builder()
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
                EmployeeEntity.builder()
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
                EmployeeEntity.builder()
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