package com.flz.controller;

import com.flz.model.Employee;
import com.flz.model.request.EmployeeCreateRequest;
import com.flz.model.request.EmployeeUpdateRequest;
import com.flz.model.request.PageRequest;
import com.flz.model.response.EmployeeDetailsResponse;
import com.flz.model.response.EmployeeSummaryResponse;
import com.flz.model.response.HotelResponse;
import com.flz.service.EmployeeReadService;
import com.flz.service.EmployeeWriteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class EmployeeController {

    private final EmployeeReadService employeeReadService;
    private final EmployeeWriteService employeeWriteService;

    @GetMapping("/employee/{id}")
    public HotelResponse<EmployeeDetailsResponse> findById(@PathVariable(value = "id") @Positive Long id) {
        EmployeeDetailsResponse employeeDetailsResponse = employeeReadService.findById(id);
        return HotelResponse.successOf(employeeDetailsResponse);
    }

    @GetMapping("/employees")
    public HotelResponse<List<Employee>> findAll(@Valid PageRequest pageRequest) {
        List<Employee> employees = employeeReadService.findAll(pageRequest.getPage(), pageRequest.getPageSize());
        return HotelResponse.successOf(employees);
    }

    @GetMapping("/employees/summary")
    public HotelResponse<List<EmployeeSummaryResponse>> findSummaryAll() {
        List<EmployeeSummaryResponse> employeeSummaryResponses = employeeReadService.findSummaryAll();
        return HotelResponse.successOf(employeeSummaryResponses);
    }

    @PostMapping("/employee")
    public HotelResponse<Void> create(@RequestBody @Valid EmployeeCreateRequest createRequest) {
        employeeWriteService.create(createRequest);
        return HotelResponse.success();
    }

    @PutMapping("/employee/{id}")
    public HotelResponse<Void> update(@PathVariable(value = "id") @Positive Long id,
                                      @RequestBody @Valid EmployeeUpdateRequest employeeUpdateRequest) {
        employeeWriteService.update(id, employeeUpdateRequest);
        return HotelResponse.success();
    }

    @DeleteMapping("/employee/{id}")
    public HotelResponse<Void> delete(@PathVariable(value = "id") @Positive Long id) {
        employeeWriteService.delete(id);
        return HotelResponse.success();
    }

}
