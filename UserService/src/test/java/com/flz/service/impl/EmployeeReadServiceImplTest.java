package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.model.response.EmployeeSummaryResponse;
import com.flz.port.EmployeeReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

class EmployeeReadServiceImplTest extends BaseTest {

    @Mock
    EmployeeReadPort employeeReadPort;

    @InjectMocks
    EmployeeReadServiceImpl employeeReadServiceImpl;

    /**
     * {@link EmployeeReadServiceImpl#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryEmployee_thenReturnListOfEmployeesSummaryResponse() {

        //When
        List<EmployeeSummaryResponse> mockEmployeeSummaryResponse = List.of(
                EmployeeSummaryResponse.builder()
                        .id(1L)
                        .firstName("John")
                        .lastName("Doe")
                        .build(),
                EmployeeSummaryResponse.builder()
                        .id(2L)
                        .firstName("Jane")
                        .lastName("Boe")
                        .build(),
                EmployeeSummaryResponse.builder()
                        .id(3L)
                        .firstName("Bob")
                        .lastName("Joe")
                        .build()
        );

        Mockito.when(employeeReadPort.findSummaryAll())
                .thenReturn(mockEmployeeSummaryResponse);

        //Then
        List<EmployeeSummaryResponse> result = employeeReadServiceImpl
                .findSummaryAll();
        Assertions.assertEquals(mockEmployeeSummaryResponse, result);

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findSummaryAll();
    }


    @Test
    public void whenCalledAllSummaryEmployeeIfAllSummaryEntitiesIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(employeeReadPort.findSummaryAll())
                .thenReturn(Collections.emptyList());

        //Then
        List<EmployeeSummaryResponse> employeeSummaryResponses =
                employeeReadServiceImpl.findSummaryAll();

        Assertions.assertNotNull(employeeReadPort);
        Assertions.assertEquals(0, employeeSummaryResponses.size());
        Assertions.assertTrue(employeeSummaryResponses.isEmpty());

        //Verify
        Mockito.verify(employeeReadPort, Mockito.times(1))
                .findSummaryAll();

    }

}