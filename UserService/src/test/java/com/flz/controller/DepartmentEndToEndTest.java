package com.flz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flz.BaseEndToEndTest;
import com.flz.exception.DepartmentNameNotFoundException;
import com.flz.model.Department;
import com.flz.model.Employee;
import com.flz.model.enums.DepartmentStatus;
import com.flz.model.enums.Gender;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.port.DepartmentReadPort;
import com.flz.port.DepartmentTestPort;
import com.flz.port.EmployeeSavePort;
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

class DepartmentEndToEndTest extends BaseEndToEndTest {

    @Autowired
    private DepartmentReadPort departmentReadPort;

    @Autowired
    private DepartmentTestPort departmentTestPort;

    @Autowired
    private EmployeeSavePort employeeSavePort;

    private static final String BASE_PATH = "/api/v1";

    @Test
    void givenCreateRequest_whenCreateDepartment_thenReturnSuccess() throws Exception {

        //Initialize
        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Eylem")
                .lastName("Tor")
                .identityNumber("598188814785")
                .email("eylemtr@example.com")
                .phoneNumber("05368888865")
                .address("Trabzon")
                .birthDate(LocalDate.of(1982, 1, 15))
                .gender(Gender.MALE)
                .nationality("Turkey")
                .build());

        DepartmentCreateRequest createRequest = DepartmentCreateRequest.builder()
                .name("Personel Alım")
                .managerId(manager.getId())
                .build();

        //When
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
                .post(BASE_PATH + "/department")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createRequest));

        //Then
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess").value(true));

        //Verify
        Department createdDepartment = departmentTestPort.findByName(createRequest.getName())
                .orElseThrow(() -> new DepartmentNameNotFoundException(createRequest.getName()));

        Assertions.assertNotNull(createdDepartment);
        Assertions.assertNotNull(createdDepartment.getId());

        Assertions.assertEquals(createRequest.getName(), createdDepartment.getName());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, createdDepartment.getStatus());
        Assertions.assertEquals(createRequest.getManagerId(), createdDepartment.getManager().getId());
        Assertions.assertEquals(manager.getIdentityNumber(), createdDepartment.getManager().getIdentityNumber());
        Assertions.assertEquals(manager.getPhoneNumber(), createdDepartment.getManager().getPhoneNumber());

        Assertions.assertEquals("System",createdDepartment.getCreatedBy());
        Assertions.assertNotNull(createdDepartment.getCreatedBy());
        Assertions.assertNull(createdDepartment.getUpdatedBy());
        Assertions.assertNull(createdDepartment.getUpdatedAt());

    }


    @Test
    void givenUpdateRequest_whenUpdateDepartment_thenReturnSuccess() throws Exception {

        //Initialize
        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Cemil")
                .lastName("Denver")
                .identityNumber("515155714785")
                .email("dencem@example.com")
                .phoneNumber("05558888565")
                .address("Mersin")
                .birthDate(LocalDate.of(1982, 1, 15))
                .gender(Gender.MALE)
                .nationality("Norway")
                .build());

        Department departmentSaved = departmentTestPort.save(Department.builder()
                .name("Kat Güvenlik")
                .status(DepartmentStatus.ACTIVE)
                .manager(manager)
                .build());

        DepartmentUpdateRequest updateRequest = DepartmentUpdateRequest.builder()
                .name("Kat Güvenlik Departmani")
                .managerId(manager.getId())
                .build();

        //Given
        Long departmentId = departmentSaved.getId();

        //When
        MockHttpServletRequestBuilder updateRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/department/" + departmentId)
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
        Department department = departmentTestPort.findByName(updateRequest.getName())
                .orElseThrow(() -> new DepartmentNameNotFoundException(updateRequest.getName()));

        Assertions.assertNotNull(department.getId());
        Assertions.assertNotNull(department.getName());
        Assertions.assertNotNull(department.getStatus());
        Assertions.assertNotNull(department.getManager());

        Assertions.assertEquals(updateRequest.getName(), department.getName());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, department.getStatus());
        Assertions.assertEquals(manager.getIdentityNumber(), department.getManager().getIdentityNumber());
        Assertions.assertEquals(manager.getPhoneNumber(), department.getManager().getPhoneNumber());

        Assertions.assertEquals("System",department.getUpdatedBy());
        Assertions.assertNotNull(department.getUpdatedBy());

    }

    @Test
    void givenUpdateRequest_whenUpdateManagerOfDepartment_thenReturnSuccess() throws Exception {

        //Initialize
        Employee oldManager = employeeSavePort.save(Employee.builder()
                .firstName("Aysel")
                .lastName("Kızmaz")
                .identityNumber("515155733785")
                .email("ayselkz@example.com")
                .phoneNumber("05451118565")
                .address("Aksaray")
                .birthDate(LocalDate.of(1982, 3, 15))
                .gender(Gender.FEMALE)
                .nationality("Türkiye")
                .build());

        Employee newManager = employeeSavePort.save(Employee.builder()
                .firstName("Fatma")
                .lastName("Deniz")
                .identityNumber("778755714785")
                .email("denizftm@example.com")
                .phoneNumber("05328888121")
                .address("Çorum")
                .birthDate(LocalDate.of(1987, 1, 15))
                .gender(Gender.FEMALE)
                .nationality("Türkiye")
                .build());

        Department departmentSaved = departmentTestPort.save(Department.builder()
                .name("Kat Güvenlik")
                .status(DepartmentStatus.ACTIVE)
                .manager(oldManager)
                .build());

        DepartmentUpdateRequest updateRequest = DepartmentUpdateRequest.builder()
                .name("Kat Güvenlik")
                .managerId(newManager.getId())
                .build();

        //Given
        Long departmentId = departmentSaved.getId();

        //When
        MockHttpServletRequestBuilder updateRequestBuilder = MockMvcRequestBuilders
                .put(BASE_PATH + "/department/" + departmentId)
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
        Department department = departmentTestPort.findByName(updateRequest.getName())
                .orElseThrow(() -> new DepartmentNameNotFoundException(updateRequest.getName()));

        Assertions.assertNotNull(department.getId());
        Assertions.assertNotNull(department.getName());
        Assertions.assertNotNull(department.getStatus());
        Assertions.assertNotNull(department.getManager());

        Assertions.assertEquals(updateRequest.getName(), department.getName());
        Assertions.assertEquals(DepartmentStatus.ACTIVE, department.getStatus());
        Assertions.assertNotEquals(oldManager.getIdentityNumber(), department.getManager().getIdentityNumber());
        Assertions.assertEquals(newManager.getPhoneNumber(), department.getManager().getPhoneNumber());
        Assertions.assertEquals(newManager.getIdentityNumber(), department.getManager().getIdentityNumber());

        Assertions.assertEquals("System",department.getUpdatedBy());
        Assertions.assertNotNull(department.getUpdatedAt());

    }


    @Test
    void givenDepartmentId_whenDeleteDepartment_thenSoftDeleted() throws Exception {

        //Initialize
        Employee manager = employeeSavePort.save(Employee.builder()
                .firstName("Cemal")
                .lastName("Durmus")
                .identityNumber("515111714785")
                .email("cemalddd@example.com")
                .phoneNumber("05288811565")
                .address("Balıkesir")
                .birthDate(LocalDate.of(1985, 2, 15))
                .gender(Gender.MALE)
                .nationality("Denmark")
                .build());

        Department departmentSaved = departmentTestPort.save(Department.builder()
                .name("Teras Güvenlik")
                .status(DepartmentStatus.ACTIVE)
                .manager(manager)
                .build());

        //Given
        Long departmentId = departmentSaved.getId();

        //When
        MockHttpServletRequestBuilder deleteRequestBuilder = MockMvcRequestBuilders
                .delete(BASE_PATH + "/department/" + departmentId);

        //Then
        mockMvc.perform(deleteRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true));

        //Verify
        Optional<Department> deletedDepartment = departmentReadPort.findById(departmentId);

        Assertions.assertTrue(deletedDepartment.isPresent());

        Assertions.assertEquals(departmentId, deletedDepartment.get().getId());
        Assertions.assertEquals(departmentSaved.getName(), deletedDepartment.get().getName());
        Assertions.assertNotEquals(departmentSaved.getStatus(), deletedDepartment.get().getStatus());
        Assertions.assertEquals(departmentSaved.getManager().getPhoneNumber(), deletedDepartment.get().getManager().getPhoneNumber());
        Assertions.assertEquals(DepartmentStatus.DELETED, deletedDepartment.get().getStatus());

        Assertions.assertEquals("System",deletedDepartment.get().getUpdatedBy());
        Assertions.assertNotNull(deletedDepartment.get().getUpdatedAt());
        Assertions.assertEquals("System",deletedDepartment.get().getCreatedBy());
        Assertions.assertNotNull(deletedDepartment.get().getCreatedBy());

    }


    @Test
    void whenFindAllDepartments_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments")
                .param("page", "1")
                .param("pageSize", "10")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].status")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdAt")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].createdBy")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager.firstName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager.lastName")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager.identityNumber")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager.email")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager.phoneNumber")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager.address")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager.birthDate")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager.gender")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager.nationality")
                        .isString());

        //Then
        List<Department> departments = departmentReadPort.findAll(1, 10);

        Assertions.assertNotNull(departments);
        Assertions.assertFalse(departments.isEmpty());
        Assertions.assertNotNull(departments.get(0).getName());
        Assertions.assertNotNull(departments.get(0).getStatus());
        Assertions.assertNotNull(departments.get(0).getManager());
        Assertions.assertNotNull(departments.get(0).getCreatedAt());
        Assertions.assertNotNull(departments.get(0).getCreatedBy());
        Assertions.assertTrue(departments.size() > 1);
        Assertions.assertNotNull(departments.get(0).getManager().getFirstName());
        Assertions.assertNotNull(departments.get(0).getManager().getLastName());
        Assertions.assertNotNull(departments.get(0).getManager().getIdentityNumber());
        Assertions.assertNotNull(departments.get(0).getManager().getPhoneNumber());
        Assertions.assertNotNull(departments.get(0).getManager().getAddress());
        Assertions.assertNotNull(departments.get(0).getManager().getBirthDate());
        Assertions.assertNotNull(departments.get(0).getManager().getGender());
        Assertions.assertNotNull(departments.get(0).getManager().getNationality());

    }


    @Test
    void whenFindSummaryAllDepartments_thenReturnListAndVerifyContent() throws Exception {

        //When
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BASE_PATH + "/departments/summary")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isSuccess")
                        .value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.response")
                        .isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].id")
                        .isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].name")
                        .isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].status")
                        .doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response[0].manager")
                        .doesNotExist());

        //Then
        List<Department> departments = departmentReadPort.findSummaryAll();

        Assertions.assertNotNull(departments);
        Assertions.assertFalse(departments.isEmpty());
        Assertions.assertNotNull(departments.get(0).getName());
        Assertions.assertNotNull(departments.get(0).getId());

    }

}
