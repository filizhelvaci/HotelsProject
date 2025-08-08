package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.EmployeeExperienceAlreadyExistsException;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.enums.PositionStatus;
import com.flz.model.request.EmployeeExperienceCreateRequest;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.port.EmployeeReadPort;
import com.flz.port.PositionReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


class EmployeeExperienceWriteServiceImplTest extends BaseTest {

    @InjectMocks
    EmployeeExperienceWriteServiceImpl employeeExperienceWriteServiceImpl;

    @Mock
    EmployeeExperienceSavePort employeeExperienceSavePort;

    @Mock
    EmployeeExperienceReadPort employeeExperienceReadPort;

    @Mock
    EmployeeReadPort employeeReadPort;

    @Mock
    PositionReadPort positionReadPort;

    @Test
    void givenValidEmployeeIdAndValidCreateRequest_whenEmployeeExperienceCreateCalled_thenCreateSuccessfully() {

        //Given
        Long employeeId = 101L;
        Long supervisorId = 201L;
        Long positionId = 11L;

        Employee employee = getEmployee();
        Employee supervisor = getSupervisor();
        Position position = getPosition();

        EmployeeExperience lastExperience = getEmployeeExperience2();

        EmployeeExperienceCreateRequest request = EmployeeExperienceCreateRequest.builder()
                .supervisorId(supervisorId)
                .positionId(positionId)
                .startDate(LocalDate.of(2024, 1, 1))
                .salary(BigDecimal.valueOf(10000))
                .build();

        EmployeeExperience lastExperience2 = getEmployeeExperience2();
        lastExperience2.setEndDate(request.getStartDate().minusDays(1));

        //When
        Mockito.when(employeeReadPort.findById(employeeId))
                .thenReturn(Optional.of(employee));
        Mockito.when(employeeReadPort.findById(supervisorId))
                .thenReturn(Optional.of(supervisor));
        Mockito.when(positionReadPort.findById(positionId))
                .thenReturn(Optional.of(position));
        Mockito.when(employeeExperienceReadPort.existsByEmployeeIdAndPositionIdAndStartDate(
                employeeId, positionId, request.getStartDate())).thenReturn(false);
        Mockito.when(employeeExperienceReadPort.findTopByEmployeeIdOrderByStartDateDesc(employeeId))
                .thenReturn(Optional.of(lastExperience));
        Mockito.doNothing().when(employeeExperienceSavePort)
                .save(lastExperience2);
        Mockito.doNothing().when(employeeExperienceSavePort)
                .save(Mockito.any(EmployeeExperience.class));

        //Then
        employeeExperienceWriteServiceImpl.create(employeeId, request);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(2))
                .findById(Mockito.anyLong());
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .existsByEmployeeIdAndPositionIdAndStartDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findTopByEmployeeIdOrderByStartDateDesc(Mockito.anyLong());
        Mockito.verify(employeeExperienceSavePort, Mockito.times(2))
                .save(Mockito.any(EmployeeExperience.class));

    }

    @Test
    void givenValidEmployeeId_whenEmployeeNotFound_thenThrowException() {

        //Given
        Long mockEmployeeId = 1L;

        EmployeeExperienceCreateRequest request = EmployeeExperienceCreateRequest.builder()
                .supervisorId(201L)
                .positionId(11L)
                .startDate(LocalDate.of(2024, 1, 1))
                .build();

        //When
        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeeExperienceWriteServiceImpl.create(mockEmployeeId, request));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(positionReadPort, Mockito.never())
                .findById(Mockito.anyLong());
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .existsByEmployeeIdAndPositionIdAndStartDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .findTopByEmployeeIdOrderByStartDateDesc(Mockito.anyLong());
        Mockito.verify(employeeExperienceSavePort, Mockito.never())
                .save(Mockito.any(EmployeeExperience.class));
    }

    @Test
    void givenValidEmployeeIdAndCreateRequest_whenEmployeeExperienceCreateCalledButSupervisorNotFound_thenThrowException() {

        //Given
        Long mockEmployeeId = 101L;
        Long mockSupervisorId = 1L;

        EmployeeExperienceCreateRequest request = EmployeeExperienceCreateRequest.builder()
                .supervisorId(mockSupervisorId)
                .positionId(11L)
                .startDate(LocalDate.of(2024, 1, 1))
                .build();

        //When
        Employee employee = getEmployee();

        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(employee));
        Mockito.when(employeeReadPort.findById(mockSupervisorId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeeExperienceWriteServiceImpl.create(mockEmployeeId, request));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(2))
                .findById(Mockito.anyLong());
        Mockito.verify(positionReadPort, Mockito.never())
                .findById(Mockito.anyLong());
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .existsByEmployeeIdAndPositionIdAndStartDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .findTopByEmployeeIdOrderByStartDateDesc(Mockito.anyLong());
        Mockito.verify(employeeExperienceSavePort, Mockito.never())
                .save(Mockito.any(EmployeeExperience.class));
    }

    @Test
    void givenValidEmployeeIdAndCreateRequest_whenEmployeeExperienceCreateCalledButPositionNotFound_thenThrowException() {

        //Given
        Long mockEmployeeId = 101L;
        Long mockSupervisorId = 201L;
        Long mockPositionId = 1111L;

        EmployeeExperienceCreateRequest mockRequest = EmployeeExperienceCreateRequest.builder()
                .supervisorId(mockSupervisorId)
                .positionId(mockPositionId)
                .startDate(LocalDate.of(2024, 1, 1))
                .build();

        //When
        Employee mockEmployee = getEmployee();
        Employee mockSupervisor = getSupervisor();

        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(mockEmployee));
        Mockito.when(employeeReadPort.findById(mockSupervisorId))
                .thenReturn(Optional.of(mockSupervisor));
        Mockito.when(positionReadPort.findById(mockPositionId))
                .thenReturn(Optional.empty());
        //Then
        Assertions.assertThrows(PositionNotFoundException.class,
                () -> employeeExperienceWriteServiceImpl.create(mockEmployeeId, mockRequest));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(2))
                .findById(Mockito.anyLong());
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .existsByEmployeeIdAndPositionIdAndStartDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .findTopByEmployeeIdOrderByStartDateDesc(Mockito.anyLong());
        Mockito.verify(employeeExperienceSavePort, Mockito.never())
                .save(Mockito.any(EmployeeExperience.class));
    }

    @Test
    void givenValidEmployeeIdAndRequest_whenEmployeeExperienceCreateCalled_thenThrowException() {

        //Given
        Long mockEmployeeId = 101L;
        Long mockSupervisorId = 201L;
        Long mockPositionId = 11L;
        LocalDate mockStartDate = LocalDate.of(2020, 1, 15);

        EmployeeExperienceCreateRequest mockRequest = EmployeeExperienceCreateRequest.builder()
                .supervisorId(mockSupervisorId)
                .positionId(mockPositionId)
                .salary(BigDecimal.valueOf(65000))
                .startDate(mockStartDate)
                .build();

        //When
        Employee mockEmployee = getEmployee();
        Employee mockSupervisor = getSupervisor();
        Position mockPosition = getPosition();

        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(mockEmployee));
        Mockito.when(employeeReadPort.findById(mockSupervisorId))
                .thenReturn(Optional.of(mockSupervisor));
        Mockito.when(positionReadPort.findById(mockPositionId))
                .thenReturn(Optional.of(mockPosition));
        Mockito.when(employeeExperienceReadPort
                        .existsByEmployeeIdAndPositionIdAndStartDate(mockEmployeeId, mockPositionId, mockStartDate))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(EmployeeExperienceAlreadyExistsException.class,
                () -> employeeExperienceWriteServiceImpl.create(mockEmployeeId, mockRequest));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(2))
                .findById(Mockito.anyLong());
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .existsByEmployeeIdAndPositionIdAndStartDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .findTopByEmployeeIdOrderByStartDateDesc(Mockito.anyLong());
        Mockito.verify(employeeExperienceSavePort, Mockito.never())
                .save(Mockito.any(EmployeeExperience.class));

    }

    @Test
    void givenValidEmployeeIdAndRequest_whenNoPreviousExperience_thenCreateWithoutUpdatingPrevious() {

        //Given
        Long employeeId = 101L;
        Long supervisorId = 201L;
        Long positionId = 11L;

        Employee employee = getEmployee();
        Employee supervisor = getSupervisor();
        Position position = getPosition();

        EmployeeExperienceCreateRequest request = EmployeeExperienceCreateRequest.builder()
                .supervisorId(supervisorId)
                .positionId(positionId)
                .startDate(LocalDate.of(2024, 6, 1))
                .salary(BigDecimal.valueOf(10000))
                .build();

        //When
        Mockito.when(employeeReadPort.findById(employeeId))
                .thenReturn(Optional.of(employee));
        Mockito.when(employeeReadPort.findById(supervisorId))
                .thenReturn(Optional.of(supervisor));
        Mockito.when(positionReadPort.findById(positionId))
                .thenReturn(Optional.of(position));
        Mockito.when(employeeExperienceReadPort.existsByEmployeeIdAndPositionIdAndStartDate(
                        employeeId, positionId, request.getStartDate()))
                .thenReturn(false);
        Mockito.when(employeeExperienceReadPort.findTopByEmployeeIdOrderByStartDateDesc(employeeId))
                .thenReturn(Optional.empty());

        //Then
        employeeExperienceWriteServiceImpl.create(employeeId, request);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(2))
                .findById(Mockito.anyLong());
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .existsByEmployeeIdAndPositionIdAndStartDate(Mockito.anyLong(), Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceSavePort, Mockito.times(1))
                .save(Mockito.argThat(experience ->
                        experience.getStartDate().equals(request.getStartDate()) &&
                                experience.getSalary().equals(request.getSalary())
                ));
        Mockito.verify(employeeExperienceSavePort, Mockito.never())
                .save(Mockito.argThat(experience -> experience.getEndDate() != null));
    }

    @Test
    void givenStartDateBeforeLastExperienceStartDate_whenCreate_thenAllowOrThrowDependingOnRule() {

        //Given
        Long employeeId = 101L;
        Long supervisorId = 201L;
        Long positionId = 11L;

        Employee employee = getEmployee();
        Employee supervisor = getSupervisor();
        Position position = getPosition();

        LocalDate newStartDate = LocalDate.of(2020, 1, 1);
        LocalDate lastStartDate = LocalDate.of(2023, 1, 1);

        EmployeeExperience lastExperience = getEmployeeExperience2();
        lastExperience.setStartDate(lastStartDate);

        EmployeeExperienceCreateRequest request = EmployeeExperienceCreateRequest.builder()
                .supervisorId(supervisorId)
                .positionId(positionId)
                .startDate(newStartDate)
                .salary(BigDecimal.valueOf(10000))
                .build();

        // When
        Mockito.when(employeeReadPort.findById(employeeId)).thenReturn(Optional.of(employee));
        Mockito.when(employeeReadPort.findById(supervisorId)).thenReturn(Optional.of(supervisor));
        Mockito.when(positionReadPort.findById(positionId)).thenReturn(Optional.of(position));
        Mockito.when(employeeExperienceReadPort.existsByEmployeeIdAndPositionIdAndStartDate(
                employeeId, positionId, newStartDate)).thenReturn(false);
        Mockito.when(employeeExperienceReadPort.findTopByEmployeeIdOrderByStartDateDesc(employeeId))
                .thenReturn(Optional.of(lastExperience));

        employeeExperienceWriteServiceImpl.create(employeeId, request);

        // Then
        Mockito.verify(employeeExperienceSavePort, Mockito.times(2)).save(Mockito.any());
    }

    private static EmployeeExperience getEmployeeExperience2() {

        Position position = getPosition2();
        Employee employee = getEmployee();
        Employee supervisor = getSupervisor();

        return EmployeeExperience.builder()
                .id(2L)
                .salary(BigDecimal.valueOf(85000))
                .startDate(LocalDate.of(2023, 2, 15))
                .position(position)
                .employee(employee)
                .supervisor(supervisor)
                .build();
    }

    private static Employee getEmployee() {

        return Employee.builder()
                .id(101L)
                .firstName("John")
                .lastName("Doe")
                .identityNumber("25896314785")
                .email("john.doe@example.com")
                .phoneNumber("05456566565")
                .address("Ankara")
                .birthDate(LocalDate.of(2020, 1, 15))
                .gender(Gender.MALE)
                .nationality("USA")
                .build();
    }

    private static Employee getSupervisor() {

        return Employee.builder()
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
    }

    private static Position getPosition() {

        return Position.builder()
                .id(11L)
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
    }

    private static Position getPosition2() {

        return Position.builder()
                .id(12L)
                .name("TestPosition2")
                .department(Department.builder()
                        .id(1L)
                        .name("TestDepartment2")
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