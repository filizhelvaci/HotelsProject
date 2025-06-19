package com.flz.controller;


import com.flz.model.request.EmployeeExperienceCreateRequest;
import com.flz.model.response.HotelResponse;
import com.flz.service.EmployeeExperienceCreateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class EmployeeExperienceController {

    EmployeeExperienceCreateService employeeExperienceCreateService;

    @PostMapping("/employee")
    public HotelResponse<Void> create(@RequestBody @Valid EmployeeExperienceCreateRequest createRequest, @Positive Long id) {
        employeeExperienceCreateService.create(id, createRequest);
        return HotelResponse.success();
    }

}
