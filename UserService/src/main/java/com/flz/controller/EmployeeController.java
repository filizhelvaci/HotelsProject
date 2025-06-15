package com.flz.controller;

import com.flz.model.Employee;
import com.flz.model.response.HotelResponse;
import com.flz.service.EmployeeReadService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeReadService employeeReadService;

    @GetMapping("/employee/{id}")
    public HotelResponse<Employee> findById(@PathVariable(value = "id") @Positive Long id) {
        Employee employee = employeeReadService.findById(id);
        return HotelResponse.successOf(employee);
    }


}
