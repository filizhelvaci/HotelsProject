package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.Department;
import com.flz.model.entity.DepartmentEntity;
import com.flz.model.entity.EmployeeEntity;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class DepartmentAdapterTest extends BaseTest {

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentAdapter departmentAdapter;

    private final DepartmentEntityToDomainMapper
            departmentEntityToDomainMapper = DepartmentEntityToDomainMapper.INSTANCE;

    private static DepartmentEntity getDepartment(Long mockId) {
        EmployeeEntity manager = getManager1();

        return DepartmentEntity.builder()
                .id(mockId)
                .name("Test")
                .manager(manager)
                .status(DepartmentStatus.ACTIVE)
                .createdBy("SYSTEM")
                .createdAt(LocalDateTime.now())
                .build();
    }

    private static EmployeeEntity getManager1() {
        return EmployeeEntity.builder()
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
                .build();
    }

    private static EmployeeEntity getManager2() {
        return EmployeeEntity.builder()
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
                .build();
    }

    private static EmployeeEntity getManager3() {
        return EmployeeEntity.builder()
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
                .build();
    }

    private static List<DepartmentEntity> getDepartmentEntities() {
        EmployeeEntity manager1 = getManager1();
        EmployeeEntity manager2 = getManager2();
        EmployeeEntity manager3 = getManager3();

        return List.of(
                DepartmentEntity.builder().id(11L).name("TEST1").manager(manager1).status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now()).createdBy("testAdmin").build(),
                DepartmentEntity.builder().id(12L).name("TEST2").manager(manager2).status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now()).createdBy("testAdmin").build(),
                DepartmentEntity.builder().id(13L).name("TEST3").manager(manager3).status(DepartmentStatus.ACTIVE)
                        .createdAt(LocalDateTime.now()).createdBy("testAdmin").build()
        );
    }

    /**
     * {@link DepartmentAdapter#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPageAndPageSize_whenDepartmentFound_thenReturnListDepartments() {

        //Given
        int mockPage = 1;
        int mockPageSize = 10;

        //When
        List<DepartmentEntity> mockDepartmentEntities = getDepartmentEntities();

        Pageable mockPageable = PageRequest.of(0, mockPageSize);

        Page<DepartmentEntity> mockDepartmentEntitiesPage = new PageImpl<>(mockDepartmentEntities);
        List<Department> departments = departmentEntityToDomainMapper.map(mockDepartmentEntities);

        Mockito.when(departmentRepository.findAll(mockPageable))
                .thenReturn(mockDepartmentEntitiesPage);

        //Then
        List<Department> result = departmentAdapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(mockDepartmentEntities.size(), result.size());
        Assertions.assertEquals(departments.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(departments.get(0).getName(), result.get(0).getName());
        Assertions.assertEquals(departments.get(0).getStatus(), result.get(0).getStatus());
        Assertions.assertEquals(departments.get(0).getManager().getFirstName(), result.get(0).getManager().getFirstName());
        Assertions.assertEquals(departments.get(0).getManager().getLastName(), result.get(0).getManager().getLastName());
        Assertions.assertEquals(departments.get(0).getManager().getPhoneNumber(), result.get(0).getManager().getPhoneNumber());
        Assertions.assertEquals(departments.get(1).getManager().getFirstName(), result.get(1).getManager().getFirstName());
        Assertions.assertEquals(departments.get(1).getManager().getLastName(), result.get(1).getManager().getLastName());
        Assertions.assertEquals(departments.get(1).getManager().getPhoneNumber(), result.get(1).getManager().getPhoneNumber());

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findAll(mockPageable);

    }

    @Test
    void givenValidPageAndPageSize_whenDepartmentNotFound_thenReturnEmptyDepartment() {

        //Given
        int mockPage = 1;
        int mockPageSize = 10;

        //When
        Pageable mockPageable = PageRequest.of(0, mockPageSize);
        Mockito.when(departmentRepository.findAll(mockPageable))
                .thenReturn(Page.empty());

        //Then
        List<Department> departments = departmentAdapter.findAll(mockPage, mockPageSize);

        Assertions.assertEquals(0, departments.size());
        Assertions.assertEquals(0, departmentRepository.count());
        Assertions.assertTrue(departments.isEmpty());

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findAll(mockPageable);

    }

    /**
     * {@link DepartmentAdapter#findSummaryAll()}
     */
    @Test
    void whenCalledAllSummaryDepartment_thenReturnListOfDepartmentSummariesResponse() {

        //When
        List<DepartmentEntity> mockDepartmentEntities = getDepartmentEntities();
        List<Department> mockDepartments = departmentEntityToDomainMapper
                .map(mockDepartmentEntities);

        Mockito.when(departmentRepository.findAll())
                .thenReturn(mockDepartmentEntities);

        //Then
        List<Department> result = departmentAdapter.findSummaryAll();

        Assertions.assertEquals(mockDepartments.size(), result.size());

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findAll();

    }

    @Test
    void whenCalledAllSummaryDepartmentsIfDepartmentListIsEmpty_thenReturnEmptyList() {

        //When
        Mockito.when(departmentRepository.findAll())
                .thenReturn(Collections.emptyList());

        //Then
        List<Department> departments = departmentAdapter.findSummaryAll();

        Assertions.assertEquals(0, departments.size());
        Assertions.assertTrue(departments.isEmpty());

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findAll();

    }

    /**
     * {@link DepartmentAdapter#findById(Long)}
     */
    @Test
    void givenValidId_whenDepartmentEntityFoundAccordingById_thenReturnDepartment() {

        //Given
        Long mockId = 1L;

        //When
        Optional<DepartmentEntity> mockDepartmentEntity = Optional.of(getDepartment(mockId));

        Mockito.when(departmentRepository.findById(mockId))
                .thenReturn(mockDepartmentEntity);

        //Then
        Optional<Department> department = departmentAdapter.findById(mockId);

        Assertions.assertNotNull(department);
        Assertions.assertTrue(department.isPresent());
        Assertions.assertEquals(mockId, department.get().getId());
        Assertions.assertEquals(department.get().getName(), mockDepartmentEntity.get().getName());
        Assertions.assertEquals(department.get().getManager().getFirstName(), mockDepartmentEntity.get().getManager().getFirstName());
        Assertions.assertEquals(department.get().getManager().getLastName(), mockDepartmentEntity.get().getManager().getLastName());
        Assertions.assertEquals(department.get().getManager().getPhoneNumber(), mockDepartmentEntity.get().getManager().getPhoneNumber());

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findById(mockId);

    }

    @Test
    void givenValidId_whenDepartmentEntityNotFoundById_returnOptionalEmpty() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(departmentRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Optional<Department> department = departmentAdapter.findById(mockId);

        Assertions.assertFalse(department.isPresent());

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findById(mockId);

    }

    /**
     * {@link DepartmentAdapter#existsByName(String)}
     */
    @Test
    void givenValidName_whenDepartmentEntityFoundAccordingByName_thenReturnTrue() {

        //Given
        String mockName = "TestName";

        //When
        Mockito.when(departmentRepository.existsByName(mockName))
                .thenReturn(Boolean.TRUE);

        //Then
        boolean result = departmentAdapter.existsByName(mockName);

        Assertions.assertTrue(result);

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .existsByName(mockName);

    }

    @Test
    void givenValidName_whenDepartmentEntityNotFoundAccordingByName_thenReturnFalse() {

        //Given
        String mockName = "TestName";

        //When
        Mockito.when(departmentRepository.existsByName(mockName))
                .thenReturn(Boolean.FALSE);

        //Then
        boolean result = departmentAdapter.existsByName(mockName);

        Assertions.assertFalse(result);

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .existsByName(mockName);

    }

    /**
     * {@link DepartmentAdapter#existsByManagerId(Long)}
     */
    @Test
    void givenValidManagerId_whenExistsCalledWithManagerId_thenReturnTrue() {

        //Given
        Long mockManagerId = 15L;

        //When
        Mockito.when(departmentRepository.existsByManagerId(mockManagerId))
                .thenReturn(Boolean.TRUE);

        //Then
        boolean result = departmentAdapter.existsByManagerId(mockManagerId);

        Assertions.assertTrue(result);

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .existsByManagerId(mockManagerId);

    }

    @Test
    void givenValidManagerId_whenExistsCalledNotFoundAccordingByManager_thenReturnFalse() {

        //Given
        Long mockManagerId = 1000L;

        //When
        Mockito.when(departmentRepository.existsByManagerId(mockManagerId))
                .thenReturn(Boolean.FALSE);

        //Then
        boolean result = departmentAdapter.existsByManagerId(mockManagerId);

        Assertions.assertFalse(result);

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .existsByManagerId(Mockito.anyLong());

    }

    /**
     * {@link DepartmentAdapter#findByManagerId(Long)}
     */
    @Test
    void givenValidManagerId_whenDepartmentsManagerFoundAccordingById_thenReturnDepartment() {

        //Given
        Long mockId = 10L;

        //When
        Optional<DepartmentEntity> mockDepartmentEntity = Optional.of(getDepartment(mockId));

        Mockito.when(departmentRepository.findByManagerId(mockId))
                .thenReturn(mockDepartmentEntity.get());

        //Then
        Department result = departmentAdapter.findByManagerId(mockId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getName(), mockDepartmentEntity.get().getName());
        Assertions.assertEquals(result.getManager().getFirstName(), mockDepartmentEntity.get().getManager().getFirstName());
        Assertions.assertEquals(result.getManager().getLastName(), mockDepartmentEntity.get().getManager().getLastName());
        Assertions.assertEquals(result.getManager().getPhoneNumber(), mockDepartmentEntity.get().getManager().getPhoneNumber());

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .findById(mockId);

    }

    /**
     * {@link DepartmentAdapter#save(Department)}
     */
    @Test
    void givenDepartment_whenCalledSave_thenSaveDepartmentEntity() {

        //Given
        Department mockDepartment = Department.builder()
                .name("TestName")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("TestAdmin")
                .build();

        DepartmentEntity mockDepartmentEntity = DepartmentToEntityMapper
                .INSTANCE.map(mockDepartment);

        //When
        Mockito.when(departmentRepository.save(Mockito.any(DepartmentEntity.class)))
                .thenReturn(mockDepartmentEntity);

        //Then
        departmentAdapter.save(mockDepartment);

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .save(Mockito.any());

    }

    @Test
    void givenDepartment_whenRepositoryThrowsException_thenPropagateException() {

        //Given
        Department mockDepartment = Department.builder()
                .name("TestName")
                .status(DepartmentStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .createdBy("TestAdmin")
                .build();

        DepartmentToEntityMapper.INSTANCE.map(mockDepartment);

        //When
        Mockito.when(departmentRepository.save(Mockito.any(DepartmentEntity.class)))
                .thenThrow(new RuntimeException("Database error"));

        //Then
        Assertions.assertThrows(RuntimeException.class,
                () -> departmentAdapter.save(mockDepartment));

        //Verify
        Mockito.verify(departmentRepository, Mockito.times(1))
                .save(Mockito.any());

    }

}
