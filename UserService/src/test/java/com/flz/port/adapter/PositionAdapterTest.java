package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.Position;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.entity.EmployeeEntity;
import com.flz.model.entity.PositionEntity;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class PositionAdapterTest extends BaseTest {

    @Mock
    PositionRepository positionRepository;

    @InjectMocks
    PositionAdapter adapter;

    private final PositionToEntityMapper positionToEntityMapper = PositionToEntityMapper.INSTANCE;
    private final PositionEntityToDomainMapper positionEntityToDomainMapper = PositionEntityToDomainMapper.INSTANCE;


    /**
     * {@link PositionAdapter#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPageAndPageSize_whenPositionFound_thenReturnListPositions() {

        //Given
        int mockPage = 1;
        int mockPageSize = 10;

        //When
        List<PositionEntity> mockPositionEntities = getPositionEntities();
        Pageable mockPageable = PageRequest.of(0, mockPageSize);

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
        int mockPage = 1;
        int mockPageSize = 10;

        //When
        Pageable mockPageable = PageRequest.of(0, mockPageSize);
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


    /**
     * {@link PositionAdapter#findById(Long)}
     */
    @Test
    void givenValidId_whenPositionEntityFoundAccordingById_thenReturnPosition() {

        //Given
        Long mockId = 1L;

        //When
        Optional<PositionEntity> mockPositionEntity = Optional.of(getPositionEntity(mockId));

        Mockito.when(positionRepository.findById(mockId))
                .thenReturn(mockPositionEntity);

        //Then
        Optional<Position> position = adapter.findById(mockId);

        Assertions.assertNotNull(position);
        Assertions.assertTrue(position.isPresent());
        Assertions.assertEquals(mockId, position.get()
                .getId());

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .findById(mockId);

    }


    @Test
    void givenValidId_whenPositionEntityNotFoundById_returnOptionalEmpty() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(positionRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Optional<Position> position = adapter.findById(mockId);

        Assertions.assertFalse(position.isPresent());

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .findById(mockId);

    }


    /**
     * {@link PositionAdapter#existsByName(String)}
     */
    @Test
    void givenValidName_whenPositionEntityFoundAccordingByName_thenReturnTrue() {

        //Given
        String mockName = "TestName";

        //When
        Mockito.when(positionRepository.existsByName(mockName))
                .thenReturn(Boolean.TRUE);

        //Then
        boolean result = adapter.existsByName(mockName);

        Assertions.assertTrue(result);

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .existsByName(mockName);

    }


    @Test
    void givenValidName_whenPositionEntityNotFoundAccordingByName_thenReturnFalse() {

        //Given
        String mockName = "TestName";

        //When
        Mockito.when(positionRepository.existsByName(mockName))
                .thenReturn(Boolean.FALSE);

        //Then
        boolean result = adapter.existsByName(mockName);

        Assertions.assertFalse(result);

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .existsByName(mockName);

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

    /**
     * {@link PositionAdapter#save(Position)}
     */
    @Test
    void givenPosition_whenCalledSave_thenSavePositionEntity() {

        //Given
        Position mockPosition = getPosition();
        PositionEntity mockPositionEntity = positionToEntityMapper.map(mockPosition);

        //When
        Mockito.when(positionRepository.save(Mockito.any(PositionEntity.class)))
                .thenReturn(mockPositionEntity);

        //Then
        adapter.save(mockPosition);

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .save(Mockito.any());

    }

    @Test
    void givenPosition_whenRepositoryThrowsException_thenReturnException() {

        //Given
        Position mockPosition = getPosition();
        PositionToEntityMapper.INSTANCE.map(mockPosition);

        //When
        Mockito.when(positionRepository.save(Mockito.any(PositionEntity.class)))
                .thenThrow(new RuntimeException("Database error"));

        //Then
        Assertions.assertThrows(RuntimeException.class, () -> adapter.save(mockPosition));

        //Verify
        Mockito.verify(positionRepository, Mockito.times(1))
                .save(Mockito.any());

    }

    private static Position getPosition() {
        return Position.builder()
                .name("Test")
                .department(Department.builder()
                        .id(1L)
                        .name("TestDepartment")
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
                        .status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .createdBy("TestSystem")
                        .build())
                .status(PositionStatus.ACTIVE)
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .build();
    }

    private static PositionEntity getPositionEntity(Long mockId) {
        return PositionEntity.builder()
                .id(mockId)
                .name("Test")
                .department(DepartmentEntity.builder()
                        .id(1L)
                        .name("TestDepartment")
                        .manager(EmployeeEntity.builder()
                                .id(128L)
                                .firstName("Jessi")
                                .lastName("Dual")
                                .identityNumber("25116311181")
                                .email("jessi@example.com")
                                .phoneNumber("05452321165")
                                .address("Malatya")
                                .birthDate(LocalDate.of(1997, 1, 15))
                                .gender(Gender.FEMALE)
                                .nationality("British")
                                .build())
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
                                .manager(EmployeeEntity.builder()
                                        .id(129L)
                                        .firstName("Jeffry")
                                        .lastName("Dido")
                                        .identityNumber("85116545481")
                                        .email("jeffry@example.com")
                                        .phoneNumber("05852321165")
                                        .address("Konya")
                                        .birthDate(LocalDate.of(1997, 1, 15))
                                        .gender(Gender.MALE)
                                        .nationality("British")
                                        .build())
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
                                .manager(EmployeeEntity.builder()
                                        .id(130L)
                                        .firstName("Dany")
                                        .lastName("Did")
                                        .identityNumber("85226545481")
                                        .email("danydid@example.com")
                                        .phoneNumber("05987321165")
                                        .address("Paris")
                                        .birthDate(LocalDate.of(1995, 4, 15))
                                        .gender(Gender.MALE)
                                        .nationality("British")
                                        .build())
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
                                .manager(EmployeeEntity.builder()
                                        .id(131L)
                                        .firstName("Sindy")
                                        .lastName("Diddy")
                                        .identityNumber("851122222481")
                                        .email("sindydd@example.com")
                                        .phoneNumber("06547821165")
                                        .address("Pekin")
                                        .birthDate(LocalDate.of(1997, 1, 15))
                                        .gender(Gender.FEMALE)
                                        .nationality("German")
                                        .build())
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
