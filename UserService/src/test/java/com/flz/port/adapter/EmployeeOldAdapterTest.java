package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.EmployeeOld;
import com.flz.model.entity.EmployeeOldEntity;
import com.flz.model.enums.Gender;
import com.flz.model.mapper.EmployeeOldEntityToDomainMapper;
import com.flz.model.mapper.EmployeeOldToEntityMapper;
import com.flz.repository.EmployeeOldRepository;
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
import java.util.List;
import java.util.Optional;

class EmployeeOldAdapterTest extends BaseTest {

    @InjectMocks
    EmployeeOldAdapter employeeOldAdapter;

    @Mock
    EmployeeOldRepository employeeOldRepository;

    private final EmployeeOldEntityToDomainMapper
            employeeOldEntityToDomainMapper = EmployeeOldEntityToDomainMapper.INSTANCE;
    private final EmployeeOldToEntityMapper
            employeeOldToEntityMapper = EmployeeOldToEntityMapper.INSTANCE;

    /**
     * {@link EmployeeOldAdapter#findById(Long)}
     */
    @Test
    void givenValidId_whenEmployeeOldEntityFoundAccordingById_thenReturnEmployeeOld() {

        //Given
        Long mockId = 1L;

        //When
        Optional<EmployeeOldEntity> mockEmployeeEntity = Optional
                .of(getEmployeeOldEntity(mockId));

        Mockito.when(employeeOldRepository.findById(mockId))
                .thenReturn(mockEmployeeEntity);

        EmployeeOld mockEmployeeOld = employeeOldEntityToDomainMapper.map(mockEmployeeEntity.get());

        //Then
        Optional<EmployeeOld> result = employeeOldAdapter.findById(mockId);

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockId, result.get()
                .getId());
        Assertions.assertEquals(mockEmployeeOld.getId(),
                result.get().getId());
        Assertions.assertEquals(mockEmployeeOld.getIdentityNumber(),
                result.get().getIdentityNumber());
        Assertions.assertEquals(mockEmployeeOld.getLastName(),
                result.get().getLastName());
        Assertions.assertNotNull(result.get()
                .getId());
        Assertions.assertNotNull(result.get()
                .getFirstName());
        Assertions.assertNotNull(result.get()
                .getLastName());
        Assertions.assertNotNull(result.get()
                .getGender());
        Assertions.assertNotNull(result.get()
                .getBirthDate());

        //Verify
        Mockito.verify(employeeOldRepository, Mockito.times(1))
                .findById(mockId);
    }


    @Test
    void givenValidId_whenEmployeeOldEntityNotFoundById_returnOptionalEmpty() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(employeeOldRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Optional<EmployeeOld> result = employeeOldAdapter.findById(mockId);

        Assertions.assertFalse(result.isPresent());

        //Verify
        Mockito.verify(employeeOldRepository, Mockito.times(1))
                .findById(mockId);

    }

    /**
     * {@link EmployeeOldAdapter#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPageAndPageSize_whenEmployeeOldFound_thenReturnListEmployeeOld() {

        //Given
        int mockPage = 1;
        int mockPageSize = 10;

        //When
        List<EmployeeOldEntity> mockEmployeeOldEntities = getEmployeeOldEntities();
        Pageable mockPageable = PageRequest.of(0, mockPageSize);

        Page<EmployeeOldEntity> mockEmployeeOldEntitiesPage = new PageImpl<>(mockEmployeeOldEntities);
        Mockito.when(employeeOldRepository.findAll(mockPageable))
                .thenReturn(mockEmployeeOldEntitiesPage);

        //Then
        List<EmployeeOld> employeesOld = employeeOldAdapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(mockEmployeeOldEntities.size(), employeesOld.size());

        //Verify
        Mockito.verify(employeeOldRepository, Mockito.times(1))
                .findAll(mockPageable);
    }

    @Test
    void givenValidPageAndPageSize_whenEmployeeOldNotFound_thenReturnEmptyEmployees() {

        //Given
        int mockPage = 1;
        int mockPageSize = 10;

        //When
        Pageable mockPageable = PageRequest.of(0, mockPageSize);
        Mockito.when(employeeOldRepository.findAll(mockPageable))
                .thenReturn(Page.empty());

        //Then
        List<EmployeeOld> employeesOld = employeeOldAdapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(0, employeesOld.size());
        Assertions.assertEquals(0, employeeOldRepository.count());
        Assertions.assertTrue(employeesOld.isEmpty());

        //Verify
        Mockito.verify(employeeOldRepository, Mockito.times(1))
                .findAll(mockPageable);
    }

    /**
     * {@link EmployeeOldAdapter#save(EmployeeOld)} )}
     */
    @Test
    void givenEmployeeOld_whenCalledSave_thenSaveEmployeeOldEntity() {

        //Given
        EmployeeOld mockEmployeeOld = getEmployeeOld();

        EmployeeOldEntity mockEmployeeOldEntity = employeeOldToEntityMapper
                .map(mockEmployeeOld);

        //When
        Mockito.when(employeeOldRepository.save(Mockito.any(EmployeeOldEntity.class)))
                .thenReturn(mockEmployeeOldEntity);

        //Then
        Optional<EmployeeOld> result = employeeOldAdapter.save(mockEmployeeOld);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(mockEmployeeOld.getFirstName(), result.get()
                .getFirstName());
        Assertions.assertEquals(mockEmployeeOld.getLastName(), result.get()
                .getLastName());
        Assertions.assertEquals(mockEmployeeOld.getGender(), result.get()
                .getGender());
        Assertions.assertEquals(mockEmployeeOld.getBirthDate(), result.get()
                .getBirthDate());

        //Verify
        Mockito.verify(employeeOldRepository, Mockito.times(1))
                .save(Mockito.any());
    }

    @Test
    void givenEmployeeOld_whenRepositoryReturnsNull_thenReturnEmptyOptional() {

        //Given
        EmployeeOld mockEmployeeOld = getEmployeeOld();
        employeeOldToEntityMapper.map(mockEmployeeOld);

        //When
        Mockito.when(employeeOldRepository.save(Mockito.any(EmployeeOldEntity.class)))
                .thenReturn(null);

        //Then
        Optional<EmployeeOld> result = employeeOldAdapter.save(mockEmployeeOld);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isPresent());

        //Verify
        Mockito.verify(employeeOldRepository, Mockito.times(1))
                .save(Mockito.any(EmployeeOldEntity.class));

    }

    private static EmployeeOld getEmployeeOld() {
        return EmployeeOld.builder()
                .address("test address")
                .firstName("test first name")
                .lastName("test last name")
                .birthDate(LocalDate.parse("2000-01-01"))
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .email("test@gmail.com")
                .gender(Gender.FEMALE)
                .nationality("TC")
                .phoneNumber("05465321456")
                .build();
    }

    private static EmployeeOldEntity getEmployeeOldEntity(Long id) {
        return EmployeeOldEntity.builder()
                .id(id)
                .address("test address")
                .firstName("test first name")
                .lastName("test last name")
                .birthDate(LocalDate.parse("2000-01-01"))
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .email("test@gmail.com")
                .gender(Gender.FEMALE)
                .nationality("TC")
                .phoneNumber("05465321456")
                .build();
    }

    private static List<EmployeeOldEntity> getEmployeeOldEntities() {
        return List.of(
                EmployeeOldEntity.builder()
                        .id(1L)
                        .firstName("test first name 1")
                        .lastName("test last name 1")
                        .address("test address 1")
                        .birthDate(LocalDate.parse("2000-01-01"))
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .email("test1@gmail.com")
                        .gender(Gender.FEMALE)
                        .nationality("TC")
                        .phoneNumber("05465321456")
                        .build(),
                EmployeeOldEntity.builder()
                        .id(2L)
                        .firstName("test first name 2")
                        .lastName("test last name 2 ")
                        .address("test address 2")
                        .birthDate(LocalDate.parse("2000-02-02"))
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .email("test2@gmail.com")
                        .gender(Gender.FEMALE)
                        .nationality("TC")
                        .phoneNumber("05465321465")
                        .build(),
                EmployeeOldEntity.builder()
                        .id(3L)
                        .firstName("test first name 3")
                        .lastName("test last name 3")
                        .address("test address 3")
                        .birthDate(LocalDate.parse("2000-03-03"))
                        .createdBy("SYSTEM")
                        .createdAt(LocalDateTime.now())
                        .email("test3@gmail.com")
                        .gender(Gender.FEMALE)
                        .nationality("TC")
                        .phoneNumber("05465321499")
                        .build());
    }

}