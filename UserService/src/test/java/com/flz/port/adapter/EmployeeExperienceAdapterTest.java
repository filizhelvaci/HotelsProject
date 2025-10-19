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
import java.util.Optional;

class EmployeeExperienceAdapterTest extends BaseTest {

    @Mock
    EmployeeExperienceRepository employeeExperienceRepository;

    @InjectMocks
    EmployeeExperienceAdapter employeeExperienceAdapter;

    private final EmployeeExperienceEntityToDomainMapper
            employeeExperienceEntityToDomainMapper = EmployeeExperienceEntityToDomainMapper.INSTANCE;

    private final EmployeeExperienceToEntityMapper
            employeeExperienceToEntityMapper = EmployeeExperienceToEntityMapper.INSTANCE;


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

        //When
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


    /**
     * {@link EmployeeExperienceAdapter#existsByEmployeeIdAndPositionIdAndStartDate(Long, Long, LocalDate)}
     */
    @Test
    void givenValidEmployeeIdAndPositionIdAndStartDate_whenEmployeeExperienceCalledWithGiving_thenReturnFalse() {

        //Given
        Long mockEmployeeId = 10L;
        Long mockPositionId = 20L;
        LocalDate mockStartDate = LocalDate.of(2020, 1, 15);

        //When
        Mockito.when(employeeExperienceRepository
                        .existsByEmployeeIdAndPositionIdAndStartDate(mockEmployeeId, mockPositionId, mockStartDate))
                .thenReturn(Boolean.FALSE);

        //Then
        employeeExperienceAdapter
                .existsByEmployeeIdAndPositionIdAndStartDate(mockEmployeeId, mockPositionId, mockStartDate);

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .existsByEmployeeIdAndPositionIdAndStartDate(
                        Mockito.anyLong(),
                        Mockito.anyLong(),
                        Mockito.any(LocalDate.class)
                );

    }


    @Test
    void givenRepositoryThrowsException_whenExistsMethodCalled_thenPropagateException() {

        //Given
        Long mockEmployeeId = 10L;
        Long mockPositionId = 20L;
        LocalDate mockStartDate = LocalDate.of(2020, 1, 15);

        //When
        Mockito.when(employeeExperienceRepository
                        .existsByEmployeeIdAndPositionIdAndStartDate(mockEmployeeId, mockPositionId, mockStartDate))
                .thenThrow(new RuntimeException("Simulated DB failure"));

        //Then
        Assertions.assertThrows(RuntimeException.class,
                () -> employeeExperienceAdapter
                        .existsByEmployeeIdAndPositionIdAndStartDate(mockEmployeeId, mockPositionId, mockStartDate));

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .existsByEmployeeIdAndPositionIdAndStartDate(
                        Mockito.anyLong(),
                        Mockito.anyLong(),
                        Mockito.any(LocalDate.class)
                );
    }


    /**
     * {@link EmployeeExperienceAdapter#findTopByEmployeeIdOrderByStartDateDesc(Long)}
     */
    @Test
    void givenValidEmployeeId_whenLastEmployeeExperienceCalled_thenReturnEmployeeExperience() {

        //Given
        Long mockEmployeeId = 101L;

        //Initialize
        Optional<EmployeeExperienceEntity> employeeExperienceEntity = getEmployeeExperienceEntity();

        //When
        Mockito.when(employeeExperienceRepository.findTopByEmployeeIdOrderByStartDateDesc(mockEmployeeId))
                .thenReturn(employeeExperienceEntity);

        EmployeeExperience expected = employeeExperienceEntity
                .map(employeeExperienceEntityToDomainMapper::map)
                .orElseThrow();

        //Then
        Optional<EmployeeExperience> actual = employeeExperienceAdapter
                .findTopByEmployeeIdOrderByStartDateDesc(mockEmployeeId);

        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected.getEmployee().getId(),
                actual.get().getEmployee().getId());
        Assertions.assertEquals(expected.getEmployee().getIdentityNumber(),
                actual.get().getEmployee().getIdentityNumber());
        Assertions.assertEquals(expected.getPosition().getName(),
                actual.get().getPosition().getName());
        Assertions.assertEquals(expected.getPosition().getDepartment().getName(),
                actual.get().getPosition().getDepartment().getName());

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .findTopByEmployeeIdOrderByStartDateDesc(mockEmployeeId);
    }


    @Test
    void givenRepositoryThrowsException_whenLastEmployeeExperienceCalled_thenReturnException() {

        //Given
        Long mockEmployeeId = 101L;

        //When
        Mockito.when(employeeExperienceRepository
                        .findTopByEmployeeIdOrderByStartDateDesc(mockEmployeeId))
                .thenThrow(new RuntimeException("Simulated DB failure"));

        //Then
        Assertions.assertThrows(RuntimeException.class,
                () -> employeeExperienceAdapter
                        .findTopByEmployeeIdOrderByStartDateDesc(mockEmployeeId));

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .findTopByEmployeeIdOrderByStartDateDesc(Mockito.anyLong());

    }


    /**
     * {@link EmployeeExperienceAdapter#deleteAllByEmployeeId(Long)}
     */
    @Test
    void givenValidEmployeeId_whenDeleteAllEmployeeExperience_thenDeleteAllExperienceSuccess() {

        //Given
        Long mockEmployeeId = 101L;

        //When
        Mockito.doNothing().when(employeeExperienceRepository)
                .deleteAllByEmployee_Id(mockEmployeeId);

        //Then
        employeeExperienceAdapter.deleteAllByEmployeeId(mockEmployeeId);

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .deleteAllByEmployee_Id(mockEmployeeId);
    }


    @Test
    void givenValidEmployeeId_whenRepositoryThrowsException_thenExceptionIsPropagated() {

        //Given
        Long mockEmployeeId = 101L;

        //When
        Mockito.doThrow(new RuntimeException("Database error"))
                .when(employeeExperienceRepository)
                .deleteAllByEmployee_Id(mockEmployeeId);

        //Then
        RuntimeException thrownException = Assertions.assertThrows(RuntimeException.class,
                () -> employeeExperienceAdapter.deleteAllByEmployeeId(mockEmployeeId));

        Assertions.assertEquals("Database error", thrownException.getMessage());

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .deleteAllByEmployee_Id(mockEmployeeId);
    }

    /**
     * {@link EmployeeExperienceAdapter#findAllByEmployeeId(Long)}
     */
    @Test
    void givenValidId_whenEmployeeExperienceListFoundAccordingById_thenReturnEmployeeExperienceList() {

        //Given
        Long mockId = 101L;

        //Initialize
        PositionEntity position = PositionEntity.builder()
                .id(mockId)
                .name("Test")
                .department(DepartmentEntity.builder()
                        .id(1L)
                        .name("TestDepartment")
                        .status(DepartmentStatus.ACTIVE)
                        .manager(EmployeeEntity.builder()
                                .id(11L)
                                .firstName("Jonny")
                                .lastName("Deep")
                                .identityNumber("999896314785")
                                .email("jonnyd@example.com")
                                .phoneNumber("05059966565")
                                .address("Malatya")
                                .birthDate(LocalDate.of(1985, 1, 15))
                                .gender(Gender.MALE)
                                .nationality("USA")
                                .build())
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

        List<EmployeeExperienceEntity> mockEmployeeExperienceEntities = List.of(
                EmployeeExperienceEntity.builder()
                        .id(1L)
                        .salary(BigDecimal.valueOf(65000))
                        .startDate(LocalDate.of(2020, 1, 15))
                        .endDate(LocalDate.of(2023, 12, 31))
                        .position(position)
                        .employee(employee)
                        .build(),
                EmployeeExperienceEntity.builder()
                        .id(1L)
                        .salary(BigDecimal.valueOf(85000))
                        .startDate(LocalDate.of(2024, 1, 1))
                        .endDate(LocalDate.of(2025, 1, 31))
                        .position(position)
                        .employee(employee)
                        .build());

        //When
        Mockito.when(employeeExperienceRepository.findAllByEmployee_Id(mockId))
                .thenReturn(mockEmployeeExperienceEntities);

        List<EmployeeExperience> mockEmployeeExperiences =
                employeeExperienceEntityToDomainMapper.map(mockEmployeeExperienceEntities);

        //Then
        List<EmployeeExperience> result = employeeExperienceAdapter.findAllByEmployeeId(mockId);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(mockEmployeeExperiences);
        Assertions.assertNotNull(mockEmployeeExperiences.get(0).getEmployee());
        Assertions.assertNotNull(mockEmployeeExperiences.get(0).getPosition());
        Assertions.assertNotNull(mockEmployeeExperiences.get(0).getPosition().getId());
        Assertions.assertNotNull(mockEmployeeExperiences.get(0).getPosition().getDepartment());
        Assertions.assertNotNull(mockEmployeeExperiences.get(0).getPosition().getStatus());
        Assertions.assertNotNull(result.get(0).getPosition().getName());
        Assertions.assertNotNull(result.get(0).getPosition().getStatus());
        Assertions.assertNotNull(result.get(0).getPosition().getDepartment());
        Assertions.assertNotNull(result.get(0).getPosition().getDepartment().getName());
        Assertions.assertNotNull(result.get(0).getPosition().getDepartment().getStatus());
        Assertions.assertNotNull(result.get(0).getPosition().getDepartment().getManager().getFirstName());
        Assertions.assertNotNull(result.get(0).getPosition().getDepartment().getManager().getLastName());
        Assertions.assertNotNull(result.get(0).getPosition().getDepartment().getManager().getIdentityNumber());
        Assertions.assertNotNull(result.get(0).getPosition().getDepartment().getManager().getPhoneNumber());

        //Verify
        Mockito.verify(employeeExperienceRepository, Mockito.times(1))
                .findAllByEmployee_Id(Mockito.anyLong());

    }

    private static EmployeeExperience getEmployeeExperience() {
        Long mockId = 101L;

        Position position = getPosition(mockId);

        Employee employee = getEmployee();

        return EmployeeExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(65000))
                .startDate(LocalDate.of(2020, 1, 15))
                .endDate(LocalDate.of(2023, 12, 31))
                .position(position)
                .employee(employee)
                .build();
    }

    private Optional<EmployeeExperienceEntity> getEmployeeExperienceEntity() {

        Long mockId = 101L;

        Position position = getPosition(mockId);

        Employee employee = getEmployee();

        EmployeeExperience employeeExperience = EmployeeExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(65000))
                .startDate(LocalDate.of(2020, 1, 15))
                .position(position)
                .employee(employee)
                .build();

        return Optional.of(employeeExperienceToEntityMapper.map(employeeExperience));
    }

    private static Employee getEmployee() {

        return Employee.builder()
                .id(101L)
                .firstName("John")
                .lastName("Doe")
                .identityNumber("25896314785")
                .email("john.doe@example.com")
                .phoneNumber("05456566565")
                .gender(Gender.MALE)
                .nationality("USA")
                .build();
    }

    private static Position getPosition(Long mockId) {
        Employee manager = Employee.builder()
                .id(118L)
                .firstName("John")
                .lastName("Doe")
                .identityNumber("25896314785")
                .email("john.doe@example.com")
                .phoneNumber("05456566565")
                .gender(Gender.MALE)
                .nationality("USA")
                .build();

        return Position.builder()
                .id(mockId)
                .name("Test")
                .department(Department.builder()
                        .id(1L)
                        .name("TestDepartment")
                        .manager(manager)
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("TestSystem")
                        .build())
                .status(PositionStatus.ACTIVE)
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .build();
    }

}
