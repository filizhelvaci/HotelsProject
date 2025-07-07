package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.mapper.DepartmentToDepartmentSummaryResponseMapper;
import com.flz.model.response.DepartmentSummaryResponse;
import com.flz.port.DepartmentReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DepartmentReadServiceImplTest extends BaseTest {

    @Mock
    DepartmentReadPort departmentReadPort;

    @InjectMocks
    DepartmentReadServiceImpl departmentReadServiceImpl;

    /**
     * {@link DepartmentReadServiceImpl#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryDepartment_thenReturnListOfDepartmentsSummaryResponse() {

        //When
        List<Department> mockDepartments = new ArrayList<>();

        Department mockDepartment1 = Department.builder().name("Test1").build();
        Department mockDepartment2 = Department.builder().name("Test2").build();
        mockDepartments.add(mockDepartment1);
        mockDepartments.add(mockDepartment2);


        Mockito.when(departmentReadPort.findSummaryAll()).thenReturn(mockDepartments);

        List<DepartmentSummaryResponse> mockDepartmentSummaryResponses =
                DepartmentToDepartmentSummaryResponseMapper.INSTANCE
                        .map(mockDepartments);

        //Then
        List<DepartmentSummaryResponse> result = departmentReadServiceImpl
                .findSummaryAll();
        Assertions.assertEquals(mockDepartmentSummaryResponses, result);

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findSummaryAll();
    }


    @Test
    public void whenCalledAllSummaryDepartmentIfAllSummaryEntitiesIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(departmentReadPort.findSummaryAll())
                .thenReturn(Collections.emptyList());

        //Then
        List<DepartmentSummaryResponse> departmentSummaryResponses =
                departmentReadServiceImpl.findSummaryAll();

        Assertions.assertNotNull(departmentReadPort);
        Assertions.assertEquals(0, departmentSummaryResponses.size());
        Assertions.assertTrue(departmentSummaryResponses.isEmpty());

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findSummaryAll();

    }

    /**
     * {@link DepartmentReadServiceImpl#findAll(Integer, Integer)}
     */
    @Test
    public void givenValidPagePageSize_whenCalledAllDepartment_thenReturnListAllOfDepartments() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        List<Department> mockDepartments = new ArrayList<>();

        Department mockDepartment1 = Department.builder().name("Test1").build();
        Department mockDepartment2 = Department.builder().name("Test2").build();
        mockDepartments.add(mockDepartment1);
        mockDepartments.add(mockDepartment2);


        Mockito.when(departmentReadPort.findAll(mockPage, mockPageSize))
                .thenReturn(mockDepartments);

        //Then
        List<Department> mockDepartment = departmentReadServiceImpl
                .findAll(mockPage, mockPageSize);
        Assertions.assertEquals(mockDepartments.size(), mockDepartment.size());

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());
    }


    @Test
    public void givenValidPagePageSize_whenCalledAllDepartmentIfAllDepartmentEntitiesIsEmpty_thenReturnEmptyList() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        Mockito.when(departmentReadPort.findAll(mockPage, mockPageSize))
                .thenReturn(Collections.emptyList());

        //Then
        List<Department> departments = departmentReadServiceImpl
                .findAll(mockPage, mockPageSize);

        Assertions.assertNotNull(departments);
        Assertions.assertEquals(0, departments.size());
        Assertions.assertTrue(departments.isEmpty());

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findAll(mockPage, mockPageSize);

    }


}