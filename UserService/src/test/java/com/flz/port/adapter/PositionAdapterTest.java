package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.Position;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.entity.PositionEntity;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.PositionEntityToDomainMapper;
import com.flz.model.mapper.PositionToEntityMapper;
import com.flz.repository.PositionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class PositionAdapterTest extends BaseTest {

    @InjectMocks
    PositionAdapter adapter;

    @Mock
    PositionRepository positionRepository;

    private final PositionToEntityMapper positionToEntityMapper = PositionToEntityMapper.INSTANCE;
    private final PositionEntityToDomainMapper positionEntityToDomainMapper = PositionEntityToDomainMapper.INSTANCE;


    /**
     * {@link PositionAdapter#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPageAndPageSize_whenPositionFound_thenReturnListPositions() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        List<PositionEntity> mockPositionEntities = getPositionEntities();
        Pageable mockPageable = PageRequest.of(mockPage - 1, mockPageSize);

        Page<PositionEntity> mockPositionEntitiesPage = new PageImpl<>(mockPositionEntities);
        Mockito.when(positionRepository.findAll(mockPageable))
                .thenReturn(mockPositionEntitiesPage);

        //Then
        List<Position> positions = adapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(mockPositionEntities.size(), positions.size());

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .findAll(mockPageable);
    }

    @Test
    void givenValidPageAndPageSize_whenPositionNotFound_thenReturnEmptyPositions() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        Pageable mockPageable = PageRequest.of(mockPage - 1, mockPageSize);
        Mockito.when(positionRepository.findAll(mockPageable))
                .thenReturn(Page.empty());

        //Then
        List<Position> positions = adapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(0, positions.size());
        Assertions.assertEquals(0, positionRepository.count());
        Assertions.assertTrue(positions.isEmpty());

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .findAll(mockPageable);
    }

    /**
     * {@link PositionAdapter#findSummaryAll()}
     */
    @Test
    void whenCalledAllSummaryPosition_thenReturnListOfPositionSummariesResponse() {

        //When
        List<PositionEntity> mockPositionEntities = getPositionEntities();

        Mockito.when(positionRepository.findAll())
                .thenReturn(mockPositionEntities);

        //Then
        List<Position> mockPositions = positionEntityToDomainMapper
                .map(mockPositionEntities);
        List<Position> result = adapter.findSummaryAll();
        Assertions.assertEquals(mockPositions.size(), result.size());

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .findAll();
    }


    @Test
    void whenCalledAllSummaryPositionsIfPositionListIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(positionRepository.findAll())
                .thenReturn(Collections.emptyList());

        //Then
        List<Position> positions = adapter.findSummaryAll();

        Assertions.assertEquals(0, positions.size());
        Assertions.assertTrue(positions.isEmpty());

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .findAll();
    }


    private static PositionEntity getPositionEntity(Long mockId) {
        return PositionEntity.builder()
                .id(mockId)
                .name("Test")
                .department(DepartmentEntity.builder()
                        .id(1L)
                        .name("TestDepartment")
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("TestSystem")
                        .build())
                .status(PositionStatus.ACTIVE)
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .build();
    }

    private static List<PositionEntity> getPositionEntities() {
        return List.of(
                PositionEntity.builder()
                        .id(11L)
                        .name("Test1")
                        .department(DepartmentEntity.builder()
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
                PositionEntity.builder()
                        .id(12L)
                        .name("Test2")
                        .department(DepartmentEntity.builder()
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
                PositionEntity.builder()
                        .id(13L)
                        .name("Test3")
                        .department(DepartmentEntity.builder()
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