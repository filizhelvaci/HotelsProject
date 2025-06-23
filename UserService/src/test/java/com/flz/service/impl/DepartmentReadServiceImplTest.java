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


        Mockito.when(departmentReadPort.findSummaryAll())
                .thenReturn(mockDepartments);

        List<DepartmentSummaryResponse> departmentSummaryResponses =
                DepartmentToDepartmentSummaryResponseMapper.INSTANCE.map(mockDepartments);

        //Then
        List<DepartmentSummaryResponse> result = departmentReadServiceImpl.findSummaryAll();
        Assertions.assertEquals(departmentSummaryResponses, result);

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1)).findSummaryAll();
    }

    /**
     * {@link DepartmentReadServiceImpl#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryDepartmentIfAllSummaryEntitiesIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(departmentReadPort.findSummaryAll()).thenReturn(Collections.emptyList());

        //Then
        List<DepartmentSummaryResponse> departmentSummaryResponses = departmentReadServiceImpl.findSummaryAll();

        Assertions.assertNotNull(departmentReadPort);
        Assertions.assertEquals(0, departmentSummaryResponses.size());
        Assertions.assertTrue(departmentSummaryResponses.isEmpty());

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1)).findSummaryAll();

    }


}