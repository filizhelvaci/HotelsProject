package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseEndToEndTest;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.Position;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.enums.PositionStatus;
import com.flz.model.request.PositionCreateRequest;
import com.flz.model.request.PositionUpdateRequest;
import com.flz.port.DepartmentTestPort;
import com.flz.port.EmployeeSavePort;
import com.flz.port.PositionReadPort;
import com.flz.port.PositionTestPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

class PositionEndToEndTest extends BaseEndToEndTest {

    @Autowired
    private PositionReadPort positionReadPort;

    @Autowired
    private PositionTestPort positionTestPort;

    @Autowired
    private EmployeeSavePort employeeSavePort;

    @Autowired
    private DepartmentTestPort departmentTestPort;

    private static final String BASE_PATH = "/api/v1";

    @Test
    void givenCreateRequest_whenCreatePosition_thenReturnSuccess() throws Exception {

        //Initialize
        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Kamil")
                .lastName("Koç")
                .identityNumber("852155714785")
                .email("kamilk@example.com")
                .phoneNumber("05328778565")
                .address("Bursa")
                .birthDate(LocalDate.of(1980, 1, 15))
                .gender(Gender.MALE)
                .nationality("Turkey")
                .build());

        Department departmentSaved = departmentTestPort.save(Department.builder()
                .name("Özel Güvenlik Birimi")
                .status(DepartmentStatus.ACTIVE)
                .manager(manager)
                .build());

        //Given
        PositionCreateRequest createRequest = PositionCreateRequest.builder()
                .name("Otopark - Güvenlik")
                .departmentId(departmentSaved.getId())
                .build();

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        //Then
        Position createdPosition = positionTestPort.findByName(createRequest.getName())
                .orElseThrow(() -> new AssertionError("Position not found"));

        Assertions.assertNotNull(createdPosition.getId());
        Assertions.assertNotNull(createdPosition.getName());
        Assertions.assertNotNull(createdPosition.getStatus());

        Assertions.assertNotNull(createdPosition.getDepartment().getId());
        Assertions.assertEquals(PositionStatus.ACTIVE, createdPosition.getStatus());
        Assertions.assertEquals(createRequest.getName(), createdPosition.getName());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, createdPosition.getDepartment().getStatus());
        Assertions.assertEquals(departmentSaved.getName(), createdPosition.getDepartment().getName());
        Assertions.assertEquals(departmentSaved.getManager().getFirstName(),
                createdPosition.getDepartment().getManager().getFirstName());
        Assertions.assertEquals(departmentSaved.getManager().getPhoneNumber(),
                createdPosition.getDepartment().getManager().getPhoneNumber());

        Assertions.assertNotNull(createdPosition.getCreatedAt());
        Assertions.assertEquals("System", createdPosition.getCreatedBy());
        Assertions.assertNull(createdPosition.getUpdatedAt());
        Assertions.assertNull(createdPosition.getUpdatedBy());

    }


    @Test
    void givenUpdateRequest_whenUpdatePosition_thenReturnSuccess() throws Exception {

        //Initialize
        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Semih")
                .lastName("Kaynar")
                .identityNumber("852166714785")
                .email("semihkk@example.com")
                .phoneNumber("05468128565")
                .address("Karaman")
                .birthDate(LocalDate.of(1985, 1, 15))
                .gender(Gender.MALE)
                .nationality("Turkey")
                .build());

        Department departmentSaved = departmentTestPort.save(Department.builder()
                .name("Giriş Güvenlik")
                .status(DepartmentStatus.ACTIVE)
                .manager(manager)
                .build());

        Position positionSaved = positionTestPort.save(Position.builder()
                .department(departmentSaved)
                .name("Lobi Güvenlik")
                .status(PositionStatus.ACTIVE)
                .build());

        PositionUpdateRequest updateRequest = PositionUpdateRequest.builder()
                .name("Lobi Güvenlik Personeli")
                .departmentId(departmentSaved.getId())
                .build();

        //Given
        Long positionId = positionSaved.getId();

        //When
        MockHttpServletRequestBuilder updateRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/position/" + positionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequest));
        //Then
        mockMvc.perform(updateRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Optional<Position> position = positionReadPort.findById(positionId);

        Assertions.assertTrue(position.isPresent());

        Assertions.assertEquals(updateRequest.getName(), position.get().getName());
        Assertions.assertEquals(updateRequest.getDepartmentId(), position.get().getDepartment().getId());
        Assertions.assertNotEquals(positionSaved.getName(), position.get().getName());

        Assertions.assertEquals(PositionStatus.ACTIVE, position.get().getStatus());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, position.get().getDepartment().getStatus());

        Assertions.assertEquals("System", position.get().getCreatedBy());
        Assertions.assertNotNull(position.get().getCreatedAt());
        Assertions.assertNotNull(position.get().getUpdatedAt());
        Assertions.assertEquals("System", position.get().getUpdatedBy());

    }

    @Test
    void givenUpdateRequest_whenUpdateDepartmentOfPosition_thenReturnSuccess() throws Exception {

        //Initialize
        Employee otherManager = employeeSavePort.save(Employee.builder()
                .firstName("Semiha")
                .lastName("Kaynamaz")
                .identityNumber("999166714785")
                .email("semihak@example.com")
                .phoneNumber("05328123365")
                .address("Kırka")
                .birthDate(LocalDate.of(1995, 1, 15))
                .gender(Gender.FEMALE)
                .nationality("Turkey")
                .build());

        Department otherDepartmentSaved = departmentTestPort.save(Department.builder()
                .name("Giriş Kontrol")
                .status(DepartmentStatus.ACTIVE)
                .manager(otherManager)
                .build());

        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Sevde")
                .lastName("Kayık")
                .identityNumber("852166755785")
                .email("sevdeee@example.com")
                .phoneNumber("05051235252")
                .address("Kastamonu")
                .birthDate(LocalDate.of(1992, 1, 15))
                .gender(Gender.FEMALE)
                .nationality("Turkey")
                .build());

        Department departmentSaved = departmentTestPort.save(Department.builder()
                .name("Giriş Güvenlik Kontrol Birimi")
                .status(DepartmentStatus.ACTIVE)
                .manager(manager)
                .build());

        Position positionSaved = positionTestPort.save(Position.builder()
                .department(departmentSaved)
                .name("Lobi Kontrol Amiri")
                .status(PositionStatus.ACTIVE)
                .build());

        PositionUpdateRequest updateRequest = PositionUpdateRequest.builder()
                .name("Lobi Kontrol Amiri")
                .departmentId(otherDepartmentSaved.getId())
                .build();

        //Given
        Long positionId = positionSaved.getId();

        //When
        MockHttpServletRequestBuilder updateRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/position/" + positionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateRequest));
        //Then
        mockMvc.perform(updateRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Optional<Position> position = positionReadPort.findById(positionId);

        Assertions.assertTrue(position.isPresent());

        Assertions.assertEquals(updateRequest.getName(), position.get().getName());
        Assertions.assertEquals(updateRequest.getDepartmentId(), position.get().getDepartment().getId());
        Assertions.assertEquals(positionSaved.getName(), position.get().getName());

        Assertions.assertEquals(PositionStatus.ACTIVE, position.get().getStatus());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, position.get().getDepartment().getStatus());

        Assertions.assertEquals(otherDepartmentSaved.getName(), position.get().getDepartment().getName());
        Assertions.assertEquals(otherDepartmentSaved.getManager().getPhoneNumber(),
                position.get().getDepartment().getManager().getPhoneNumber());
        Assertions.assertEquals(otherDepartmentSaved.getManager().getIdentityNumber(),
                position.get().getDepartment().getManager().getIdentityNumber());

        Assertions.assertEquals("System", position.get().getCreatedBy());
        Assertions.assertNotNull(position.get().getCreatedAt());
        Assertions.assertNotNull(position.get().getUpdatedAt());
        Assertions.assertEquals("System", position.get().getUpdatedBy());

    }


    @Test
    void givenPositionId_whenDeletePosition_thenSoftDeleted() throws Exception {

        //Initialize
        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Semra")
                .lastName("Karar")
                .identityNumber("996166714785")
                .email("krrsemra@example.com")
                .phoneNumber("05429125561")
                .address("Balıkesir")
                .birthDate(LocalDate.of(1981, 2, 15))
                .gender(Gender.FEMALE)
                .nationality("Turkey")
                .build());

        Department departmentSaved = departmentTestPort.save(Department.builder()
                .name("Sağlık Hizmetleri")
                .status(DepartmentStatus.ACTIVE)
                .manager(manager)
                .build());

        Position positionSaved = positionTestPort.save(Position.builder()
                .department(departmentSaved)
                .name("Sağlık Görevlisi")
                .status(PositionStatus.ACTIVE)
                .build());

        //Given
        Long positionId = positionSaved.getId();

        //When
        MockHttpServletRequestBuilder deleteRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/position/" + positionId);

        //Then
        mockMvc.perform(deleteRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Optional<Position> deletedPosition = positionReadPort.findById(positionId);

        Assertions.assertTrue(deletedPosition.isPresent());
        Assertions.assertEquals(positionId, deletedPosition.get().getId());
        Assertions.assertEquals(positionSaved.getName(), deletedPosition.get().getName());
        Assertions.assertEquals(positionSaved.getDepartment().getId(), deletedPosition.get().getDepartment().getId());
        Assertions.assertNotEquals(positionSaved.getStatus(), deletedPosition.get().getStatus());

        Assertions.assertEquals(PositionStatus.ACTIVE, positionSaved.getStatus());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, positionSaved.getDepartment().getStatus());
        Assertions.assertEquals(PositionStatus.DELETED, deletedPosition.get().getStatus());

        Assertions.assertNotNull(deletedPosition.get().getName());
        Assertions.assertNotNull(deletedPosition.get().getDepartment());
        Assertions.assertNotNull(deletedPosition.get().getDepartment().getManager());
        Assertions.assertNotNull(deletedPosition.get().getDepartment().getManager().getPhoneNumber());

        Assertions.assertNotNull(deletedPosition.get().getCreatedAt());
        Assertions.assertEquals("System", deletedPosition.get().getCreatedBy());
        Assertions.assertNotNull(deletedPosition.get().getUpdatedAt());
        Assertions.assertEquals("System", deletedPosition.get().getUpdatedBy());

    }


    @Test
    void whenFindAllPositions_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions")
                .param("page", "1")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON);
        //Then
        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].status")
                        .exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdBy")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdAt")
                        .exists());

        //Then
        List<Position> positions = positionReadPort.findAll(1, 10);

        Assertions.assertNotNull(positions);
        Assertions.assertFalse(positions.isEmpty());
        Assertions.assertNotNull(positions.get(0).getId());
        Assertions.assertNotNull(positions.get(0).getName());
        Assertions.assertNotNull(positions.get(0).getStatus());
        Assertions.assertNotNull(positions.get(0).getCreatedAt());
        Assertions.assertNotNull(positions.get(0).getCreatedBy());
        Assertions.assertNotNull(positions.get(0).getDepartment());
        Assertions.assertNotNull(positions.get(0).getDepartment().getId());
        Assertions.assertNotNull(positions.get(0).getDepartment().getName());
        Assertions.assertNotNull(positions.get(0).getDepartment().getStatus());
        Assertions.assertNotNull(positions.get(0).getDepartment().getManager().getFirstName());
        Assertions.assertNotNull(positions.get(0).getDepartment().getManager().getLastName());
        Assertions.assertNotNull(positions.get(0).getDepartment().getManager().getIdentityNumber());
        Assertions.assertNotNull(positions.get(0).getDepartment().getManager().getPhoneNumber());
        Assertions.assertNotNull(positions.get(0).getDepartment().getManager().getBirthDate());
        Assertions.assertNotNull(positions.get(0).getDepartment().getManager().getGender());

    }


    @Test
    void whenFindSummaryAllPositions_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/positions/summary")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].status")
                        .doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].departmentId")
                        .doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.createdAt")
                        .doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response.createdBy")
                        .doesNotExist());

        //Then
        List<Position> positions = positionReadPort.findSummaryAll();

        Assertions.assertNotNull(positions);
        Assertions.assertFalse(positions.isEmpty());
        Assertions.assertNotNull(positions.get(0).getName());
        Assertions.assertNotNull(positions.get(0).getId());

    }

}
