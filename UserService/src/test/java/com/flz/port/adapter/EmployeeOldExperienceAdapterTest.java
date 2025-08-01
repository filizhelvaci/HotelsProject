package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.EmployeeOldExperience;
import com.flz.model.entity.*;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.enums.PositionStatus;
import com.flz.model.mapper.EmployeeOldExperienceEntityToDomainMapper;
import com.flz.model.mapper.EmployeeOldExperienceToEntityMapper;
import com.flz.repository.EmployeeOldExperienceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class EmployeeOldExperienceAdapterTest extends BaseTest {

    @InjectMocks
    EmployeeOldExperienceAdapter employeeOldExperienceAdapter;

    @Mock
    EmployeeOldExperienceRepository employeeOldExperienceRepository;

    private final EmployeeOldExperienceEntityToDomainMapper
            employeeOldExperienceEntityToDomainMapper = EmployeeOldExperienceEntityToDomainMapper.INSTANCE;

    private final EmployeeOldExperienceToEntityMapper
            employeeOldExperienceToEntityMapper = EmployeeOldExperienceToEntityMapper.INSTANCE;

    /**
     * {@link EmployeeOldExperienceAdapter#findAllByEmployeeOldId(Long)}
     */
    @Test
    void givenValidId_whenEmployeeOldExperienceListFoundAccordingById_thenReturnEmployeeExperienceList() {

        //Given
        Long mockId = 101L;

        PositionEntity position = PositionEntity.builder()
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

        EmployeeOldEntity employee = EmployeeOldEntity.builder()
                .id(101L)
                .firstName("John")
                .lastName("Doe")
                .identityNumber("25896314785")
                .email("john.doe@example.com")
                .phoneNumber("05456566565")
                .gender(Gender.MALE)
                .nationality("USA")
                .build();

        EmployeeEntity supervisor = EmployeeEntity.builder()
                .id(201L)
                .firstName("Jane")
                .lastName("Smith")
                .identityNumber("987654321478")
                .email("jane.smith@example.com")
                .phoneNumber("05053213232")
                .gender(Gender.MALE)
                .nationality("TC")
                .gender(Gender.FEMALE)
                .build();

        List<EmployeeOldExperienceEntity> mockEmployeeOldExperienceEntities = List.of(
                EmployeeOldExperienceEntity.builder()
                        .id(1L)
                        .salary(BigDecimal.valueOf(65000))
                        .startDate(LocalDate.of(2020, 1, 15))
                        .endDate(LocalDate.of(2023, 12, 31))
                        .position(position)
                        .employeeOld(employee)
                        .supervisor(supervisor)
                        .build(),
                EmployeeOldExperienceEntity.builder()
                        .id(1L)
                        .salary(BigDecimal.valueOf(85000))
                        .startDate(LocalDate.of(2024, 1, 1))
                        .endDate(LocalDate.of(2025, 1, 31))
                        .position(position)
                        .employeeOld(employee)
                        .supervisor(supervisor)
                        .build());

        Mockito.when(employeeOldExperienceRepository.findAllByEmployeeOld_Id(mockId))
                .thenReturn(mockEmployeeOldExperienceEntities);

        List<EmployeeOldExperience> mockEmployeeOldExperiences =
                employeeOldExperienceEntityToDomainMapper.map(mockEmployeeOldExperienceEntities);

        //Then
        List<EmployeeOldExperience> result = employeeOldExperienceAdapter.findAllByEmployeeOldId(mockId);

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(mockEmployeeOldExperiences);
        Assertions.assertNotNull(mockEmployeeOldExperiences.get(0)
                .getEmployeeOld());
        Assertions.assertNotNull(mockEmployeeOldExperiences.get(0)
                .getSupervisor());
        Assertions.assertNotNull(mockEmployeeOldExperiences.get(0)
                .getPosition());
        Assertions.assertNotNull(mockEmployeeOldExperiences.get(0)
                .getPosition().getId());
        Assertions.assertNotNull(mockEmployeeOldExperiences.get(0)
                .getPosition().getDepartment());
        Assertions.assertNotNull(mockEmployeeOldExperiences.get(0)
                .getPosition().getStatus());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getName());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getStatus());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getDepartment());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getDepartment().getName());
        Assertions.assertNotNull(result.get(0).getPosition()
                .getDepartment().getStatus());

        //Verify
        Mockito.verify(employeeOldExperienceRepository, Mockito.times(1))
                .findAllByEmployeeOld_Id(Mockito.anyLong());

    }

    @Test
    void givenValidId_whenEmployeeOldExperienceEntityNotFoundById_returnEmptyList() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(employeeOldExperienceRepository.findAllByEmployeeOld_Id(mockId))
                .thenReturn(Collections.emptyList());

        //Then
        List<EmployeeOldExperience> result = employeeOldExperienceAdapter
                .findAllByEmployeeOldId(mockId);

        Assertions.assertTrue(result.isEmpty());
        Assertions.assertNotNull(result);

        //Verify
        Mockito.verify(employeeOldExperienceRepository, Mockito.times(1))
                .findAllByEmployeeOld_Id(Mockito.anyLong());

    }
}