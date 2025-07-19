package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.Position;
import com.flz.model.enums.Gender;
import com.flz.model.mapper.EmployeeCreateRequestToDomainMapper;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.PositionCreateRequest;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.port.EmployeeReadPort;
import com.flz.port.EmployeeSavePort;
import com.flz.port.PositionReadPort;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
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
     * {@link PositionWriteServiceImpl#create(PositionCreateRequest)}
     */
    @Test
    void givenValidCreateRequest_whenCreate_thenEmployeeIsCreatedSuccessfully() {

        //Given
        EmployeeCreateRequest mockEmployeeCreateRequest = new EmployeeCreateRequest();
        mockEmployeeCreateRequest.setFirstName("John");
        mockEmployeeCreateRequest.setLastName("Doe");
        mockEmployeeCreateRequest.setIdentityNumber("12345678901");
        mockEmployeeCreateRequest.setPhoneNumber("05468529674");
        mockEmployeeCreateRequest.setAddress("Ankara");
        mockEmployeeCreateRequest.setGender(Gender.MALE);
        mockEmployeeCreateRequest.setNationality("USA");
        mockEmployeeCreateRequest.setSalary(BigDecimal.valueOf(2500));
        mockEmployeeCreateRequest.setPositionId(1L);
        mockEmployeeCreateRequest.setDepartmentId(2L);
        mockEmployeeCreateRequest.setSupervisorId(5L);
        mockEmployeeCreateRequest.setStartDate(LocalDate.parse("2025-10-01"));

        //When
        Mockito.when(employeeReadPort.existsByIdentity("12345678901"))
                .thenReturn(false);

        Employee mockMappedEmployee = EmployeeCreateRequestToDomainMapper.INSTANCE
                .map(mockEmployeeCreateRequest);

        Employee mockSavedEmployee = Employee.builder()
                .id(10L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .nationality("USA")
                .address("Ankara")
                .phoneNumber("05468529674")
                .identityNumber("12345678901")
                .build();

        Mockito.when(employeeSavePort.save(mockMappedEmployee))
                .thenReturn(Optional.of(mockSavedEmployee));

        Position mockPosition = Position.builder()
                .id(1L)
                .name("Developer")
                .build();

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


}