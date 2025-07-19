package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.EmployeeAlreadyExistsException;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.enums.Gender;
import com.flz.model.mapper.EmployeeCreateRequestToDomainMapper;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.port.EmployeeReadPort;
import com.flz.port.EmployeeSavePort;
import com.flz.port.PositionReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

class EmployeeWriteServiceImplTest extends BaseTest {

    @InjectMocks
    EmployeeWriteServiceImpl employeeWriteServiceImpl;

    @Mock
    EmployeeReadPort employeeReadPort;

    @Mock
    EmployeeSavePort employeeSavePort;

    @Mock
    PositionReadPort positionReadPort;

    @Mock
    EmployeeExperienceSavePort employeeExperienceSavePort;

    /**
     * {@link EmployeeWriteServiceImpl#create(EmployeeCreateRequest)}
     */
    @Test
    void givenValidCreateRequest_whenIdentityNumberThereIsNotInDatabase_thenEmployeeIsCreatedSuccessfully() {

        //Given
        Position mockPosition = Position.builder()
                .id(1L)
                .name("Developer")
                .build();

        EmployeeCreateRequest mockEmployeeCreateRequest = getEmployeeCreateRequest();

        //When
        Mockito.when(employeeReadPort.existsByIdentity("12345678901"))
                .thenReturn(false);

        Employee mockMappedEmployee = EmployeeCreateRequestToDomainMapper.INSTANCE
                .map(mockEmployeeCreateRequest);

        Employee mockSavedEmployee = getSavedEmployee();

        Mockito.when(employeeSavePort.save(mockMappedEmployee))
                .thenReturn(Optional.of(mockSavedEmployee));


        Mockito.when(positionReadPort.findById(1L))
                .thenReturn(Optional.of(mockPosition));

        Employee mockSupervisor = Employee.builder()
                .id(5L)
                .identityNumber("98765432109")
                .firstName("Eric")
                .lastName("Smith")
                .gender(Gender.MALE)
                .nationality("USA")
                .address("New York")
                .phoneNumber("05328529674")
                .build();

        Mockito.when(employeeReadPort.findById(5L))
                .thenReturn(Optional.of(mockSupervisor));

        //Then
        employeeWriteServiceImpl.create(mockEmployeeCreateRequest);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByIdentity(Mockito.anyString());
        Mockito.verify(employeeSavePort, Mockito.times(1))
                .save(Mockito.any(Employee.class));
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeExperienceSavePort, Mockito.times(1))
                .save(Mockito.any(EmployeeExperience.class));
    }

    @Test
    void givenEmployeeCreateRequest_whenIdentityNumberAlreadyExists_thenThrowsEmployeeAlreadyExistsException() {

        //Given
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setIdentityNumber("12345678901");

        //When
        Mockito.when(employeeReadPort.existsByIdentity("12345678901"))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(EmployeeAlreadyExistsException.class, () ->
                employeeWriteServiceImpl.create(request));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByIdentity("12345678901");
        Mockito.verify(employeeSavePort, Mockito.never())
                .save(Mockito.any(Employee.class));
    }

    @Test
    void givenValidRequestButEmployeeSaveFails_whenCreateEmployee_thenThrowsRuntimeException() {

        //Given
        EmployeeCreateRequest request = getEmployeeCreateRequest();

        //When
        Mockito.when(employeeReadPort.existsByIdentity("12345678901"))
                .thenReturn(false);

        Employee mappedEmployee = EmployeeCreateRequestToDomainMapper.INSTANCE.map(request);

        Mockito.when(employeeSavePort.save(mappedEmployee))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(RuntimeException.class, () -> {
            employeeWriteServiceImpl.create(request);
        });

        //Verify
        Mockito.verify(employeeSavePort)
                .save(mappedEmployee);
    }

    @Test
    void givenInvalidPositionId_whenCreateEmployee_thenThrowsPositionNotFoundException() {

        //Given
        EmployeeCreateRequest request = getEmployeeCreateRequest();

        //When
        Mockito.when(employeeReadPort.existsByIdentity("12345678901"))
                .thenReturn(false);

        Employee mappedEmployee = EmployeeCreateRequestToDomainMapper.INSTANCE.map(request);

        Employee mockSavedEmployee = getSavedEmployee();

        Mockito.when(employeeSavePort.save(mappedEmployee))
                .thenReturn(Optional.of(mockSavedEmployee));
        Mockito.when(positionReadPort.findById(999L))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(PositionNotFoundException.class,
                () -> employeeWriteServiceImpl.create(request));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByIdentity(Mockito.anyString());
        Mockito.verify(employeeSavePort, Mockito.times(1))
                .save(Mockito.any(Employee.class));
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

    }

    @Test
    void givenInvalidSupervisorId_whenCreateEmployee_thenThrowsEmployeeNotFoundException() {

        //Given
        EmployeeCreateRequest request = EmployeeCreateRequest.builder()
                .identityNumber("12345678901")
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .nationality("USA")
                .address("Ankara")
                .phoneNumber("05468529674")
                .positionId(1000L)
                .supervisorId(5L)
                .startDate(LocalDate.parse("2025-10-01"))
                .build();

        Position mockPosition = Position.builder()
                .id(1L)
                .name("Developer")
                .build();

        //When
        Mockito.when(employeeReadPort.existsByIdentity("12345678901"))
                .thenReturn(false);

        Employee mappedEmployee = EmployeeCreateRequestToDomainMapper.INSTANCE
                .map(request);

        Employee savedEmployee = getSavedEmployee();

        Mockito.when(employeeSavePort.save(mappedEmployee))
                .thenReturn(Optional.of(savedEmployee));
        Mockito.when(positionReadPort.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(mockPosition));
        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(EmployeeNotFoundException.class,

                () -> employeeWriteServiceImpl.create(request));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByIdentity(Mockito.anyString());
        Mockito.verify(employeeSavePort, Mockito.times(1))
                .save(Mockito.any(Employee.class));
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
    }

    private static EmployeeCreateRequest getEmployeeCreateRequest() {
        return EmployeeCreateRequest.builder()
                .identityNumber("12345678901")
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .nationality("USA")
                .address("Ankara")
                .phoneNumber("05468529674")
                .positionId(1L)
                .supervisorId(5L)
                .startDate(LocalDate.parse("2025-10-01"))
                .build();
    }

    private static Employee getSavedEmployee() {
        return Employee.builder()
                .id(10L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .nationality("USA")
                .address("Ankara")
                .phoneNumber("05468529674")
                .identityNumber("12345678901")
                .build();
    }

}