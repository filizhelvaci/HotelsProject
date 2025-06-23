package com.flz.controller;


import com.flz.model.request.EmployeeExperienceCreateRequest;
import com.flz.model.response.HotelResponse;
import com.flz.service.EmployeeExperienceCreateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class EmployeeExperienceController {

    private final EmployeeExperienceCreateService employeeExperienceCreateService;

    @PostMapping("/employee/{id}/experience")
    public HotelResponse<Void> create(@PathVariable(value = "id") @Positive Long id, @RequestBody @Valid EmployeeExperienceCreateRequest createRequest) {
        employeeExperienceCreateService.create(id, createRequest);
        return HotelResponse.success();
    }

}
