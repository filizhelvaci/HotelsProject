package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.DepartmentAlreadyExistsException;
import com.flz.model.Department;
import com.flz.model.mapper.DepartmentCreateRequestToDomainMapper;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentSavePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

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
        Assertions.assertThrows(DepartmentAlreadyExistsException.class,
                () -> departmentCreateService.create(mockDepartmentCreateRequest));

        // Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .existsByName(mockDepartmentCreateRequest.getName());
        Mockito.verify(departmentSavePort, Mockito.never())
                .save(Mockito.any(Department.class));

    }
}