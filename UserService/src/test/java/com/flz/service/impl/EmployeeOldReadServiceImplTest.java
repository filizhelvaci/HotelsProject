package com.flz.service.impl;

import com.flz.BaseTest;
import com.flz.exception.EmployeeOldNotFoundException;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.EmployeeOld;
import com.flz.model.EmployeeOldExperience;
import com.flz.model.Position;
import com.flz.model.enums.Gender;
import com.flz.model.mapper.EmployeeOldExperienceToResponseMapper;
import com.flz.model.response.EmployeeOldDetailsResponse;
import com.flz.model.response.EmployeeOldExperienceResponse;
import com.flz.port.EmployeeOldExperienceReadPort;
import com.flz.port.EmployeeOldReadPort;
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
import java.util.Optional;


class EmployeeOldReadServiceImplTest extends BaseTest {

    @Mock
    EmployeeOldReadPort employeeOldReadPort;

    @Mock
    EmployeeOldExperienceReadPort employeeOldExperienceReadPort;

    @Mock
    EmployeeOldExperienceToResponseMapper employeeOldExperienceToResponseMapper;

    @InjectMocks
    EmployeeOldReadServiceImpl employeeOldReadServiceImpl;


    /**
     * {@link EmployeeOldReadServiceImpl#findById(Long)}
     */
    @Test
    void givenValidId_whenFindByIdEmployeeOld_thenReturnEmployeeOldDetailsResponseSuccessfully() {

        //Given
        Long mockId = 1L;

        //Initialize
        EmployeeOld mockEmployeeOld = EmployeeOld.builder()
                .id(mockId)
                .firstName("Filiz")
                .lastName("Helvaci")
                .identityNumber("12345678901")
                .phoneNumber("05551231212")
                .address("Ankara")
                .email("filiz@example.com")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.of(1995, 1, 1))
                .nationality("TR")
                .build();

        EmployeeOldExperience empExp1 = EmployeeOldExperience.builder()
                .id(1L)
                .salary(BigDecimal.valueOf(55000))
                .startDate(LocalDate.of(2021, 11, 12))
                .endDate(LocalDate.of(2022, 11, 12))
                .position(Position.builder()
                        .id(20L)
                        .name("Kat Sorumlusu")
                        .department(Department.builder()
                                .id(18L)
                                .name("Düzen ve Tedarik Departmanı")
                                .manager(Employee.builder()
                                        .firstName("Samuel")
                                        .lastName("Jackson")
                                        .identityNumber("9998889988577")
                                        .address("New Jersey")
                                        .birthDate(LocalDate.of(1995, 1, 2))
                                        .email("samuell@gmail.com")
                                        .gender(Gender.MALE)
                                        .nationality("USA")
                                        .phoneNumber("05551212211")
                                        .build())
                                .build())
                        .build())
                .employeeOld(mockEmployeeOld)
                .build();

        EmployeeOldExperience empExp2 = EmployeeOldExperience.builder()
                .id(2L)
                .salary(BigDecimal.valueOf(75000))
                .startDate(LocalDate.of(2022, 11, 13))
                .endDate(LocalDate.of(2023, 11, 13))
                .position(Position.builder()
                        .id(28L)
                        .name("Departman Sorumlusu")
                        .department(Department.builder()
                                .id(18L)
                                .name("Düzen ve Tedarik Departmanı")
                                .manager(Employee.builder()
                                        .firstName("Brad")
                                        .lastName("Pitt")
                                        .identityNumber("1118889988577")
                                        .address("New York")
                                        .birthDate(LocalDate.of(1981, 1, 2))
                                        .email("braddd@gmail.com")
                                        .gender(Gender.MALE)
                                        .nationality("USA")
                                        .phoneNumber("05329982211")
                                        .build())
                                .build())
                        .build())
                .employeeOld(mockEmployeeOld)
                .build();

        List<EmployeeOldExperience> mockExperienceList = List.of(empExp1, empExp2);

        //When
        Mockito.when(employeeOldReadPort.findById(mockId))
                .thenReturn(Optional.of(mockEmployeeOld));

        Mockito.when(employeeOldExperienceReadPort.findAllByEmployeeOldId(mockId))
                .thenReturn(mockExperienceList);

        Mockito.when(employeeOldExperienceToResponseMapper.map(empExp1))
                .thenReturn(EmployeeOldExperienceResponse.builder()
                        .id(empExp1.getId())
                        .salary(empExp1.getSalary())
                        .startDate(empExp1.getStartDate())
                        .endDate(empExp1.getEndDate())
                        .positionName(empExp1.getPosition().getName())
                        .departmentName(empExp1.getPosition().getDepartment().getName())
                        .managerName(empExp1.getPosition().getDepartment().getManager().getFirstName() + " " + empExp1.getPosition().getDepartment().getManager().getLastName())
                        .build());

        Mockito.when(employeeOldExperienceToResponseMapper.map(empExp2))
                .thenReturn(EmployeeOldExperienceResponse.builder()
                        .id(empExp2.getId())
                        .salary(empExp2.getSalary())
                        .startDate(empExp2.getStartDate())
                        .endDate(empExp2.getEndDate())
                        .positionName(empExp2.getPosition().getName())
                        .departmentName(empExp2.getPosition().getDepartment().getName())
                        .managerName(empExp2.getPosition().getDepartment().getManager().getFirstName() + " " + empExp2.getPosition().getDepartment().getManager().getLastName())
                        .build());

        //Then
        EmployeeOldDetailsResponse response = employeeOldReadServiceImpl.findById(mockId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockEmployeeOld, response.getEmployeeOld());
        Assertions.assertEquals(2, response.getExperiences()
                .size());
        Assertions.assertEquals("Kat Sorumlusu", response.getExperiences()
                .get(0)
                .getPositionName());

        //Verify
        Mockito.verify(employeeOldReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(employeeOldExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeOldId(mockId);

    }

    @Test
    void givenInvalidEmployeeOldId_whenFindByIdEmployeeOld_thenThrowEmployeeNotFoundException() {

        //Given
        Long invalidEmployeeOldId = 999L;

        //When
        Mockito.when(employeeOldReadPort.findById(invalidEmployeeOldId))
                .thenReturn(Optional.empty());

        //Then
        Assertions.assertThrows(EmployeeOldNotFoundException.class,
                () -> employeeOldReadServiceImpl.findById(invalidEmployeeOldId));

        //Verify
        Mockito.verify(employeeOldReadPort, Mockito.times(1))
                .findById(Mockito.anyLong());
        Mockito.verify(employeeOldExperienceReadPort, Mockito.never())
                .findAllByEmployeeOldId(Mockito.anyLong());

    }

    @Test
    void givenValidEmployeeId_whenFindByIdEmployeeOldWithEmptyEmployeeExperienceList_thenReturnSuccessfully() {

        //Given
        Long mockId = 1L;

        //Initialize
        EmployeeOld mockEmployeeOld = EmployeeOld.builder()
                .id(mockId)
                .firstName("Filiz")
                .lastName("Helvaci")
                .identityNumber("12345678901")
                .phoneNumber("05551231212")
                .address("Ankara")
                .email("filiz@example.com")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.of(1995, 1, 1))
                .nationality("TR")
                .build();

        List<EmployeeOldExperience> mockExperienceList = Collections.emptyList();

        //When
        Mockito.when(employeeOldReadPort.findById(mockId))
                .thenReturn(Optional.of(mockEmployeeOld));

        Mockito.when(employeeOldExperienceReadPort.findAllByEmployeeOldId(mockId))
                .thenReturn(mockExperienceList);

        //Then
        EmployeeOldDetailsResponse response = employeeOldReadServiceImpl.findById(mockId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(mockEmployeeOld, response.getEmployeeOld());
        Assertions.assertEquals(0, response.getExperiences()
                .size());

        //Verify
        Mockito.verify(employeeOldReadPort, Mockito.times(1))
                .findById(mockId);
        Mockito.verify(employeeOldExperienceReadPort, Mockito.times(1))
                .findAllByEmployeeOldId(mockId);

    }

    /**
     * {@link EmployeeOldReadServiceImpl#findAll(Integer, Integer)}
     */
    @Test
    void givenValidPagePageSize_whenCalledAllEmployeeOld_thenReturnListAllOfEmployeesOld() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        List<EmployeeOld> mockEmployeeOlds = getEmployeeOlds();

        Mockito.when(employeeOldReadPort.findAll(mockPage, mockPageSize))
                .thenReturn(mockEmployeeOlds);

        //Then
        List<EmployeeOld> result = employeeOldReadServiceImpl
                .findAll(mockPage, mockPageSize);
        Assertions.assertEquals(mockEmployeeOlds.size(), result.size());

        //Verify
        Mockito.verify(employeeOldReadPort, Mockito.times(1))
                .findAll(Mockito.anyInt(), Mockito.anyInt());

    }

    @Test
    void givenValidPagePageSize_whenCalledAllEmployeeOldIfAllEmployeeOldIsEmpty_thenReturnEmptyList() {

        //Given
        Integer mockPage = 1;
        Integer mockPageSize = 10;

        //When
        Mockito.when(employeeOldReadPort.findAll(mockPage, mockPageSize))
                .thenReturn(Collections.emptyList());

        //Then
        List<EmployeeOld> result = employeeOldReadServiceImpl
                .findAll(mockPage, mockPageSize);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.size());
        Assertions.assertTrue(result.isEmpty());

        //Verify
        Mockito.verify(employeeOldReadPort, Mockito.times(1))
                .findAll(mockPage, mockPageSize);

    }

    private static List<EmployeeOld> getEmployeeOlds() {
        return List.of(
                EmployeeOld.builder()
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
                EmployeeOld.builder()
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
                EmployeeOld.builder()
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
