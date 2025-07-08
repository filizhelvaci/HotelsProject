package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.PositionToPositionSummaryResponseMapper;
import com.flz.model.response.PositionSummaryResponse;
import com.flz.port.PositionReadPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class PositionReadServiceImplTest extends BaseTest {


    @Mock
    PositionReadPort positionReadPort;

    @InjectMocks
    PositionReadServiceImpl positionReadServiceImpl;

    /**
     * {@link DepartmentReadServiceImpl#findSummaryAll()}
     */
    @Test
    public void whenCalledAllSummaryPosition_thenReturnListOfPositionsSummaryResponse() {

        //When
        List<Position> mockPositions = getPositions();

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
    public void whenCalledAllSummaryPositionIfAllSummaryEntitiesIsEmpty_thenReturnEmptyList() {

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

    private static List<Position> getPositions() {
        return List.of(
                Position.builder()
                        .id(11L)
                        .name("Test1")
                        .department(Department.builder()
                                .id(1L)
                                .name("Test1Department")
                                .status(DepartmentStatus.ACTIVE)
                                .createdAt(LocalDateTime.now())
                                .createdUser("TestSystem")
                                .build())
                        .status(PositionStatus.ACTIVE)
                        .createdUser("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .build(),
                Position.builder()
                        .id(12L)
                        .name("Test2")
                        .department(Department.builder()
                                .id(1L)
                                .name("Test2Department")
                                .status(DepartmentStatus.ACTIVE)
                                .createdAt(LocalDateTime.now())
                                .createdUser("TestSystem")
                                .build())
                        .status(PositionStatus.ACTIVE)
                        .createdUser("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .build(),
                Position.builder()
                        .id(13L)
                        .name("Test3")
                        .department(Department.builder()
                                .id(2L)
                                .name("Test3Department")
                                .status(DepartmentStatus.ACTIVE)
                                .createdAt(LocalDateTime.now())
                                .createdUser("TestSystem")
                                .build())
                        .status(PositionStatus.ACTIVE)
                        .createdUser("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .build());
    }

}