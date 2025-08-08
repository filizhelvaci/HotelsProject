package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.DepartmentAlreadyDeletedException;
import com.flz.exception.DepartmentAlreadyExistsException;
import com.flz.exception.DepartmentNotFoundException;
import com.flz.model.Department;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.mapper.DepartmentCreateRequestToDomainMapper;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

class DepartmentWriteServiceImplTest extends BaseTest {

    @Mock
    DepartmentSavePort departmentSavePort;

    @Mock
    DepartmentReadPort departmentReadPort;

    @InjectMocks
    DepartmentWriteServiceImpl departmentWriteService;


    /**
     * {@link DepartmentWriteServiceImpl#create(DepartmentCreateRequest)}
     */
    @Test
    void givenDepartmentCreateRequest_whenCreateRequestNameIsNotInDatabase_thenCreateDepartment() {

        //Given
        DepartmentCreateRequest mockDepartmentCreateRequest = new DepartmentCreateRequest();
        mockDepartmentCreateRequest.setName("test");

        //When
        Mockito.when(departmentReadPort.existsByName(mockDepartmentCreateRequest.getName()))
                .thenReturn(false);

        Department mockDepartment = DepartmentCreateRequestToDomainMapper.INSTANCE
                .map(mockDepartmentCreateRequest);

        Mockito.doNothing().when(departmentSavePort).save(mockDepartment);

        //Then
        departmentWriteService.create(mockDepartmentCreateRequest);

        //Verify
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(Mockito.any(Department.class));

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
                .existsByName(mockDepartmentCreateRequest.getName());
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any(Department.class));

    }


    /**
     * {@link DepartmentWriteServiceImpl#update(Long, DepartmentUpdateRequest)}
     */
    @Test
    void givenValidDepartmentIdAndDepartmentUpdateRequest_whenDepartmentExists_thenUpdateDepartment() {

        //Given
        Long mockId = 1L;

        Department mockDepartment = Department.builder()
                .id(mockId)
                .name("Department")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        DepartmentUpdateRequest mockDepartmentUpdateRequest = new DepartmentUpdateRequest();
        mockDepartmentUpdateRequest.setName("updatedDepartment");

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.doNothing().when(departmentSavePort)
                .save(Mockito.any(Department.class));

        //Then
        departmentWriteService.update(mockId, mockDepartmentUpdateRequest);

        Assertions.assertEquals("updatedDepartment", mockDepartment.getName());
        Assertions.assertEquals("SYSTEM", mockDepartment.getUpdatedBy());
        Assertions.assertNotNull(mockDepartment.getUpdatedAt());

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(mockDepartmentUpdateRequest.getName());
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(Mockito.any(Department.class));

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
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any());

    }


    @Test
    void givenValidDepartmentIdAndSameName_whenDepartmentExists_thenUpdateWithoutNameCheck() {

        //Given
        Long mockId = 2L;

        Department mockDepartment = Department.builder()
                .id(mockId)
                .name("SameName")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .build();

        DepartmentUpdateRequest mockRequest = new DepartmentUpdateRequest();
        mockRequest.setName("SameName");

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));
        Mockito.doNothing().when(departmentSavePort)
                .save(Mockito.any());

        //Then
        departmentWriteService.update(mockId, mockRequest);

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(departmentReadPort, Mockito.never())
                .existsByName(Mockito.any());
        Mockito.verify(departmentSavePort)
                .save(Mockito.any());

    }


    @Test
    void givenValidDepartmentIdAndDepartmentUpdateRequest_whenDepartmentEntityAlreadyExists_thenThrowsDepartmentAlreadyExistsException() {

        //Given
        Long mockId = 1L;

        Department mockDepartment = Department.builder()
                .id(mockId)
                .name("SameName")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .build();

        DepartmentUpdateRequest mockRequest = new DepartmentUpdateRequest();
        mockRequest.setName("DifferentName");

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));
        Mockito.when(departmentReadPort.existsByName(mockRequest.getName()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(DepartmentAlreadyExistsException.class,
                () -> departmentWriteService.update(mockId, mockRequest));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(mockRequest.getName());
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any());

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
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        Department mockDeletedDepartment = Department.builder()
                .id(mockDepartment.getId())
                .name(mockDepartment.getName())
                .status(DepartmentStatus.DELETED)
                .createdAt(mockDepartment.getCreatedAt())
                .createdBy(mockDepartment.getCreatedBy())
                .build();

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.doNothing().when(departmentSavePort)
                .save(mockDeletedDepartment);

        //Then
        departmentWriteService.delete(mockId);

        Assertions.assertEquals(DepartmentStatus.DELETED, mockDeletedDepartment.getStatus());

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(Mockito.any(Department.class));

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

}
