package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.DepartmentNotFoundException;
import com.flz.exception.PositionAlreadyDeletedException;
import com.flz.exception.PositionAlreadyExistsException;
import com.flz.exception.PositionNotFoundException;
import com.flz.model.Department;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.PositionCreateRequestToPositionDomainMapper;
import com.flz.model.request.PositionCreateRequest;
import com.flz.model.request.PositionUpdateRequest;
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
                .createdBy("admin")
                .build());

        //When
        Mockito.when(positionReadPort.existsByName(mockPositionCreateRequest.getName()))
                .thenReturn(false);

        Position mockPosition = PositionCreateRequestToPositionDomainMapper.INSTANCE
                .map(mockPositionCreateRequest);

        Long departmentId = mockPositionCreateRequest.getDepartmentId();
        Mockito.when(departmentReadPort.findById(departmentId))
                .thenReturn(mockDepartment);

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

    /**
     * {@link PositionWriteServiceImpl#update(Long, PositionUpdateRequest)}
     */
    @Test
    void givenValidIdAndValidRequest_whenUpdatePosition_thenPositionIsUpdated() {

        //Given
        Long positionId = 1L;
        Long departmentId = 2L;

        PositionUpdateRequest request = PositionUpdateRequest.builder()
                .name("Updated Name")
                .departmentId(departmentId)
                .build();

        Department mockDepartment = Department.builder()
                .id(departmentId)
                .name("Department")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        Position mockPosition = Position.builder()
                .id(positionId)
                .name("Position")
                .status(PositionStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .department(mockDepartment)
                .build();

        //When
        Mockito.when(positionReadPort.findById(positionId))
                .thenReturn(Optional.of(mockPosition));
        Mockito.when(departmentReadPort.findById(departmentId))
                .thenReturn(Optional.of(mockDepartment));

        //Then
        positionWriteServiceImpl.update(positionId, request);

        Assertions.assertEquals("Updated Name", mockPosition.getName());
        Assertions.assertEquals(mockDepartment, mockPosition.getDepartment());
        Assertions.assertEquals(PositionStatus.ACTIVE, mockPosition.getStatus());

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(positionSavePort)
                .save(mockPosition);

    }

    @Test
    void givenInvalidId_whenUpdatePosition_thenThrowPositionNotFoundException() {

        //Given
        Long mockId = 999L;

        PositionUpdateRequest request = PositionUpdateRequest.builder()
                .name("Any Name")
                .departmentId(1L)
                .build();

        //When
        Mockito.when(positionReadPort.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(PositionNotFoundException.class,
                () -> positionWriteServiceImpl.update(mockId, request));

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(positionSavePort, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void givenValidIdAndInValidDepartmentId_whenUpdatePosition_thenThrowDepartmentNotFoundException() {

        //Given
        Long mockDepartmentId = -9L;
        Long mockPositionId = 1L;

        PositionUpdateRequest request = PositionUpdateRequest.builder()
                .name("Any Name")
                .departmentId(mockDepartmentId)
                .build();

        Department mockDepartment = Department.builder()
                .id(11L)
                .name("Department")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        Position mockPosition = Position.builder()
                .id(mockPositionId)
                .name("Position")
                .status(PositionStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .department(mockDepartment)
                .build();

        //When
        Mockito.when(positionReadPort.findById(1L))
                .thenReturn(Optional.of(mockPosition));
        Mockito.when(departmentReadPort.findById(mockDepartmentId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(DepartmentNotFoundException.class,
                () -> positionWriteServiceImpl.update(mockPositionId, request));

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(mockPositionId);
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(mockDepartmentId);
        Mockito.verify(positionSavePort, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    void givenSameNameAndSameDepartmentId_whenUpdatePosition_thenThrowPositionAlreadyExistsException() {

        //Given
        Long positionId = 1L;
        Long departmentId = 2L;

        PositionUpdateRequest request = PositionUpdateRequest.builder()
                .name("SamePositionName")
                .departmentId(departmentId)
                .build();

        Department mockDepartment = Department.builder()
                .id(departmentId)
                .name("SameDepartmentName")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .build();

        Position mockPosition = Position.builder()
                .id(positionId)
                .name("SamePositionName")
                .status(PositionStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("createdUser")
                .department(mockDepartment)
                .build();

        //When
        Mockito.when(positionReadPort.findById(positionId))
                .thenReturn(Optional.of(mockPosition));
        Mockito.when(departmentReadPort.findById(departmentId))
                .thenReturn(Optional.of(mockDepartment));

        //Then
        PositionAlreadyExistsException exception = Assertions.assertThrows(PositionAlreadyExistsException.class,
                () -> positionWriteServiceImpl.update(positionId, request));

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(departmentReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(positionSavePort, Mockito.never())
                .save(Mockito.any());
    }

    /**
     * {@link PositionWriteServiceImpl#delete(Long)}
     */
    @Test
    public void givenValidId_whenPositionEntityFoundById_thenMakeStatusOfPositionEntityDeleted() {

        //Given
        Long mockPositionId = 1L;
        Long mockDepartmentId = 2L;

        //When
        Department mockDepartment = Department.builder()
                .id(mockDepartmentId)
                .name("TestDepartment")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .build();

        Position mockPosition = Position.builder()
                .id(mockPositionId)
                .name("TestPosition")
                .status(PositionStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("ADMIN")
                .department(mockDepartment)
                .build();

        Position mockDeletedPosition = Position.builder()
                .id(mockPosition.getId())
                .name(mockPosition.getName())
                .status(PositionStatus.DELETED)
                .createdAt(mockPosition.getCreatedAt())
                .createdBy(mockPosition.getCreatedBy())
                .build();

        Mockito.when(positionReadPort.findById(mockPositionId))
                .thenReturn(Optional.of(mockPosition));

        Mockito.doNothing()
                .when(positionSavePort)
                .save(mockDeletedPosition);

        //Then
        positionWriteServiceImpl.delete(mockPositionId);

        Assertions.assertEquals(PositionStatus.DELETED, mockDeletedPosition.getStatus());


        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(mockPositionId);
        Mockito.verify(positionSavePort, Mockito.times(1))
                .save(Mockito.any(Position.class));
    }


    @Test
    public void givenValidId_whenPositionEntityNotFoundById_thenThrowsPositionNotFoundException() {

        //Given
        Long mockId = 1L;

        //When
        Mockito.when(positionReadPort.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(PositionNotFoundException.class,
                () -> positionWriteServiceImpl.delete(mockId));

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(positionSavePort, Mockito.never())
                .save(Mockito.any());
    }

    @Test
    public void givenDeletedPosition_whenDeleteCalled_thenThrowPositionAlreadyDeletedException() {

        //Given
        Long mockId = 1L;
        Long mockDepartmentId = 2L;

        Department mockDepartment = Department.builder()
                .id(mockDepartmentId)
                .name("TestDepartment")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .build();

        Position mockPosition = Position.builder()
                .id(mockId)
                .name("TestPosition")
                .status(PositionStatus.DELETED)
                .createdAt(LocalDateTime.now())
                .createdBy("ADMIN")
                .department(mockDepartment)
                .build();


        //When
        Mockito.when(positionReadPort.findById(mockId))
                .thenReturn(Optional.of(mockPosition));

        //Then
        Assertions.assertThrows(PositionAlreadyDeletedException.class, () -> {
            positionWriteServiceImpl.delete(mockId);
        });

        //Verify
        Mockito.verify(positionReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(positionSavePort, Mockito.never())
                .save(Mockito.any());
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
                                .id(1L)
                                .name("Test2Department")
                                .status(DepartmentStatus.ACTIVE)
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
                                .createdAt(LocalDateTime.now())
                                .createdBy("TestSystem")
                                .build())
                        .status(PositionStatus.ACTIVE)
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .build());
    }

}