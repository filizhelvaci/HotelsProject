package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.entity.EmployeeEntity;
import com.flz.model.entity.EmployeeExperienceEntity;
import com.flz.model.entity.PositionEntity;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.EmployeeExperienceEntityToDomainMapper;
import com.flz.model.mapper.EmployeeExperienceToEntityMapper;
import com.flz.repository.EmployeeExperienceRepository;
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

class EmployeeExperienceAdapterTest extends BaseTest {

    @InjectMocks
    EmployeeExperienceAdapter employeeExperienceAdapter;

    @Mock
    EmployeeExperienceRepository employeeExperienceRepository;

    private final EmployeeExperienceEntityToDomainMapper
            employeeExperienceEntityToDomainMapper = EmployeeExperienceEntityToDomainMapper.INSTANCE;

    private final EmployeeExperienceToEntityMapper
            employeeExperienceToEntityMapper = EmployeeExperienceToEntityMapper.INSTANCE;

    /**
     * {@link EmployeeExperienceAdapter#findAllByEmployeeId(Long)}
     */
    @Test
    void givenValidId_whenEmployeeExperienceListFoundAccordingById_thenReturnEmployeeExperienceList() {

        //Given
        Long mockId = 101L;

        PositionEntity position = PositionEntity.builder()
                .id(mockId)
                .name("Test")
                .department(DepartmentEntity.builder()
                        .id(1L)
                        .name("TestDepartment")
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("TestSystem")
                        .build())
                .status(PositionStatus.ACTIVE)
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .build();

        EmployeeEntity employee = EmployeeEntity.builder()
                .id(101L)
                .firstName("John")
                .lastName("Doe")
                .identityNumber("25896314785")
                .email("john.doe@example.com")
                .phoneNumber("05456566565")
                .gender(Gender.MALE)
                .nationality("USA")
                .build();

        EmployeeEntity supervisor = EmployeeEntity.builder()
                .id(201L)
                .firstName("Jane")
                .lastName("Smith")
                .identityNumber("987654321478")
                .email("jane.smith@example.com")
                .phoneNumber("05053213232")
                .gender(Gender.MALE)
                .nationality("TC")
                .gender(Gender.FEMALE)
                .build();

        List<EmployeeExperienceEntity> mockEmployeeExperienceEntities = List.of(
                EmployeeExperienceEntity.builder()
                        .id(1L)
                        .salary(BigDecimal.valueOf(65000))
                        .startDate(LocalDate.of(2020, 1, 15))
                        .endDate(LocalDate.of(2023, 12, 31))
                        .position(position)
                        .employee(employee)
                        .supervisor(supervisor)
                        .build(),
                EmployeeExperienceEntity.builder()
                        .id(1L)
                        .salary(BigDecimal.valueOf(85000))
                        .startDate(LocalDate.of(2024, 1, 1))
                        .endDate(LocalDate.of(2025, 1, 31))
                        .position(position)
                        .employee(employee)
                        .supervisor(supervisor)
                        .build());

        Mockito.when(employeeExperienceRepository.findAllByEmployee_Id(mockId))
                .thenReturn(mockEmployeeExperienceEntities);

        List<EmployeeExperience> mockEmployeeExperiences =
                employeeExperienceEntityToDomainMapper.map(mockEmployeeExperienceEntities);

        //Then
        List<EmployeeExperience> result = employeeExperienceAdapter.findAllByEmployeeId(mockId);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(mockEmployeeExperiences);
        Assertions.assertNotNull(mockEmployeeExperiences.get(0)
                .getEmployee());
        Assertions.assertNotNull(mockEmployeeExperiences.get(0)
                .getSupervisor());
        Assertions.assertNotNull(mockEmployeeExperiences.get(0)
                .getPosition());
        Assertions.assertNotNull(mockEmployeeExperiences.get(0)
                .getPosition().getId());
        Assertions.assertNotNull(mockEmployeeExperiences.get(0)
                .getPosition().getDepartment());
        Assertions.assertNotNull(mockEmployeeExperiences.get(0)
                .getPosition().getStatus());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getName());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getStatus());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getDepartment());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getDepartment().getName());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getDepartment().getStatus());

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .findAllByEmployee_Id(Mockito.anyLong());

    }

    @Test
    void givenValidId_whenEmployeeExperienceEntityNotFoundById_returnEmptyList() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(employeeExperienceRepository.findAllByEmployee_Id(mockId))
                .thenReturn(Collections.emptyList());

        //Then
        List<EmployeeExperience> result = employeeExperienceAdapter
                .findAllByEmployeeId(mockId);

        Assertions.assertTrue(result.isEmpty());
        Assertions.assertNotNull(result);

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .findAllByEmployee_Id(Mockito.anyLong());

    }


    /**
     * {@link EmployeeExperienceAdapter#save(EmployeeExperience)}
     */
    @Test
    void givenEmployeeExperience_whenEmployeeExperienceSave_thenEmployeeExperienceEntitySuccessSave() {

        //Given
        EmployeeExperience employeeExperience = getEmployeeExperience();

        EmployeeExperienceEntity employeeExperienceEntity =
                employeeExperienceToEntityMapper.map(employeeExperience);

        Mockito.when(employeeExperienceRepository.save(employeeExperienceEntity))
                .thenReturn(employeeExperienceEntity);

        //Then
        employeeExperienceAdapter.save(employeeExperience);

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .save(Mockito.any(EmployeeExperienceEntity.class));

    }

    @Test
    void givenEmployeeExperience_whenRepositoryThrowsException_thenExceptionIsPropagated() {

        //Given
        EmployeeExperience mockEmployeeExperience = getEmployeeExperience();

        //When
        Mockito.when(employeeExperienceRepository.save(Mockito.any(EmployeeExperienceEntity.class)))
                .thenThrow(new RuntimeException("Simulated database connection error"));

        //Then
        Assertions.assertThrows(RuntimeException.class,
                () -> employeeExperienceAdapter.save(mockEmployeeExperience));

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .save(Mockito.any(EmployeeExperienceEntity.class));

    }


    private static EmployeeExperience getEmployeeExperience() {
        Long mockId = 101L;

        Position position = Position.builder()
                .id(mockId)
                .name("Test")
                .department(Department.builder()
                        .id(1L)
                        .name("TestDepartment")
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("TestSystem")
                        .build())
                .status(PositionStatus.ACTIVE)
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .build();

        Employee employee = Employee.builder()
                .id(101L)
                .firstName("John")
                .lastName("Doe")
                .identityNumber("25896314785")
                .email("john.doe@example.com")
                .phoneNumber("05456566565")
                .gender(Gender.MALE)
                .nationality("USA")
                .build();

        Employee supervisor = Employee.builder()
                .id(201L)
                .firstName("Jane")
                .lastName("Smith")
                .identityNumber("987654321478")
                .email("jane.smith@example.com")
                .phoneNumber("05053213232")
                .gender(Gender.MALE)
                .nationality("TC")
                .gender(Gender.FEMALE)
                .build();

        EmployeeExperience employeeExperience = EmployeeExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(65000))
                .startDate(LocalDate.of(2020, 1, 15))
                .endDate(LocalDate.of(2023, 12, 31))
                .position(position)
                .employee(employee)
                .supervisor(supervisor)
                .build();

        return employeeExperience;
    }

    private static Employee getEmployeeDomain() {
        return Employee.builder()
                .id(2L)
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

}