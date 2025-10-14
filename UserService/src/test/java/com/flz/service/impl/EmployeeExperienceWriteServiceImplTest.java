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

    @Mock
    EmployeeExperienceSavePort employeeExperienceSavePort;

    @Mock
    EmployeeExperienceReadPort employeeExperienceReadPort;

    @Mock
    EmployeeReadPort employeeReadPort;

    @Mock
    PositionReadPort positionReadPort;

    @InjectMocks
    EmployeeExperienceWriteServiceImpl employeeExperienceWriteServiceImpl;


    /**
     * {@link EmployeeExperienceWriteServiceImpl#create(Long, EmployeeExperienceCreateRequest)}
     */
    @Test
    void givenValidEmployeeIdAndCreateRequest_whenSameEmployeeExperienceThereIsNot_thenCreateSuccessfully() {

        //Initialize
        Long positionId = 12L;

        Employee employee = getEmployee();
        Position position = getPosition2();

        //Given
        Long employeeId = 101L;

        EmployeeExperienceCreateRequest request = EmployeeExperienceCreateRequest.builder()
                .positionId(positionId)
                .startDate(LocalDate.now().plusDays(5))
                .salary(BigDecimal.valueOf(10000))
                .build();

        EmployeeExperience employeeExperience = EmployeeExperience.builder()
                .salary(request.getSalary())
                .startDate(request.getStartDate())
                .position(position)
                .employee(employee)
                .build();

        EmployeeExperience lastExperience = getEmployeeExperience();
        lastExperience.setEndDate(request.getStartDate().minusDays(1));

        //When
        Mockito.when(employeeReadPort.findById(employeeId))
                .thenReturn(Optional.of(employee));
        Mockito.when(positionReadPort.findById(positionId))
                .thenReturn(Optional.of(position));
        Mockito.when(employeeExperienceReadPort.existsByEmployeeIdAndPositionIdAndStartDate(
                employeeId, positionId, request.getStartDate())).thenReturn(false);
        Mockito.when(employeeExperienceReadPort.findTopByEmployeeIdOrderByStartDateDesc(employeeId))
                .thenReturn(Optional.of(lastExperience));
        Mockito.doNothing().when(employeeExperienceSavePort)
                .save(lastExperience);
        Mockito.doNothing().when(employeeExperienceSavePort)
                .save(employeeExperience);

        //Then
        employeeExperienceWriteServiceImpl.create(employeeId, request);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
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
    void givenValidEmployeeId_whenEmployeeNotFound_thenThrowEmployeeNotFoundException() {

        //Given
        Long mockEmployeeId = 1L;

        EmployeeExperienceCreateRequest request = EmployeeExperienceCreateRequest.builder()
                .positionId(11L)
                .startDate(LocalDate.now().plusDays(5))
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
    void givenValidEmployeeIdAndCreateRequest_whenPositionThereIsNot_thenThrowPositionNotFoundException() {

        //Given
        Long mockEmployeeId = 101L;
        Long mockPositionId = 1111L;

        EmployeeExperienceCreateRequest mockRequest = EmployeeExperienceCreateRequest.builder()
                .positionId(mockPositionId)
                .startDate(LocalDate.now().plusDays(5))
                .build();

        //When
        Employee mockEmployee = getEmployee();

        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(mockEmployee));
        Mockito.when(positionReadPort.findById(mockPositionId))
                .thenReturn(Optional.empty());
        //Then
        Assertions.assertThrows(PositionNotFoundException.class,
                () -> employeeExperienceWriteServiceImpl.create(mockEmployeeId, mockRequest));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
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
    void givenValidEmployeeIdAndCreateRequest_whenSameExperienceThereIs_thenThrowEmployeeExperienceAlreadyExists() {

        //Initialize
        Long positionId = 12L;

        Employee employee = getEmployee();
        Position position = getPosition2();

        //Given
        Long mockEmployeeId = 101L;

        EmployeeExperienceCreateRequest request = EmployeeExperienceCreateRequest.builder()
                .positionId(positionId)
                .startDate(LocalDate.now().plusDays(5))
                .salary(BigDecimal.valueOf(10000))
                .build();

        //When
        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(employee));
        Mockito.when(positionReadPort.findById(positionId))
                .thenReturn(Optional.of(position));
        Mockito.when(employeeExperienceReadPort.existsByEmployeeIdAndPositionIdAndStartDate(
                mockEmployeeId, positionId, request.getStartDate())).thenReturn(true);

        //Then
        Assertions.assertThrows(EmployeeExperienceAlreadyExistsException.class,
                () -> employeeExperienceWriteServiceImpl.create(mockEmployeeId, request));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
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

    private static Employee getEmployee() {

        return Employee.builder()
                .id(101L)
                .firstName("John")
                .lastName("Doe")
                .identityNumber("25896314785")
                .email("john.doe@example.com")
                .phoneNumber("05456566565")
                .address("Ankara")
                .birthDate(LocalDate.of(2000, 1, 15))
                .gender(Gender.MALE)
                .nationality("USA")
                .build();
    }

    private static Position getPosition() {

        Employee manager = Employee.builder()
                .id(201L)
                .firstName("Denis")
                .lastName("Smith")
                .identityNumber("987111321118")
                .email("denisSS@example.com")
                .phoneNumber("05057744232")
                .gender(Gender.MALE)
                .nationality("TC")
                .build();

        return Position.builder()
                .id(11L)
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

    private static Position getPosition2() {

        Employee manager = Employee.builder()
                .id(221L)
                .firstName("Jenifer")
                .lastName("See")
                .identityNumber("955555551478")
                .email("jenyjeny@example.com")
                .phoneNumber("053212333332")
                .nationality("TC")
                .gender(Gender.FEMALE)
                .build();

        return Position.builder()
                .id(12L)
                .name("TestPosition2")
                .department(Department.builder()
                        .id(1L)
                        .name("TestDepartment2")
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

    private static EmployeeExperience getEmployeeExperience() {

        Position position = getPosition();
        Employee employee = getEmployee();

        return EmployeeExperience.builder()
                .id(3L)
                .salary(BigDecimal.valueOf(65000))
                .startDate(LocalDate.of(2021, 2, 15))
                .position(position)
                .employee(employee)
                .build();
    }

}
