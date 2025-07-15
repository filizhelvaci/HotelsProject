package com.flz.port.adapter;

import com.flz.BaseTest;
import com.flz.model.Employee;
import com.flz.model.entity.EmployeeEntity;
import com.flz.model.enums.Gender;
import com.flz.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

class EmployeeAdapterTest extends BaseTest {

    @InjectMocks
    EmployeeAdapter employeeAdapter;

    @Mock
    EmployeeRepository employeeRepository;

    /**
     * {@link EmployeeAdapter#findById(Long)}
     */
    @Test
    public void givenValidId_whenEmployeeEntityFoundAccordingById_thenReturnEmployee() {

        //Given
        Long mockId = 1L;

        //When
        Optional<EmployeeEntity> mockEmployeeEntity = Optional.of(getEmployeeEntity(mockId));

        Mockito.when(employeeRepository.findById(mockId))
                .thenReturn(mockEmployeeEntity);

        //Then
        Optional<Employee> employee = employeeAdapter.findById(mockId);

        Assertions.assertNotNull(employee);
        Assertions.assertTrue(employee.isPresent());
        Assertions.assertEquals(mockId, employee.get()
                .getId());
        Assertions.assertNotNull(employee.get()
                .getId());
        Assertions.assertNotNull(employee.get()
                .getFirstName());
        Assertions.assertNotNull(employee.get()
                .getLastName());
        Assertions.assertNotNull(employee.get()
                .getGender());
        Assertions.assertNotNull(employee.get()
                .getBirthDate());

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findById(mockId);
    }


    @Test
    public void givenValidId_whenEmployeeEntityNotFoundById_returnOptionalEmpty() {

        //Given
        Long mockId = 10L;

        //When
        Mockito.when(employeeRepository.findById(mockId))
                .thenReturn(Optional.empty());

        //Then
        Optional<Employee> employee = employeeAdapter.findById(mockId);

        Assertions.assertFalse(employee.isPresent());

        //Verify
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findById(mockId);

    }

    private static EmployeeEntity getEmployeeEntity(Long id) {
        return EmployeeEntity.builder()
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
}