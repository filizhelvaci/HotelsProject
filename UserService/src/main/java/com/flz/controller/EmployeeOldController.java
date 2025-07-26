package com.flz.controller;

import com.flz.model.EmployeeOld;
import com.flz.model.request.PageRequest;
import com.flz.model.response.EmployeeOldDetailsResponse;
import com.flz.model.response.HotelResponse;
import com.flz.service.EmployeeOldReadService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmployeeOldController {

    private final EmployeeOldReadService employeeOldReadService;

    @GetMapping("/employee-old/{id}")
    public HotelResponse<EmployeeOldDetailsResponse> findById(@PathVariable(value = "id") @Positive Long id) {
        EmployeeOldDetailsResponse employeeOldDetailsResponse = employeeOldReadService.findById(id);
        return HotelResponse.successOf(employeeOldDetailsResponse);
    }

    @GetMapping("/employees-old")
    public HotelResponse<List<EmployeeOld>> findAll(@Valid PageRequest pageRequest) {
        List<EmployeeOld> employeesOld = employeeOldReadService.findAll(pageRequest.getPage(), pageRequest.getPageSize());
        return HotelResponse.successOf(employeesOld);
    }

}
