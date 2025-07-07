package com.flz.service.impl;

import com.flz.BaseTest;
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

class DepartmentCreateServiceImplTest extends BaseTest {


    @InjectMocks
    DepartmentCreateServiceImpl departmentCreateService;

    @Mock
    DepartmentSavePort departmentSavePort;

    @Mock
    DepartmentReadPort departmentReadPort;


    /**
     * {@link DepartmentCreateServiceImpl#create(DepartmentCreateRequest)}
     */
    @Test
    public void givenDepartmentCreateRequest_whenCreateRequestNameIsNotInDatabase_thenCreateDepartment() {

        //Given
        DepartmentCreateRequest mockDepartmentCreateRequest = new DepartmentCreateRequest();
        mockDepartmentCreateRequest.setName("test");

        //When
        Mockito.when(departmentReadPort.existsByName(mockDepartmentCreateRequest.getName())).thenReturn(false);

        Department mockDepartment = DepartmentCreateRequestToDomainMapper.INSTANCE.map(mockDepartmentCreateRequest);

        Mockito.doNothing().when(departmentSavePort).save(mockDepartment);

        // Then
        departmentCreateService.create(mockDepartmentCreateRequest);

        // Verify
        Mockito.verify(departmentSavePort, Mockito.times(1)).save(Mockito.any(Department.class));

    }


    @Test
    public void givenDepartmentCreateRequest_whenCreateRequestNameAlreadyExists_thenThrowDepartmentAlreadyExists() {

        //Given
        DepartmentCreateRequest mockDepartmentCreateRequest = new DepartmentCreateRequest();
        mockDepartmentCreateRequest.setName("test");

        //When
        Mockito.when(departmentReadPort.existsByName(mockDepartmentCreateRequest.getName())).thenReturn(true);

        // Then
        Assertions.assertThrows(DepartmentAlreadyExistsException.class, () -> departmentCreateService.create(mockDepartmentCreateRequest));

        // Verify
        Mockito.verify(departmentReadPort, Mockito.times(1)).existsByName(mockDepartmentCreateRequest.getName());
        Mockito.verify(departmentSavePort, Mockito.never()).save(Mockito.any(Department.class));

    }

    /**
     * {@link DepartmentCreateServiceImpl#update(Long, DepartmentUpdateRequest)}
     */
    @Test
    public void givenValidDepartmentIdAndDepartmentUpdateRequest_whenDepartmentEntityFoundById_thenThatDepartmentEntityUpdate() {

        //Given
        Long mockId = 1L;

        Department mockDepartment = Department.builder()
                .id(mockId)
                .name("Department")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdUser("createdUser")
                .build();

        DepartmentUpdateRequest mockDepartmentUpdateRequest = new DepartmentUpdateRequest();
        mockDepartmentUpdateRequest.setName("updatedDepartment");

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));
        Mockito.when(departmentReadPort.existsByName(mockDepartmentUpdateRequest.getName()))
                .thenReturn(false);

        mockDepartment.setName(mockDepartmentUpdateRequest.getName());
        Mockito.doNothing().when(departmentSavePort).save(mockDepartment);

        //Then
        departmentCreateService.update(mockId, mockDepartmentUpdateRequest);

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(mockDepartmentUpdateRequest.getName());
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(Mockito.any(Department.class));
    }


    @Test
    public void givenValidDepartmentIdAndDepartmentUpdateRequest_whenDepartmentEntityNotFoundById_thenThrowsDepartmentNotFoundException() {

        //Given
        Long mockId = 1L;

        DepartmentUpdateRequest mockDepartmentUpdateRequest = new DepartmentUpdateRequest();
        mockDepartmentUpdateRequest.setName("updatedDepartment");

        //When
        Mockito.when(departmentReadPort.findById(mockId)).thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(DepartmentNotFoundException.class, () -> departmentCreateService.update(mockId, mockDepartmentUpdateRequest));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1)).findById(mockId);
        Mockito.verify(departmentSavePort, Mockito.never()).save(Mockito.any());
    }


    @Test
    public void givenValidDepartmentIdAndDepartmentUpdateRequest_whenDepartmentEntityAlreadyExists_thenThrowsDepartmentAlreadyExistsException() {

        //Given
        Long mockId = 1L;

        DepartmentUpdateRequest mockDepartmentUpdateRequest = new DepartmentUpdateRequest();
        mockDepartmentUpdateRequest.setName("updatedDepartment");

        //When
        Department mockDepartment = Mockito.mock(Department.class);

        Mockito.when(departmentReadPort.findById(mockId)).thenReturn(Optional.of(mockDepartment));
        Mockito.when(departmentReadPort.existsByName(mockDepartmentUpdateRequest.getName())).thenReturn(true);

        //Then
        Assertions.assertThrows(DepartmentAlreadyExistsException.class, () -> departmentCreateService.update(mockId, mockDepartmentUpdateRequest));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1)).findById(mockId);
        Mockito.verify(departmentReadPort, Mockito.times(1)).existsByName(mockDepartmentUpdateRequest.getName());
        Mockito.verify(departmentSavePort, Mockito.never()).save(Mockito.any());
    }

    /**
     * {@link DepartmentCreateServiceImpl#delete(Long)}
     */
    @Test
    public void givenValidId_whenDepartmentEntityFoundById_thenMakeStatusOfDepartmentEntityDeleted() {

        //Given
        Long mockId = 1L;

        //When
        Department mockDepartment = Department.builder()
                .id(mockId)
                .name("updatedDepartment")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdUser("createdUser")
                .build();

        Department mockDeletedDepartment = Department.builder()
                .id(mockDepartment.getId())
                .name(mockDepartment.getName())
                .status(mockDepartment.getStatus())
                .createdAt(mockDepartment.getCreatedAt())
                .createdUser(mockDepartment.getCreatedUser())
                .build();
        mockDeletedDepartment.delete();

        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.of(mockDepartment));

        Mockito.doNothing().when(departmentSavePort)
                .save(mockDeletedDepartment);

        //Then
        departmentCreateService.delete(mockId);

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(departmentSavePort, Mockito.times(1))
                .save(Mockito.any(Department.class));
    }


    @Test
    public void givenValidId_whenDepartmentEntityNotFoundById_thenThrowsDepartmentNotFoundException() {

        //Given
        Long mockId = 1L;

        //When
        Mockito.when(departmentReadPort.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(DepartmentNotFoundException.class,
                () -> departmentCreateService.delete(mockId));

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any());
    }


}
