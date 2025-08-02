package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.EmployeeAlreadyExistsException;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.EmployeeExperience;
import com.flz.model.EmployeeOld;
import com.flz.model.EmployeeOldExperience;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.EmployeeCreateRequestToDomainMapper;
import com.flz.model.mapper.EmployeeExperienceToEmployeeOldExperienceMapper;
import com.flz.model.mapper.EmployeeToEmployeeOldMapper;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.EmployeeUpdateRequest;
import com.flz.port.EmployeeDeletePort;
import com.flz.port.EmployeeExperienceDeletePort;
import com.flz.port.EmployeeExperienceReadPort;
import com.flz.port.EmployeeExperienceSavePort;
import com.flz.port.EmployeeOldExperienceSavePort;
import com.flz.port.EmployeeOldSavePort;
import com.flz.port.EmployeeReadPort;
import com.flz.port.EmployeeSavePort;
import com.flz.port.PositionReadPort;
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

class EmployeeWriteServiceImplTest extends BaseTest {

    @InjectMocks
    EmployeeWriteServiceImpl employeeWriteServiceImpl;

    @Mock
    EmployeeReadPort employeeReadPort;

    @Mock
    EmployeeSavePort employeeSavePort;

    @Mock
    EmployeeDeletePort employeeDeletePort;

    @Mock
    EmployeeOldSavePort employeeOldSavePort;

    @Mock
    PositionReadPort positionReadPort;

    @Mock
    EmployeeExperienceSavePort employeeExperienceSavePort;

    @Mock
    EmployeeExperienceReadPort employeeExperienceReadPort;

    @Mock
    EmployeeExperienceDeletePort employeeExperienceDeletePort;

    @Mock
    EmployeeOldExperienceSavePort employeeOldExperienceSavePort;

    EmployeeToEmployeeOldMapper
            employeeToEmployeeOldMapper = EmployeeToEmployeeOldMapper.INSTANCE;

    EmployeeExperienceToEmployeeOldExperienceMapper
            employeeExperienceToEmployeeOldExperienceMapper = EmployeeExperienceToEmployeeOldExperienceMapper.INSTANCE;

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
        Assertions.assertThrows(RuntimeException.class,
                () -> employeeWriteServiceImpl.create(request));

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

    /**
     * {@link EmployeeWriteServiceImpl#update(Long, EmployeeUpdateRequest)}
     */
    @Test
    void givenValidIdAndValidUpdateRequest_whenUpdateEmployee_thenEmployeeIsUpdatedSuccessful() {

        //Given
        Long mockEmployeeId = 1L;

        EmployeeUpdateRequest request = EmployeeUpdateRequest.builder()
                .firstName("UpdatedName")
                .lastName("UpdatedSurname")
                .identityNumber("99999999999")
                .email("updated@example.com")
                .phoneNumber("05559998877")
                .address("Updated Address")
                .birthDate(LocalDate.of(1990, 1, 1))
                .gender(Gender.FEMALE)
                .nationality("UpdatedCountry")
                .build();

        Employee mockEmployee = Employee.builder()
                .id(mockEmployeeId)
                .firstName("OldName")
                .lastName("OldSurname")
                .identityNumber("12345678901")
                .email("old@example.com")
                .phoneNumber("05551231212")
                .address("Old Address")
                .birthDate(LocalDate.of(1985, 1, 1))
                .gender(Gender.MALE)
                .nationality("OldCountry")
                .build();

        //When
        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(mockEmployee));
        Mockito.when(employeeReadPort.existsByIdentity("99999999999"))
                .thenReturn(false);
        Mockito.when(employeeReadPort.existsByPhoneNumber("05559998877"))
                .thenReturn(false);

        //Then
        employeeWriteServiceImpl.update(mockEmployeeId, request);

        Assertions.assertEquals("UpdatedName", mockEmployee.getFirstName());
        Assertions.assertEquals("UpdatedSurname", mockEmployee.getLastName());
        Assertions.assertEquals("99999999999", mockEmployee.getIdentityNumber());
        Assertions.assertEquals("updated@example.com", mockEmployee.getEmail());
        Assertions.assertEquals("05559998877", mockEmployee.getPhoneNumber());
        Assertions.assertEquals("Updated Address", mockEmployee.getAddress());
        Assertions.assertEquals(LocalDate.of(1990, 1, 1), mockEmployee.getBirthDate());
        Assertions.assertEquals(Gender.FEMALE, mockEmployee.getGender());
        Assertions.assertEquals("UpdatedCountry", mockEmployee.getNationality());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByIdentity(Mockito.anyString());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByPhoneNumber(Mockito.anyString());
        Mockito.verify(employeeSavePort, Mockito.times(1))
                .save(mockEmployee);
    }

    @Test
    void givenInvalidEmployeeId_whenUpdateEmployee_thenThrowEmployeeNotFoundException() {

        //Given
        Long mockEmployeeId = 999L;
        EmployeeUpdateRequest request = EmployeeUpdateRequest.builder()
                .identityNumber("12345678901")
                .phoneNumber("05551231212")
                .build();

        //When
        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeeWriteServiceImpl.update(mockEmployeeId, request));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(mockEmployeeId);
        Mockito.verify(employeeReadPort, Mockito.never())
                .existsByIdentity(Mockito.anyString());
        Mockito.verify(employeeReadPort, Mockito.never())
                .existsByPhoneNumber(Mockito.anyString());
        Mockito.verify(employeeSavePort, Mockito.never())
                .save(Mockito.any(Employee.class));
    }

    @Test
    void givenSameIdentityNumberExists_whenUpdateEmployee_thenThrowEmployeeAlreadyExistsException() {

        //Given
        Long mockEmployeeId = 1L;

        EmployeeUpdateRequest request = EmployeeUpdateRequest.builder()
                .identityNumber("99999999999")
                .phoneNumber("05551231212")
                .build();

        Employee existingEmployee = Employee.builder()
                .id(mockEmployeeId)
                .identityNumber("12345678901")
                .phoneNumber("05551231212")
                .build();

        //When
        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(existingEmployee));
        Mockito.when(employeeReadPort.existsByIdentity("99999999999"))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(EmployeeAlreadyExistsException.class,
                () -> employeeWriteServiceImpl.update(mockEmployeeId, request));

        //verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByIdentity(Mockito.anyString());
        Mockito.verify(employeeReadPort, Mockito.never())
                .existsByPhoneNumber(Mockito.anyString());
        Mockito.verify(employeeSavePort, Mockito.never())
                .save(Mockito.any(Employee.class));
    }

    @Test
    void givenSamePhoneNumberExists_whenUpdateEmployee_thenThrowEmployeeAlreadyExistsException() {

        //Given
        Long mockEmployeeId = 1L;

        EmployeeUpdateRequest request = EmployeeUpdateRequest.builder()
                .identityNumber("12345678902")
                .phoneNumber("05559998877")
                .build();

        Employee existingEmployee = Employee.builder()
                .id(mockEmployeeId)
                .identityNumber("12345678901")
                .phoneNumber("05551231212")
                .build();

        //When
        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(existingEmployee));
        Mockito.when(employeeReadPort.existsByIdentity(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(employeeReadPort.existsByPhoneNumber(Mockito.anyString()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(EmployeeAlreadyExistsException.class,
                () -> employeeWriteServiceImpl.update(mockEmployeeId, request));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByIdentity(Mockito.anyString());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByPhoneNumber(Mockito.anyString());
        Mockito.verify(employeeSavePort, Mockito.never())
                .save(Mockito.any(Employee.class));
    }

    @Test
    void givenValidIdAndIdenticalUpdateRequest_whenUpdateEmployee_thenEmployeeSaved() {

        //Given
        Long mockEmployeeId = 1L;

        EmployeeUpdateRequest request = EmployeeUpdateRequest.builder()
                .firstName("OldName")
                .lastName("OldSurname")
                .identityNumber("12345678901")
                .email("old@example.com")
                .phoneNumber("05551231212")
                .address("Old Address")
                .birthDate(LocalDate.of(1985, 1, 1))
                .gender(Gender.MALE)
                .nationality("OldCountry")
                .build();

        Employee mockEmployee = Employee.builder()
                .id(mockEmployeeId)
                .firstName("OldName")
                .lastName("OldSurname")
                .identityNumber("12345678901")
                .email("old@example.com")
                .phoneNumber("05551231212")
                .address("Old Address")
                .birthDate(LocalDate.of(1985, 1, 1))
                .gender(Gender.MALE)
                .nationality("OldCountry")
                .build();

        //When
        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(mockEmployee));
        Mockito.when(employeeSavePort.save(mockEmployee))
                .thenReturn(Optional.of(mockEmployee));

        //Then
        employeeWriteServiceImpl.update(mockEmployeeId, request);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(mockEmployeeId);
        Mockito.verify(employeeReadPort, Mockito.never())
                .existsByIdentity(Mockito.anyString());
        Mockito.verify(employeeReadPort, Mockito.never())
                .existsByPhoneNumber(Mockito.anyString());
        Mockito.verify(employeeSavePort, Mockito.times(1))
                .save(Mockito.any(Employee.class));
    }

    /**
     * {@link EmployeeWriteServiceImpl#delete(Long)}
     */
    @Test
    void givenEmployeeId_whenDeleteEmployeeCalled_thenSaveEmployeeAndEmployeeExperiencesToEmployeeOldSuccess() {

        //Given
        Long mockId = 101L;

        Employee employee = getEmployee();

        EmployeeOld employeeOldSaved = getSavedEmployeeOld();

        EmployeeExperience experience1 = getEmployeeExperience();
        EmployeeExperience experience2 = getEmployeeExperience2();

        List<EmployeeExperience> experiences = List.of(experience1, experience2);

        EmployeeOldExperience oldExperience1 = getEmployeeOldExperience();
        EmployeeOldExperience oldExperience2 = getEmployeeOldExperience2();

        List<EmployeeOldExperience> oldExperiences = List.of(oldExperience1, oldExperience2);

        //When
        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(employee));

        EmployeeOld employeeOld = employeeToEmployeeOldMapper.map(employee);

        Mockito.when(employeeOldSavePort.save(employeeOld))
                .thenReturn(Optional.of(employeeOldSaved));

        Mockito.when(employeeExperienceReadPort.findAllByEmployeeId(mockId))
                .thenReturn(experiences);

        List<EmployeeOldExperience>
                employeeOldExperiences = employeeExperienceToEmployeeOldExperienceMapper
                .map(experiences);

        Mockito.when(employeeOldExperienceSavePort.saveAll(employeeOldExperiences))
                .thenReturn(oldExperiences);

        Mockito.doNothing().when(employeeExperienceDeletePort)
                .deleteAllByEmployeeId(mockId);

        Mockito.doNothing().when(employeeDeletePort).delete(mockId);

        //Then
        employeeWriteServiceImpl.delete(mockId);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.times(1))
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldExperienceSavePort, Mockito.times(1))
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.times(1))
                .deleteAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeDeletePort, Mockito.times(1))
                .delete(Mockito.anyLong());
    }

    @Test
    void givenEmployeeId_whenDeleteCalledWithEmptyExperienceList_thenSucceed() {

        //Given
        Long mockId = 101L;

        Employee employee = getEmployee();
        EmployeeOld employeeOldSaved = getSavedEmployeeOld();

        //When
        List<EmployeeExperience> emptyExperiences = Collections.emptyList();

        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(employee));

        EmployeeOld employeeOld = employeeToEmployeeOldMapper.map(employee);

        Mockito.when(employeeOldSavePort.save(employeeOld))
                .thenReturn(Optional.of(employeeOldSaved));

        Mockito.when(employeeExperienceReadPort.findAllByEmployeeId(mockId))
                .thenReturn(emptyExperiences);

        Mockito.doNothing().when(employeeDeletePort).delete(mockId);

        //Then
        employeeWriteServiceImpl.delete(mockId);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.times(1))
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldExperienceSavePort, Mockito.never())
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.never())
                .deleteAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeDeletePort, Mockito.times(1))
                .delete(Mockito.anyLong());
    }

    @Test
    void givenNonExistingEmployeeId_whenDeleteCalled_thenThrowsEmployeeNotFoundException() {

        //Given
        Long mockId = 999L;

        //When
        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.empty());
        //Then
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> employeeWriteServiceImpl.delete(mockId));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.never())
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldExperienceSavePort, Mockito.never())
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.never())
                .deleteAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeDeletePort, Mockito.never())
                .delete(Mockito.anyLong());
    }

    @Test
    void givenEmployeeId_whenSaveEmployeeOldFails_thenThrowsRuntimeException() {

        //Given
        Long mockId = 101L;

        Employee employee = getEmployee();
        EmployeeOld employeeOld = employeeToEmployeeOldMapper.map(employee);

        //When
        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(employee));

        Mockito.when(employeeOldSavePort.save(employeeOld))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(RuntimeException.class,
                () -> employeeWriteServiceImpl.delete(mockId)
        );

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.times(1))
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldExperienceSavePort, Mockito.never())
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.never())
                .deleteAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeDeletePort, Mockito.never())
                .delete(Mockito.anyLong());
    }

    @Test
    void givenEmployeeExperienceWithNullEndDate_whenDeleteCalled_thenEndDateIsSetAndSucceeds() {

        //Given
        Long mockId = 101L;

        Employee employee = getEmployee();
        EmployeeOld employeeOldSaved = getSavedEmployeeOld();

        EmployeeExperience experienceWithNullEndDate = getEmployeeExperience();
        experienceWithNullEndDate.setEndDate(null);

        List<EmployeeExperience> experiences = List.of(experienceWithNullEndDate);

        //When
        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(employee));
        Mockito.when(employeeOldSavePort.save(Mockito.any(EmployeeOld.class)))
                .thenReturn(Optional.of(employeeOldSaved));
        Mockito.when(employeeExperienceReadPort.findAllByEmployeeId(mockId))
                .thenReturn(experiences);

        List<EmployeeOldExperience>
                employeeOldExperiences = employeeExperienceToEmployeeOldExperienceMapper
                .map(experiences);

        Mockito.when(employeeOldExperienceSavePort.saveAll(employeeOldExperiences))
                .thenReturn(employeeOldExperiences);

        Mockito.doNothing().when(employeeExperienceDeletePort)
                .deleteAllByEmployeeId(mockId);

        Mockito.doNothing().when(employeeDeletePort).delete(mockId);

        //Then
        employeeWriteServiceImpl.delete(mockId);

        // Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.times(1))
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldExperienceSavePort, Mockito.times(1))
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.times(1))
                .deleteAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeDeletePort, Mockito.times(1))
                .delete(Mockito.anyLong());
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

    private static EmployeeExperience getEmployeeExperience() {

        Position position = getPosition();

        Employee employee = getEmployee();

        Employee supervisor = getSupervisor();

        return EmployeeExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(65000))
                .startDate(LocalDate.of(2020, 1, 15))
                .endDate(LocalDate.of(2023, 1, 28))
                .position(position)
                .employee(employee)
                .supervisor(supervisor)
                .build();
    }

    private static EmployeeExperience getEmployeeExperience2() {

        Position position = getPosition2();

        Employee employee = getEmployee();

        Employee supervisor = getSupervisor();

        return EmployeeExperience.builder()
                .id(2L)
                .salary(BigDecimal.valueOf(85000))
                .startDate(LocalDate.of(2023, 2, 15))
                .endDate(LocalDate.of(2024, 1, 28))
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

    private static EmployeeOld getEmployeeOld() {

        return EmployeeOld.builder()
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

    private static EmployeeOld getSavedEmployeeOld() {

        return EmployeeOld.builder()
                .id(102L)
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


    private static EmployeeOldExperience getEmployeeOldExperience() {

        Position position = getPosition();

        EmployeeOld employee = getEmployeeOld();

        Employee supervisor = getSupervisor();

        return EmployeeOldExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(65000))
                .startDate(LocalDate.of(2020, 1, 15))
                .endDate(LocalDate.of(2023, 1, 28))
                .position(position)
                .employeeOld(employee)
                .supervisor(supervisor)
                .build();
    }

    private static EmployeeOldExperience getEmployeeOldExperience2() {

        Position position = getPosition2();

        EmployeeOld employee = getEmployeeOld();

        Employee supervisor = getSupervisor();

        return EmployeeOldExperience.builder()
                .id(2L)
                .salary(BigDecimal.valueOf(85000))
                .startDate(LocalDate.of(2023, 2, 15))
                .endDate(LocalDate.of(2024, 1, 28))
                .position(position)
                .employeeOld(employee)
                .supervisor(supervisor)
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
