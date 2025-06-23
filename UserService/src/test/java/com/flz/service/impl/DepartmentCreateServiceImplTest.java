package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.DepartmentAlreadyExistsException;
import com.flz.exception.DepartmentNotFoundException;
import com.flz.model.Department;
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

    /**
     * {@link DepartmentCreateServiceImpl#create(DepartmentCreateRequest)}
     */
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

        DepartmentUpdateRequest mockDepartmentUpdateRequest = new DepartmentUpdateRequest();
        mockDepartmentUpdateRequest.setName("updatedDepartment");

        //When
        Department mockDepartment = Mockito.mock(Department.class);

        Mockito.when(departmentReadPort.findById(mockId)).thenReturn(Optional.of(mockDepartment));
        Mockito.when(departmentReadPort.existsByName(mockDepartmentUpdateRequest.getName())).thenReturn(false);

        mockDepartment.setName(mockDepartmentUpdateRequest.getName());
        Mockito.doNothing().when(departmentSavePort).save(mockDepartment);

        //Then
        departmentCreateService.update(mockId, mockDepartmentUpdateRequest);

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1)).findById(mockId);
        Mockito.verify(departmentReadPort, Mockito.times(1)).existsByName(mockDepartmentUpdateRequest.getName());
        Mockito.verify(departmentSavePort, Mockito.times(1)).save(Mockito.any(Department.class));
    }

    /**
     * {@link DepartmentCreateServiceImpl#update(Long, DepartmentUpdateRequest)}
     */
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

    /**
     * {@link DepartmentCreateServiceImpl#update(Long, DepartmentUpdateRequest)}
     */
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

}
