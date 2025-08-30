package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.PositionToPositionSummaryResponseMapper;
import com.flz.model.response.PositionSummaryResponse;
import com.flz.port.PositionReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class PositionReadServiceImplTest extends BaseTest {

    @Mock
    PositionReadPort positionReadPort;

    @InjectMocks
    PositionReadServiceImpl positionReadServiceImpl;


    /**
     * {@link PositionReadServiceImpl#findSummaryAll()}
     */
    @Test
    void whenCalledAllSummaryPosition_thenReturnListOfPositionsSummaryResponse() {

        //Initialize
        List<Position> mockPositions = getPositions();

        //When
        Mockito.when(positionReadPort.findSummaryAll())
                .thenReturn(mockPositions);

        List<PositionSummaryResponse> mockPositionSummaryResponses = PositionToPositionSummaryResponseMapper
                .INSTANCE.map(mockPositions);

        //Then
        List<PositionSummaryResponse> result = positionReadServiceImpl
                .findSummaryAll();
        Assertions.assertEquals(mockPositionSummaryResponses, result);

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findSummaryAll();

    }

    @Test
    void whenCalledAllSummaryPositionIfAllSummaryEntitiesIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(positionReadPort.findSummaryAll())
                .thenReturn(Collections.emptyList());

        //Then
        List<PositionSummaryResponse> positionSummaryResponses =
                positionReadServiceImpl.findSummaryAll();

        Assertions.assertNotNull(positionReadPort);
        Assertions.assertEquals(0, positionSummaryResponses.size());
        Assertions.assertTrue(positionSummaryResponses.isEmpty());

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findSummaryAll();

    }

    /**
     * {@link PositionReadServiceImpl#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPagePageSize_whenCalledAllPosition_thenReturnListAllOfPositions() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        List<Position> mockPositions = getPositions();

        Mockito.when(positionReadPort.findAll(mockPage, mockPageSize))
                .thenReturn(mockPositions);

        //Then
        List<Position> result = positionReadServiceImpl
                .findAll(mockPage, mockPageSize);
        Assertions.assertEquals(mockPositions.size(), result.size());

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());

    }

    @Test
    void givenValidPagePageSize_whenCalledAllPositionIfAllPositionEntitiesIsEmpty_thenReturnEmptyList() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        Mockito.when(positionReadPort.findAll(mockPage, mockPageSize))
                .thenReturn(Collections.emptyList());

        //Then
        List<Position> result = positionReadServiceImpl
                .findAll(mockPage, mockPageSize);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
        Assertions.assertTrue(result.isEmpty());

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findAll(mockPage, mockPageSize);

    }

    private static List<Position> getPositions() {
        return List.of(
                Position.builder()
                        .id(11L)
                        .name("Test1")
                        .department(Department.builder()
                                .id(1L)
                                .name("Test1Department")
                                .status(DepartmentStatus.ACTIVE)
                                .manager(Employee.builder()
                                        .id(127L)
                                        .firstName("Jessica")
                                        .lastName("Dua")
                                        .identityNumber("25896311181")
                                        .email("jessica@example.com")
                                        .phoneNumber("05456511165")
                                        .address("Aksaray")
                                        .birthDate(LocalDate.of(1998, 1, 15))
                                        .gender(Gender.FEMALE)
                                        .nationality("British")
                                        .build())
                                .createdAt(LocalDateTime.now())
                                .createdBy("TestSystem")
                                .build())
                        .status(PositionStatus.ACTIVE)
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .build(),
                Position.builder()
                        .id(12L)
                        .name("Test2")
                        .department(Department.builder()
                                .id(2L)
                                .name("Test2Department")
                                .status(DepartmentStatus.ACTIVE)
                                .manager(Employee.builder()
                                        .id(128L)
                                        .firstName("Johny")
                                        .lastName("Dual")
                                        .identityNumber("85876311181")
                                        .email("johnyDual@example.com")
                                        .phoneNumber("05056511111")
                                        .address("Balikesir")
                                        .birthDate(LocalDate.of(1995, 1, 15))
                                        .gender(Gender.MALE)
                                        .nationality("Brazil")
                                        .build())
                                .createdAt(LocalDateTime.now())
                                .createdBy("TestSystem")
                                .build())
                        .status(PositionStatus.ACTIVE)
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .build(),
                Position.builder()
                        .id(13L)
                        .name("Test3")
                        .department(Department.builder()
                                .id(2L)
                                .name("Test3Department")
                                .status(DepartmentStatus.ACTIVE)
                                .manager(Employee.builder()
                                        .id(129L)
                                        .firstName("Billy")
                                        .lastName("Eilish")
                                        .identityNumber("11116311181")
                                        .email("billyeilish@example.com")
                                        .phoneNumber("05323331165")
                                        .address("Konya")
                                        .birthDate(LocalDate.of(1987, 7, 15))
                                        .gender(Gender.FEMALE)
                                        .nationality("Norway")
                                        .build())
                                .createdAt(LocalDateTime.now())
                                .createdBy("TestSystem")
                                .build())
                        .status(PositionStatus.ACTIVE)
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .build());
    }

}
