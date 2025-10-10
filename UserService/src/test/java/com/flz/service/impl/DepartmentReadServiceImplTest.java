package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.mapper.DepartmentToDepartmentSummaryResponseMapper;
import com.flz.model.response.DepartmentSummaryResponse;
import com.flz.port.DepartmentReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
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
    void whenCalledAllSummaryDepartment_thenReturnListOfDepartmentsSummaryResponse() {

        //Initialize
        Employee manager = Employee.builder()
                .id(11L)
                .firstName("Jonny")
                .lastName("Deep")
                .identityNumber("999896314785")
                .email("jonnyd@example.com")
                .phoneNumber("05059966565")
                .address("Malatya")
                .birthDate(LocalDate.of(1985, 1, 15))
                .gender(Gender.MALE)
                .nationality("USA")
                .build();

        Employee manage2 = Employee.builder()
                .id(12L)
                .firstName("George")
                .lastName("Colony")
                .identityNumber("888877314785")
                .email("george@example.com")
                .phoneNumber("05329911565")
                .address("Konya")
                .birthDate(LocalDate.of(1983, 1, 15))
                .gender(Gender.MALE)
                .nationality("USA")
                .build();

        List<Department> mockDepartments = List.of(
                Department.builder()
                        .id(21L)
                        .name("Güvenlik")
                        .status(DepartmentStatus.ACTIVE)
                        .manager(manager)
                        .build(),
                Department.builder()
                        .id(22L)
                        .name("Bilgi Islem")
                        .status(DepartmentStatus.ACTIVE)
                        .manager(manage2)
                        .build());

        //When
        Mockito.when(departmentReadPort.findSummaryAll()).thenReturn(mockDepartments);

        List<DepartmentSummaryResponse> mockDepartmentSummaryResponses =
                DepartmentToDepartmentSummaryResponseMapper.INSTANCE.map(mockDepartments);

        //Then
        List<DepartmentSummaryResponse> result = departmentReadServiceImpl
                .findSummaryAll();

        Assertions.assertEquals(mockDepartmentSummaryResponses, result);
        Assertions.assertEquals(mockDepartments.size(), result.size());
        Assertions.assertEquals(mockDepartments.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(mockDepartments.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(mockDepartmentSummaryResponses.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(mockDepartmentSummaryResponses.get(0).getName(), result.get(0).getName());

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findSummaryAll();

    }


    @Test
    void whenCalledAllSummaryOfDepartment_thenReturnEmptyList() {

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
    void givenValidPageAndPageSize_whenCalledAllDepartment_thenReturnAllOfDepartments() {

        //Initialize
        Employee manager = Employee.builder()
                .id(11L)
                .firstName("Jonny")
                .lastName("Deep")
                .identityNumber("999896314785")
                .email("jonnyd@example.com")
                .phoneNumber("05059966565")
                .address("Malatya")
                .birthDate(LocalDate.of(1985, 1, 15))
                .gender(Gender.MALE)
                .nationality("USA")
                .build();

        Employee manage2 = Employee.builder()
                .id(12L)
                .firstName("George")
                .lastName("Colony")
                .identityNumber("888877314785")
                .email("george@example.com")
                .phoneNumber("05329911565")
                .address("Konya")
                .birthDate(LocalDate.of(1983, 1, 15))
                .gender(Gender.MALE)
                .nationality("USA")
                .build();

        List<Department> mockDepartments = List.of(
                Department.builder()
                        .id(21L)
                        .name("Güvenlik")
                        .status(DepartmentStatus.ACTIVE)
                        .manager(manager)
                        .build(),
                Department.builder()
                        .id(22L)
                        .name("Bilgi Islem")
                        .status(DepartmentStatus.ACTIVE)
                        .manager(manage2)
                        .build());
        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        Mockito.when(departmentReadPort.findAll(mockPage, mockPageSize))
                .thenReturn(mockDepartments);

        //Then
        List<Department> result = departmentReadServiceImpl
                .findAll(mockPage, mockPageSize);

        Assertions.assertEquals(mockDepartments.size(), result.size());
        Assertions.assertEquals(mockDepartments.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(mockDepartments.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(mockDepartments.get(0).getStatus(), result.get(0).getStatus());
        Assertions.assertEquals(mockDepartments.get(0).getManager(), result.get(0).getManager());
        Assertions.assertEquals(mockDepartments.get(1).getId(), result.get(1).getId());
        Assertions.assertEquals(mockDepartments.get(1).getName(), result.get(1).getName());
        Assertions.assertEquals(mockDepartments.get(1).getStatus(), result.get(1).getStatus());
        Assertions.assertEquals(mockDepartments.get(1).getManager(), result.get(1).getManager());

        //Verify
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());

    }


    @Test
    void givenValidPageAndPageSize_whenCalledAllDepartment_thenReturnEmptyList() {

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
