package com.flz.controller;

import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.response.DepartmentResponse;
import com.flz.model.response.HotelResponse;
import com.flz.service.DepartmentCreateService;
import com.flz.service.DepartmentReadService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class DepartmentController {

    private final DepartmentReadService departmentReadService;
    private final DepartmentCreateService departmentCreateService;

    @GetMapping("/department/{id}")
    public HotelResponse<DepartmentResponse> findById(@PathVariable(value = "id") @Positive Long id) {
        DepartmentResponse departmentResponse = departmentReadService.findById(id);
        return HotelResponse.successOf(departmentResponse);
    }


    @PostMapping("/department")
    public HotelResponse<Void> create(@RequestBody @Valid DepartmentCreateRequest createRequest) {
        departmentCreateService.create(createRequest);
        return HotelResponse.success();
    }

}
