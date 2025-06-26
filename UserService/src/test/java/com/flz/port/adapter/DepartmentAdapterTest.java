package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.mapper.DepartmentEntityToDomainMapper;
import com.flz.model.mapper.DepartmentToEntityMapper;
import com.flz.repository.DepartmentRepository;
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

class DepartmentAdapterTest extends BaseTest {

    @InjectMocks
    DepartmentAdapter adapter;

    @Mock
    DepartmentRepository departmentRepository;

    private final DepartmentToEntityMapper departmentToEntityMapper = DepartmentToEntityMapper.INSTANCE;
    private final DepartmentEntityToDomainMapper departmentEntityToDomainMapper = DepartmentEntityToDomainMapper.INSTANCE;

    /**
     * {@link DepartmentAdapter#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPageAndPageSize_whenDepartmentFound_thenReturnListDepartments() {

        // Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        // When
        List<DepartmentEntity> mockDepartmentEntities = getDepartmentEntities();

        Pageable mockPageable = PageRequest.of(mockPage - 1, mockPageSize);

        Page<DepartmentEntity> mockDepartmentEntitiesPage = new PageImpl<>(mockDepartmentEntities);
        Mockito.when(departmentRepository.findAll(mockPageable))
                .thenReturn(mockDepartmentEntitiesPage);

        // Then
        List<Department> departments = adapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(mockDepartmentEntities.size(), departments.size());

        // Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findAll(mockPageable);
    }

    /**
     * {@link DepartmentAdapter#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPageAndPageSize_whenDepartmentNotFound_thenReturnEmptyDepartment() {

        // Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        // When
        Pageable mockPageable = PageRequest.of(mockPage - 1, mockPageSize);
        Mockito.when(departmentRepository.findAll(mockPageable))
                .thenReturn(Page.empty());

        // Then
        List<Department> departments = adapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(0, departments.size());
        Assertions.assertEquals(0, departmentRepository.count());
        Assertions.assertTrue(departments.isEmpty());

        // Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findAll(mockPageable);
    }

    /**
     * {@link DepartmentAdapter#findSummaryAll()}
     */
    @Test
    void whenCalledAllSummaryDepartment_thenReturnListOfDepartmentSummariesResponse() {

        // When
        List<DepartmentEntity> mockDepartmentEntities = getDepartmentEntities();

        Mockito.when(departmentRepository.findAll())
                .thenReturn(mockDepartmentEntities);

        // Then
        List<Department> mockDepartments = departmentEntityToDomainMapper
                .map(mockDepartmentEntities);
        List<Department> result = adapter.findSummaryAll();
        Assertions.assertEquals(mockDepartments.size(), result.size());

        // Verify
        Mockito.verify(departmentRepository, Mockito.times(1)).findAll();
    }

    /**
     * {@link DepartmentAdapter#findSummaryAll()}
     */
    @Test
    void whenCalledAllSummaryDepartmentsIfDepartmentListIsEmpty_thenReturnEmptyList() {

        // When
        Mockito.when(departmentRepository.findAll())
                .thenReturn(Collections.emptyList());

        // Then
        List<Department> departments = adapter.findSummaryAll();

        Assertions.assertEquals(0, departments.size());
        Assertions.assertTrue(departments.isEmpty());

        // Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findAll();
    }

    private static List<DepartmentEntity> getDepartmentEntities() {
        return List.of(
                DepartmentEntity.builder().id(11L).name("TEST1").status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now()).createdBy("testAdmin").build(),
                DepartmentEntity.builder().id(12L).name("TEST2").status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now()).createdBy("testAdmin").build(),
                DepartmentEntity.builder().id(13L).name("TEST3").status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now()).createdBy("testAdmin").build()
        );
    }


}