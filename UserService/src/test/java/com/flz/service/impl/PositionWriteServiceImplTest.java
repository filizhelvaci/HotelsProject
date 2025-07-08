package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.PositionAlreadyExistsException;
import com.flz.model.Department;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.PositionCreateRequestToPositionDomainMapper;
import com.flz.model.request.PositionCreateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.PositionReadPort;
import com.flz.port.PositionSavePort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class PositionWriteServiceImplTest extends BaseTest {

    @InjectMocks
    PositionWriteServiceImpl positionWriteServiceImpl;

    @Mock
    PositionReadPort positionReadPort;

    @Mock
    PositionSavePort positionSavePort;

    @Mock
    DepartmentReadPort departmentReadPort;

    /**
     * {@link PositionWriteServiceImpl#create(PositionCreateRequest)}
     */
    @Test
    public void givenPositionCreateRequest_whenCreateRequestNameIsNotInDatabase_thenCreatePosition() {

        //Given
        PositionCreateRequest mockPositionCreateRequest = new PositionCreateRequest();
        mockPositionCreateRequest.setName("test");
        mockPositionCreateRequest.setDepartmentId(1L);

        Optional<Department> mockDepartment = Optional.ofNullable(Department.builder()
                .id(1L)
                .name("test")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdUser("admin")
                .build());

        //When
        Mockito.when(positionReadPort.existsByName(mockPositionCreateRequest.getName()))
                .thenReturn(false);

        Position mockPosition = PositionCreateRequestToPositionDomainMapper.INSTANCE
                .map(mockPositionCreateRequest);

        Long departmentId = mockPositionCreateRequest.getDepartmentId();
        Mockito.when(departmentReadPort.findById(departmentId))
                .thenReturn(mockDepartment);
        ;

        mockPosition.setDepartment(mockDepartment.get());

        Mockito.doNothing()
                .when(positionSavePort)
                .save(mockPosition);

        //Then
        positionWriteServiceImpl.create(mockPositionCreateRequest);

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .existsByName(Mockito.anyString());
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(positionSavePort, Mockito.times(1))
                .save(Mockito.any(Position.class));

    }


    @Test
    public void givenPositionCreateRequest_whenCreateRequestNameAlreadyExists_thenThrowPositionAlreadyExists() {

        //Given
        PositionCreateRequest mockPositionCreateRequest = new PositionCreateRequest();
        mockPositionCreateRequest.setName("test");
        mockPositionCreateRequest.setDepartmentId(1L);

        //When
        Mockito.when(positionReadPort.existsByName(mockPositionCreateRequest.getName()))
                .thenReturn(true);

        //Then
        Assertions.assertThrows(PositionAlreadyExistsException.class,
                () -> positionWriteServiceImpl.create(mockPositionCreateRequest));

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .existsByName(mockPositionCreateRequest.getName());
        Mockito.verify(positionSavePort, Mockito.never())
                .save(Mockito.any(Position.class));

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