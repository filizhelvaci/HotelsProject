package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.DepartmentAlreadyDeletedException;
import com.flz.exception.DepartmentAlreadyExistsException;
import com.flz.exception.DepartmentNotFoundException;
import com.flz.exception.EmployeeAlreadyManagerException;
import com.flz.exception.EmployeeNotFoundException;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.mapper.DepartmentCreateRequestToDomainMapper;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
import com.flz.port.EmployeeReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

class DepartmentWriteServiceImplTest extends BaseTest {

    @Mock
    DepartmentSavePort departmentSavePort;

    @Mock
    DepartmentReadPort departmentReadPort;

    @Mock
    EmployeeReadPort employeeReadPort;

    @InjectMocks
    DepartmentWriteServiceImpl departmentWriteService;


    /**
     * {@link DepartmentWriteServiceImpl#create(DepartmentCreateRequest)}
     */
    @Test
    void givenDepartmentCreateRequest_whenCreateRequestNameIsNotInDatabase_thenCreateDepartment() {

        //Given
        DepartmentCreateRequest mockDepartmentCreateRequest = DepartmentCreateRequest.builder()
                .name("test")
                .managerId(getEmployee().getId())
                .build();

        Optional<Employee> mockManager = Optional.of(getEmployee());

        //When
        Mockito.when(departmentReadPort.existsByName(mockDepartmentCreateRequest.getName()))
                .thenReturn(false);

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(mockManager);

        Mockito.when(departmentReadPort.findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(Optional.empty());

        Mockito.doNothing().when(departmentSavePort).save(Mockito.any());

        //Then
        departmentWriteService.create(mockDepartmentCreateRequest);


        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(Mockito.any());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());

        ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(departmentCaptor.capture());

        Department savedDepartment = departmentCaptor.getValue();

        Assertions.assertNotNull(savedDepartment);
        Assertions.assertEquals(mockDepartmentCreateRequest.getName(), savedDepartment.getName());
        Assertions.assertEquals(mockManager.get().getPhoneNumber(), savedDepartment.getManager().getPhoneNumber());

    }


    @Test
    void givenDepartmentCreateRequest_whenCreateRequestManagerExistsInDeletedDepartment_thenSuccessCreateDepartment() {

        //Initialize
        Employee manager = Employee.builder()
                .id(119L)
                .firstName("Jane")
                .lastName("Doe")
                .identityNumber("11896314785")
                .email("janedoe@example.com")
                .phoneNumber("05356566565")
                .address("Malatya")
                .birthDate(LocalDate.of(2020, 2, 15))
                .gender(Gender.FEMALE)
                .nationality("UK")
                .build();

        //Given
        DepartmentCreateRequest mockDepartmentCreateRequest = DepartmentCreateRequest.builder()
                .name("test")
                .managerId(119L)
                .build();

        Optional<Employee> mockManager = Optional.of(manager);

        //When
        Mockito.when(departmentReadPort.existsByName(mockDepartmentCreateRequest.getName()))
                .thenReturn(false);

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(mockManager);

        Mockito.when(departmentReadPort.findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(Optional.empty());

        Department mockDepartment = DepartmentCreateRequestToDomainMapper.INSTANCE
                .map(mockDepartmentCreateRequest);

        Mockito.doNothing().when(departmentSavePort).save(mockDepartment);

        //Then
        departmentWriteService.create(mockDepartmentCreateRequest);

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(Mockito.any());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());

        ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(departmentCaptor.capture());

        Department savedDepartment = departmentCaptor.getValue();

        Assertions.assertNotNull(savedDepartment);
        Assertions.assertEquals(mockDepartmentCreateRequest.getName(), savedDepartment.getName());
        Assertions.assertEquals(mockManager.get().getPhoneNumber(), savedDepartment.getManager().getPhoneNumber());


    }


    @Test
    void givenDepartmentCreateRequest_whenCreateRequestNameAlreadyExists_thenThrowDepartmentAlreadyExists() {

        //Given
        DepartmentCreateRequest mockDepartmentCreateRequest = new DepartmentCreateRequest();
        mockDepartmentCreateRequest.setName("test");

        //When
        Mockito.when(departmentReadPort.existsByName(mockDepartmentCreateRequest.getName()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(DepartmentAlreadyExistsException.class,
                () -> departmentWriteService.create(mockDepartmentCreateRequest));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(Mockito.any());
        Mockito.verify(employeeReadPort, Mockito.never())
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.never())
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any(Department.class));

    }

    @Test
    void givenDepartmentCreateRequest_whenCreateRequestManagerIdNotFound_thenThrowEmployeeNotFoundException() {

        //Given
        DepartmentCreateRequest mockDepartmentCreateRequest = DepartmentCreateRequest.builder()
                .name("test")
                .managerId(850L)
                .build();

        //When
        Mockito.when(departmentReadPort.existsByName(mockDepartmentCreateRequest.getName()))
                .thenReturn(false);

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> departmentWriteService.create(mockDepartmentCreateRequest));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(Mockito.any());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.never())
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any(Department.class));

    }

    @Test
    void givenDepartmentCreateRequest_whenCreateRequestManagerFoundInActiveDepartment_thenThrowEmployeeAlreadyManagerExistsException() {

        //Initialize
        Employee manager = Employee.builder()
                .id(119L)
                .firstName("Jane")
                .lastName("Doe")
                .identityNumber("11896314785")
                .email("janedoe@example.com")
                .phoneNumber("05356566565")
                .address("Malatya")
                .birthDate(LocalDate.of(2020, 2, 15))
                .gender(Gender.FEMALE)
                .nationality("UK")
                .build();

        Optional<Department> mockManagersDepartment = Optional.of(Department.builder()
                .id(20L)
                .name("updatedDepartment")
                .manager(manager)
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build());
        //Given
        DepartmentCreateRequest mockDepartmentCreateRequest = DepartmentCreateRequest.builder()
                .name("test")
                .managerId(119L)
                .build();

        Optional<Employee> mockManager = Optional.of(getEmployee());

        //When
        Mockito.when(departmentReadPort.existsByName(mockDepartmentCreateRequest.getName()))
                .thenReturn(false);

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(mockManager);

        Mockito.when(departmentReadPort.findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(mockManagersDepartment);

        //Then
        Assertions.assertThrows(EmployeeAlreadyManagerException.class,
                () -> departmentWriteService.create(mockDepartmentCreateRequest));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(Mockito.any());
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any(Department.class));

    }

    /**
     * {@link DepartmentWriteServiceImpl#update(Long, DepartmentUpdateRequest)}
     */
    @Test
    void givenValidDepartmentIdAndDepartmentUpdateRequest_whenUpdateDepartmentName_thenUpdateDepartment() {

        //Initialize
        Department mockDepartment = Department.builder()
                .id(1L)
                .name("Department")
                .manager(getEmployee())
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        Optional<Employee> mockManager = Optional.of(getEmployee());

        //Given
        Long mockId = 1L;

        DepartmentUpdateRequest mockDepartmentUpdateRequest = DepartmentUpdateRequest.builder()
                .name("UpdatedDepartment")
                .managerId(119L)
                .build();

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(mockManager);

        Mockito.when(departmentReadPort.existsByName(mockDepartmentUpdateRequest.getName()))
                .thenReturn(false);

        Mockito.doNothing().when(departmentSavePort)
                .save(Mockito.any(Department.class));

        // Then
        departmentWriteService.update(mockId, mockDepartmentUpdateRequest);

        // Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(departmentReadPort, Mockito.never())
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());

        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(Mockito.any());

        ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(departmentCaptor.capture());

        Department savedDepartment = departmentCaptor.getValue();

        Assertions.assertNotNull(savedDepartment);
        Assertions.assertEquals(mockDepartmentUpdateRequest.getName(), savedDepartment.getName());
        Assertions.assertEquals(mockManager.get().getPhoneNumber(), savedDepartment.getManager().getPhoneNumber());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, savedDepartment.getStatus());


    }


    @Test
    void givenValidDepartmentIdAndDepartmentUpdateRequest_whenUpdateManager_thenUpdateDepartment() {

        //Initialize
        Department mockDepartment = Department.builder()
                .id(1L)
                .name("Department")
                .manager(getEmployee())
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        Employee mockNewManager = Employee.builder()
                .id(180L)
                .firstName("Sara")
                .lastName("Sweet")
                .identityNumber("25891114785")
                .email("sara@example.com")
                .phoneNumber("05458858585")
                .address("Ankara")
                .birthDate(LocalDate.of(1982, 1, 15))
                .gender(Gender.FEMALE)
                .nationality("Switzerland")
                .build();

        //Given
        Long mockId = 1L;

        DepartmentUpdateRequest mockDepartmentUpdateRequest = DepartmentUpdateRequest.builder()
                .name("Department")
                .managerId(mockNewManager.getId())
                .build();

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(mockNewManager));

        Mockito.when(departmentReadPort.findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(Optional.empty());

        Mockito.doNothing().when(departmentSavePort)
                .save(Mockito.any(Department.class));

        // Then
        departmentWriteService.update(mockId, mockDepartmentUpdateRequest);

        // Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());

        Mockito.verify(departmentReadPort, Mockito.never())
                .existsByName(Mockito.anyString());

        ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(departmentCaptor.capture());

        Department savedDepartment = departmentCaptor.getValue();

        Assertions.assertNotNull(savedDepartment);
        Assertions.assertEquals(mockDepartmentUpdateRequest.getName(), savedDepartment.getName());
        Assertions.assertEquals(mockNewManager.getPhoneNumber(), savedDepartment.getManager().getPhoneNumber());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, savedDepartment.getStatus());

    }


    @Test
    void givenValidIdAndRequest_whenManagerIsAnotherActiveDepartmentExists_thenEmployeeAlreadyManagerExistsException() {

        //Initialize
        Employee manager = Employee.builder()
                .id(125L)
                .firstName("Jane")
                .lastName("Doe")
                .identityNumber("11896314785")
                .email("janedoe@example.com")
                .phoneNumber("05356566565")
                .address("Malatya")
                .birthDate(LocalDate.of(2020, 2, 15))
                .gender(Gender.FEMALE)
                .nationality("UK")
                .build();

        Optional<Department> mockManagersDepartment = Optional.of(Department.builder()
                .id(20L)
                .name("otherDepartment")
                .manager(manager)
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build());

        Department mockDepartment = Department.builder()
                .id(1L)
                .name("Department")
                .manager(getEmployee())
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();


        //Given
        Long mockId = 1L;

        DepartmentUpdateRequest mockDepartmentUpdateRequest = DepartmentUpdateRequest.builder()
                .name("Department")
                .managerId(125L)
                .build();

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(manager));

        Mockito.when(departmentReadPort.findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(mockManagersDepartment);


        //Then
        Assertions.assertThrows(EmployeeAlreadyManagerException.class,
                () -> departmentWriteService.update(mockId, mockDepartmentUpdateRequest));

        // Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());

        Mockito.verify(departmentReadPort, Mockito.never())
                .existsByName(Mockito.anyString());

        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any(Department.class));

    }


    @Test
    void givenValidDepartmentIdAndDepartmentUpdateRequest_whenManagerIsAnotherDeletedDepartmentExists_thenUpdateDepartment() {

        //Initialize
        Employee manager = Employee.builder()
                .id(125L)
                .firstName("Jane")
                .lastName("Doe")
                .identityNumber("11896314785")
                .email("janedoe@example.com")
                .phoneNumber("05356566565")
                .address("Malatya")
                .birthDate(LocalDate.of(2020, 2, 15))
                .gender(Gender.FEMALE)
                .nationality("UK")
                .build();

        Department mockDepartment = Department.builder()
                .id(1L)
                .name("Department")
                .manager(getEmployee())
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();


        //Given
        Long mockId = 1L;

        DepartmentUpdateRequest mockDepartmentUpdateRequest = DepartmentUpdateRequest.builder()
                .name("Department")
                .managerId(125L)
                .build();

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(manager));

        Mockito.when(departmentReadPort.findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any()))
                .thenReturn(Optional.empty());

        Mockito.doNothing().when(departmentSavePort)
                .save(Mockito.any(Department.class));

        //Then
        departmentWriteService.update(mockId, mockDepartmentUpdateRequest);

        // Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());

        Mockito.verify(departmentReadPort, Mockito.never())
                .existsByName(Mockito.anyString());

        ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(departmentCaptor.capture());

        Department savedDepartment = departmentCaptor.getValue();

        Assertions.assertNotNull(savedDepartment);
        Assertions.assertEquals(mockDepartmentUpdateRequest.getName(), savedDepartment.getName());
        Assertions.assertEquals(manager.getPhoneNumber(), savedDepartment.getManager().getPhoneNumber());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, savedDepartment.getStatus());

    }


    @Test
    void givenValidDepartmentIdAndDepartmentUpdateRequest_whenDepartmentEntityNotFoundById_thenThrowsDepartmentNotFoundException() {

        //Given
        Long mockId = 1L;

        DepartmentUpdateRequest mockDepartmentUpdateRequest = new DepartmentUpdateRequest();
        mockDepartmentUpdateRequest.setName("updatedDepartment");

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(DepartmentNotFoundException.class,
                () -> departmentWriteService.update(mockId, mockDepartmentUpdateRequest));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(employeeReadPort, Mockito.never())
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.never())
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());
        Mockito.verify(departmentReadPort, Mockito.never())
                .existsByName(Mockito.any());
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any());

    }


    @Test
    void givenValidDepartmentIdAndDepartmentUpdateRequest_whenManagerNotFound_thenEmployeeNotFoundException() {

        //Given
        Long mockId = 1L;

        Department mockDepartment = Department.builder()
                .id(mockId)
                .name("Department")
                .manager(getEmployee())
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        DepartmentUpdateRequest mockDepartmentUpdateRequest = DepartmentUpdateRequest.builder()
                .name("UpdatedDepartment")
                .managerId(180L)
                .build();

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(EmployeeNotFoundException.class,
                () -> departmentWriteService.update(mockId, mockDepartmentUpdateRequest));

        // Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(departmentReadPort, Mockito.never())
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());

        Mockito.verify(departmentReadPort, Mockito.never())
                .existsByName(Mockito.anyString());

        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any(Department.class));

    }

    @Test
    void givenValidDepartmentIdAndDepartmentUpdateRequest_whenDepartmentNameExists_thenDepartmentAlreadyExistsException() {

        //Given
        Long mockId = 1L;

        Department mockDepartment = Department.builder()
                .id(mockId)
                .name("Department")
                .manager(getEmployee())
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        Optional<Employee> mockManager = Optional.of(getEmployee());

        DepartmentUpdateRequest mockDepartmentUpdateRequest = DepartmentUpdateRequest.builder()
                .name("UpdatedDepartment")
                .managerId(119L)
                .build();

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.when(employeeReadPort.findById(Mockito.anyLong()))
                .thenReturn(mockManager);

        Mockito.when(departmentReadPort.existsByName(Mockito.anyString()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(DepartmentAlreadyExistsException.class,
                () -> departmentWriteService.update(mockId, mockDepartmentUpdateRequest));

        // Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());

        Mockito.verify(departmentReadPort, Mockito.never())
                .findByManagerIdAndStatus(Mockito.anyLong(), Mockito.any());

        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(Mockito.anyString());

        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any(Department.class));

    }

    /**
     * {@link DepartmentWriteServiceImpl#delete(Long)}
     */
    @Test
    void givenValidId_whenDepartmentEntityFoundById_thenMakeStatusOfDepartmentEntityDeleted() {

        //Given
        Long mockId = 1L;

        //Initialize
        Department mockDepartment = Department.builder()
                .id(mockId)
                .name("updatedDepartment")
                .status(DepartmentStatus.ACTIVE)
                .manager(getEmployee())
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        Department mockDeletedDepartment = Department.builder()
                .id(mockDepartment.getId())
                .name(mockDepartment.getName())
                .manager(getEmployee())
                .status(DepartmentStatus.DELETED)
                .build();

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.doNothing().when(departmentSavePort)
                .save(mockDeletedDepartment);

        //Then
        departmentWriteService.delete(mockId);


        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(departmentCaptor.capture());

        Department savedDepartment = departmentCaptor.getValue();

        Assertions.assertNotNull(savedDepartment);
        Assertions.assertEquals(DepartmentStatus.DELETED, savedDepartment.getStatus());
        Assertions.assertEquals(mockDepartment.getName(), savedDepartment.getName());
        Assertions.assertEquals(mockDepartment.getManager().getPhoneNumber(), savedDepartment.getManager().getPhoneNumber());

    }

    @Test
    void givenValidId_whenDepartmentEntityNotFoundById_thenThrowsDepartmentNotFoundException() {

        //Given
        Long mockId = 1L;

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(DepartmentNotFoundException.class,
                () -> departmentWriteService.delete(mockId));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any());

    }

    @Test
    void givenDeletedDepartment_whenDeleteCalled_thenThrowDepartmentAlreadyDeletedException() {

        //Given
        Long mockId = 1L;

        Department mockDepartment = Department.builder()
                .id(mockId)
                .name("DeletedDepartment")
                .manager(getEmployee())
                .status(DepartmentStatus.DELETED)
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .build();

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        //Then
        Assertions.assertThrows(DepartmentAlreadyDeletedException.class,
                () -> departmentWriteService.delete(mockId));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any());

    }

    private static Employee getEmployee() {

        return Employee.builder()
                .id(119L)
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

}
