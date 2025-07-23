package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.Employee;
import com.flz.model.entity.EmployeeEntity;
import com.flz.model.enums.Gender;
import com.flz.model.mapper.EmployeeEntityToDomainMapper;
import com.flz.model.mapper.EmployeeToEntityMapper;
import com.flz.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    private final EmployeeToEntityMapper employeeToEntityMapper = EmployeeToEntityMapper.INSTANCE;

    @Mock
    EmployeeEntityToDomainMapper employeeEntityToDomainMapper;

    /**
     * {@link EmployeeAdapter#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPageAndPageSize_whenEmployeeFound_thenReturnListEmployee() {

        //Given
        int mockPage = 1;
        int mockPageSize = 10;

        //When
        List<EmployeeEntity> mockEmployeeEntities = getEmployeeEntities();
        Pageable mockPageable = PageRequest.of(0, mockPageSize);

        Page<EmployeeEntity> mockEmployeeEntitiesPage = new PageImpl<>(mockEmployeeEntities);
        Mockito.when(employeeRepository.findAll(mockPageable))
                .thenReturn(mockEmployeeEntitiesPage);

        //Then
        List<Employee> employees = employeeAdapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(mockEmployeeEntities.size(), employees.size());

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findAll(mockPageable);
    }

    @Test
    void givenValidPageAndPageSize_whenEmployeeNotFound_thenReturnEmptyPositions() {

        //Given
        int mockPage = 1;
        int mockPageSize = 10;

        //When
        Pageable mockPageable = PageRequest.of(0, mockPageSize);
        Mockito.when(employeeRepository.findAll(mockPageable))
                .thenReturn(Page.empty());

        //Then
        List<Employee> employees = employeeAdapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(0, employees.size());
        Assertions.assertEquals(0, employeeRepository.count());
        Assertions.assertTrue(employees.isEmpty());

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findAll(mockPageable);
    }

    /**
     * {@link EmployeeAdapter#findSummaryAll()}
     */
    @Test
    void whenCalledAllSummaryEmployee_thenReturnListOfEmployeeSummariesResponse() {

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
                        .lastName("Doe")
                        .build(),
                Employee.builder()
                        .id(3L)
                        .firstName("Bob")
                        .lastName("Joe")
                        .build());

        Mockito.when(employeeRepository.findEmployeeSummaries())
                .thenReturn(mockEmployees);

        //Then
        List<Employee> result = employeeAdapter.findSummaryAll();
        Assertions.assertEquals(mockEmployees.size(), result.size());

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
        List<Employee> responses = employeeAdapter.findSummaryAll();

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

    /**
     * {@link EmployeeAdapter#existsByIdentity(String)}
     */
    @Test
    public void givenValidIdentityNumber_whenEmployeeEntityFoundAccordingByIdentity_thenReturnTrue() {

        //Given
        String mockIdentity = "test";

        //When
        Mockito.when(employeeRepository.existsByIdentityNumber(mockIdentity))
                .thenReturn(Boolean.TRUE);

        //Then
        boolean result = employeeAdapter.existsByIdentity("test");

        Assertions.assertTrue(result);

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .existsByIdentityNumber(Mockito.anyString());

    }

    @Test
    public void givenValidIdentityNumber_whenEmployeeEntityNotFoundAccordingByIdentityNumber_thenReturnFalse() {

        //Given
        String mockIdentity = "TestIdentity";

        //When
        Mockito.when(employeeRepository.existsByIdentityNumber(mockIdentity))
                .thenReturn(Boolean.FALSE);

        //Then
        boolean result = employeeAdapter.existsByIdentity(mockIdentity);

        Assertions.assertFalse(result);

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .existsByIdentityNumber(Mockito.anyString());

    }

    /**
     * {@link EmployeeAdapter#existsByPhoneNumber(String)}
     */
    @Test
    public void givenValidPhoneNumber_whenEmployeeEntityFoundAccordingByPhoneNumber_thenReturnTrue() {

        //Given
        String mockPhoneNumber = "05558978978";

        //When
        Mockito.when(employeeRepository.existsByPhoneNumber(mockPhoneNumber))
                .thenReturn(Boolean.TRUE);

        //Then
        boolean result = employeeAdapter.existsByPhoneNumber(mockPhoneNumber);

        Assertions.assertTrue(result);

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .existsByPhoneNumber(Mockito.anyString());

    }

    @Test
    public void givenValidPhoneNumber_whenEmployeeEntityNotFoundAccordingByPhoneNumber_thenReturnFalse() {

        //Given
        String mockPhoneNumber = "5551111111";

        //When
        Mockito.when(employeeRepository.existsByPhoneNumber(mockPhoneNumber))
                .thenReturn(Boolean.FALSE);

        //Then
        boolean result = employeeAdapter.existsByPhoneNumber(mockPhoneNumber);

        Assertions.assertFalse(result);

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .existsByPhoneNumber(Mockito.anyString());

    }

    /**
     * {@link EmployeeAdapter#save(Employee)} )}
     */
    @Test
    public void givenEmployee_whenCalledSave_thenSaveEmployeeEntity() {

        //Given
        Employee mockEmployee = getEmployee();

        EmployeeEntity mockEmployeeEntity = employeeToEntityMapper.map(mockEmployee);

        //When
        Mockito.when(employeeRepository.save(Mockito.any(EmployeeEntity.class)))
                .thenReturn(mockEmployeeEntity);

        //Then
        Optional<Employee> result = employeeAdapter.save(mockEmployee);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockEmployee.getFirstName(), result.get()
                .getFirstName());
        Assertions.assertEquals(mockEmployee.getLastName(), result.get()
                .getLastName());
        Assertions.assertEquals(mockEmployee.getGender(), result.get()
                .getGender());
        Assertions.assertEquals(mockEmployee.getBirthDate(), result.get()
                .getBirthDate());

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .save(Mockito.any());
    }


    @Test
    public void givenEmployee_whenRepositoryThrowsException_thenExceptionIsPropagated() {

        //Given
        Employee mockEmployee = getEmployee();

        //When
        Mockito.when(employeeRepository.save(Mockito.any(EmployeeEntity.class)))
                .thenThrow(new RuntimeException("Simulated database connection error"));

        //Then
        Assertions.assertThrows(RuntimeException.class,
                () -> employeeAdapter.save(mockEmployee));

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .save(Mockito.any(EmployeeEntity.class));

        Mockito.verify(employeeEntityToDomainMapper, Mockito.never())
                .map(Mockito.any(EmployeeEntity.class));
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

    private static Employee getEmployee() {
        return Employee.builder()
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
