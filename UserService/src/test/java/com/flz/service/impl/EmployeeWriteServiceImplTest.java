package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.EmployeeAlreadyExistsException;
import com.flz.exception.EmployeeAlreadyManagerException;
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
import com.flz.port.DepartmentReadPort;
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
    DepartmentReadPort departmentReadPort;

    @Mock
    EmployeeExperienceSavePort employeeExperienceSavePort;

    @Mock
    EmployeeExperienceReadPort employeeExperienceReadPort;

    @Mock
    EmployeeExperienceDeletePort employeeExperienceDeletePort;

    @Mock
    EmployeeOldExperienceSavePort employeeOldExperienceSavePort;

    @InjectMocks
    EmployeeWriteServiceImpl employeeWriteServiceImpl;

    EmployeeToEmployeeOldMapper
            employeeToEmployeeOldMapper = EmployeeToEmployeeOldMapper.INSTANCE;

    EmployeeExperienceToEmployeeOldExperienceMapper
            employeeExperienceToEmployeeOldExperienceMapper = EmployeeExperienceToEmployeeOldExperienceMapper.INSTANCE;


    /**
     * {@link EmployeeWriteServiceImpl#create(EmployeeCreateRequest)}
     */
    @Test
    void givenValidCreateRequest_whenIdentityNumberThereIsNot_thenCreateSuccessfully() {

        //Given
        Position mockPosition = Position.builder()
                .id(1L)
                .name("Developer")
                .department(Department.builder()
                        .id(1L)
                        .name("Stok Departmanı")
                        .manager(getManager())
                        .build())
                .build();

        EmployeeCreateRequest mockEmployeeCreateRequest = getEmployeeCreateRequest();

        //When
        Mockito.when(employeeReadPort.existsByIdentity(Mockito.anyString()))
                .thenReturn(false);

        Employee mockMappedEmployee = EmployeeCreateRequestToDomainMapper.INSTANCE
                .map(mockEmployeeCreateRequest);

        Employee mockSavedEmployee = getSavedEmployee();

        Mockito.when(employeeSavePort.save(mockMappedEmployee))
                .thenReturn(mockSavedEmployee);

        Mockito.when(positionReadPort.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(mockPosition));

        Mockito.doNothing().when(employeeExperienceSavePort)
                .save(Mockito.any());

        //Then
        employeeWriteServiceImpl.create(mockEmployeeCreateRequest);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByIdentity(Mockito.anyString());
        Mockito.verify(employeeSavePort, Mockito.times(1))
                .save(Mockito.any(Employee.class));
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeExperienceSavePort, Mockito.times(1))
                .save(Mockito.any(EmployeeExperience.class));

    }

    @Test
    void givenCreateRequest_whenPositionIdThereIsNot_thenThrowsPositionNotFoundException() {

        //Given
        EmployeeCreateRequest request = getEmployeeCreateRequest();

        //When
        Mockito.when(employeeReadPort.existsByIdentity(Mockito.anyString()))
                .thenReturn(false);

        Employee mappedEmployee = EmployeeCreateRequestToDomainMapper.INSTANCE.map(request);

        Employee mockSavedEmployee = getSavedEmployee();

        Mockito.when(employeeSavePort.save(mappedEmployee))
                .thenReturn(mockSavedEmployee);

        Mockito.when(positionReadPort.findById(Mockito.anyLong()))
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
        Mockito.verify(employeeExperienceSavePort, Mockito.never())
                .save(Mockito.any(EmployeeExperience.class));

    }

    @Test
    void givenCreateRequest_whenIdentityNumberThereIs_thenThrowsEmployeeAlreadyExistsException() {

        //Given
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setIdentityNumber("12345678901");

        //When
        Mockito.when(employeeReadPort.existsByIdentity(Mockito.anyString()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(EmployeeAlreadyExistsException.class,
                () -> employeeWriteServiceImpl.create(request));

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .existsByIdentity("12345678901");
        Mockito.verify(employeeSavePort, Mockito.never())
                .save(Mockito.any(Employee.class));
        Mockito.verify(positionReadPort, Mockito.never())
                .findById(Mockito.anyLong());
        Mockito.verify(employeeExperienceSavePort, Mockito.never())
                .save(Mockito.any(EmployeeExperience.class));

    }

    /**
     * {@link EmployeeWriteServiceImpl#update(Long, EmployeeUpdateRequest)}
     */
    @Test
    void givenValidIdAndUpdateRequest_whenEmployeeThereIsNot_thenUpdateSuccessfully() {

        //Given
        Long mockEmployeeId = 1L;

        //Initialize
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

        Employee mockSavedEmployee = Employee.builder()
                .id(mockEmployeeId)
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

        //When
        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(mockEmployee));
        Mockito.when(employeeReadPort.existsByIdentity(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(employeeReadPort.existsByPhoneNumber(Mockito.anyString()))
                .thenReturn(false);
        Mockito.when(employeeSavePort.save(Mockito.any(Employee.class)))
                .thenReturn(mockSavedEmployee);

        //Then
        employeeWriteServiceImpl.update(mockEmployeeId, request);

        Assertions.assertEquals(request.getFirstName(), mockSavedEmployee.getFirstName());
        Assertions.assertEquals(request.getLastName(), mockSavedEmployee.getLastName());
        Assertions.assertEquals(request.getIdentityNumber(), mockSavedEmployee.getIdentityNumber());
        Assertions.assertEquals(request.getEmail(), mockSavedEmployee.getEmail());
        Assertions.assertEquals(request.getPhoneNumber(), mockSavedEmployee.getPhoneNumber());
        Assertions.assertNotNull(mockSavedEmployee.getNationality());
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
    void givenValidIdAndUpdateRequest_whenEmployeeThereIsNot_thenThrowEmployeeNotFoundException() {

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
    void givenValidIdAndUpdateRequest_whenPhoneNumberThereIs_thenThrowEmployeeAlreadyExistsException() {

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
    void givenValidIdAndUpdateRequest_whenSamePhoneNumberAndIdentityNumberThereIs_thenEmployeeSaved() {

        //Given
        Long mockEmployeeId = 1L;

        //Initialize
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
                .thenReturn(mockEmployee);

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

    @Test
    void givenValidIdAndUpdateRequest_whenSameIdentityNumberExists_thenThrowEmployeeAlreadyExistsException() {

        //Given
        Long mockEmployeeId = 1L;

        EmployeeUpdateRequest request = EmployeeUpdateRequest.builder()
                .identityNumber("99999999999")
                .phoneNumber("05551231212")
                .build();

        Employee mockEmployee = Employee.builder()
                .id(mockEmployeeId)
                .identityNumber("12345678901")
                .phoneNumber("05551231212")
                .build();

        //When
        Mockito.when(employeeReadPort.findById(mockEmployeeId))
                .thenReturn(Optional.of(mockEmployee));
        Mockito.when(employeeReadPort.existsByIdentity(Mockito.anyString()))
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

    /**
     * {@link EmployeeWriteServiceImpl#delete(Long)}
     */
    @Test
    void givenId_whenEmployeeThereIsNotAnotherWhere_thenDataOfEmployeeCarriedEmployeeOldExperiencesAndEmployeeOldSuccessfullyAndDelete() {

        //Initialize
        Employee employee = getEmployee();

        EmployeeOld employeeOldSaved = getSavedEmployeeOld();

        EmployeeExperience experience1 = getEmployeeExperience();
        EmployeeExperience experience2 = getEmployeeExperience2();

        List<EmployeeExperience> experiences = List.of(experience1, experience2);

        EmployeeOldExperience oldExperience1 = getEmployeeOldExperience();
        EmployeeOldExperience oldExperience2 = getEmployeeOldExperience2();

        List<EmployeeOldExperience> oldExperiences = List.of(oldExperience1, oldExperience2);

        //Given
        Long mockId = 101L;

        //When
        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(employee));

        Mockito.when(departmentReadPort.findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(Optional.empty());

        Mockito.when(employeeExperienceReadPort.findAllByEmployeeId(mockId))
                .thenReturn(experiences);

        EmployeeOld employeeOld = employeeToEmployeeOldMapper.map(employee);

        Mockito.when(employeeOldSavePort.save(employeeOld))
                .thenReturn(employeeOldSaved);

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
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.times(1))
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeOldExperienceSavePort, Mockito.times(1))
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.times(1))
                .deleteAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeDeletePort, Mockito.times(1))
                .delete(Mockito.anyLong());

    }

    @Test
    void givenId_whenEmployeeExperiencesThereAreNot_thenDeleteEmployeeWithEmptyExperienceList() {

        //Given
        Long mockId = 101L;

        Employee employee = getEmployee();
        EmployeeOld employeeOldSaved = getSavedEmployeeOld();

        //When
        List<EmployeeExperience> emptyExperiences = Collections.emptyList();

        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(employee));

        Mockito.when(departmentReadPort
                        .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(Optional.empty());

        Mockito.when(employeeExperienceReadPort.findAllByEmployeeId(mockId))
                .thenReturn(emptyExperiences);

        EmployeeOld employeeOld = employeeToEmployeeOldMapper.map(employee);

        Mockito.when(employeeOldSavePort.save(employeeOld))
                .thenReturn(employeeOldSaved);

        Mockito.doNothing().when(employeeDeletePort).delete(mockId);

        //Then
        employeeWriteServiceImpl.delete(mockId);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.times(1))
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeDeletePort, Mockito.times(1))
                .delete(Mockito.anyLong());
        Mockito.verify(employeeOldExperienceSavePort, Mockito.never())
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.never())
                .deleteAllByEmployeeId(Mockito.anyLong());


    }

    @Test
    void givenId_whenEmployeeThereIsNot_thenThrowsEmployeeNotFoundException() {

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
        Mockito.verify(departmentReadPort, Mockito.never())
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.never())
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeOldExperienceSavePort, Mockito.never())
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.never())
                .deleteAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeDeletePort, Mockito.never())
                .delete(Mockito.anyLong());

    }

    @Test
    void givenId_whenEmployeeExperiencesEndDateWithNull_thenEndDateIsSetAndDeleteEmployeeSuccessfully() {

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
        Mockito.when(departmentReadPort.findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(Optional.empty());
        Mockito.when(employeeExperienceReadPort.findAllByEmployeeId(mockId))
                .thenReturn(experiences);
        Mockito.when(employeeOldSavePort.save(Mockito.any(EmployeeOld.class)))
                .thenReturn(employeeOldSaved);

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
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.times(1))
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeOldExperienceSavePort, Mockito.times(1))
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.times(1))
                .deleteAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeDeletePort, Mockito.times(1))
                .delete(Mockito.anyLong());

    }

    @Test
    void givenId_whenManagerThereIsInActiveDepartment_thenThrowEmployeeAlreadyManagerException() {

        //Given
        Long mockId = 101L;

        Employee employee = getEmployee();
        Department department = Department.builder()
                .id(21L)
                .name("Güvenlik")
                .status(DepartmentStatus.ACTIVE)
                .manager(employee)
                .build();

        //When
        Mockito.when(employeeReadPort.findById(mockId))
                .thenReturn(Optional.of(employee));
        Mockito.when(departmentReadPort.findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(Optional.of(department));

        //Then
        Assertions.assertThrows(EmployeeAlreadyManagerException.class,
                () -> employeeWriteServiceImpl.delete(mockId));

        // Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());
        Mockito.verify(employeeExperienceReadPort, Mockito.never())
                .findAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeOldSavePort, Mockito.never())
                .save(Mockito.any(EmployeeOld.class));
        Mockito.verify(employeeOldExperienceSavePort, Mockito.never())
                .saveAll(Mockito.anyList());
        Mockito.verify(employeeExperienceDeletePort, Mockito.never())
                .deleteAllByEmployeeId(Mockito.anyLong());
        Mockito.verify(employeeDeletePort, Mockito.never())
                .delete(Mockito.anyLong());

    }

    private static EmployeeCreateRequest getEmployeeCreateRequest() {
        return EmployeeCreateRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .identityNumber("12345678901")
                .phoneNumber("05468529674")
                .address("Ankara")
                .gender(Gender.MALE)
                .nationality("USA")
                .salary(BigDecimal.valueOf(25000))
                .positionId(1L)
                .departmentId(5L)
                .startDate(LocalDate.now().plusDays(3))
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

        return EmployeeExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(65000))
                .startDate(LocalDate.of(2020, 1, 15))
                .endDate(LocalDate.of(2023, 1, 28))
                .position(position)
                .employee(employee)
                .build();
    }

    private static EmployeeExperience getEmployeeExperience2() {

        Position position = getPosition2();
        Employee employee = getEmployee();

        return EmployeeExperience.builder()
                .id(2L)
                .salary(BigDecimal.valueOf(85000))
                .startDate(LocalDate.of(2023, 2, 15))
                .endDate(LocalDate.of(2024, 1, 28))
                .position(position)
                .employee(employee)
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

        return EmployeeOldExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(65000))
                .startDate(LocalDate.of(2020, 1, 15))
                .endDate(LocalDate.of(2023, 1, 28))
                .position(position)
                .employeeOld(employee)
                .build();
    }

    private static EmployeeOldExperience getEmployeeOldExperience2() {

        Position position = getPosition2();
        EmployeeOld employee = getEmployeeOld();

        return EmployeeOldExperience.builder()
                .id(2L)
                .salary(BigDecimal.valueOf(85000))
                .startDate(LocalDate.of(2023, 2, 15))
                .endDate(LocalDate.of(2024, 1, 28))
                .position(position)
                .employeeOld(employee)
                .build();
    }

    private static Employee getManager() {

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
                        .manager(getManager())
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
                        .manager(getManager())
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
