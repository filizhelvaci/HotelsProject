package com.flz.controller;

import com.flz.model.Department;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.model.request.PageRequest;
import com.flz.model.response.DepartmentSummaryResponse;
import com.flz.model.response.HotelResponse;
import com.flz.service.DepartmentCreateService;
import com.flz.service.DepartmentReadService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
class DepartmentController {

    private final DepartmentReadService departmentReadService;
    private final DepartmentCreateService departmentCreateService;

    @GetMapping("/departments")
    public HotelResponse<List<Department>> findAll(@Valid PageRequest pageRequest) {
        List<Department> department = departmentReadService.findAll(
                pageRequest.getPage(),
                pageRequest.getPageSize()
        );
        return HotelResponse.successOf(department);
    }

    @GetMapping("/departments/summary")
    public HotelResponse<List<DepartmentSummaryResponse>> findSummaryAll() {
        List<DepartmentSummaryResponse> departmentSummaryResponses = departmentReadService.findSummaryAll();
        return HotelResponse.successOf(departmentSummaryResponses);
    }

    @PostMapping("/department")
    public HotelResponse<Void> create(@RequestBody @Valid DepartmentCreateRequest createRequest) {
        departmentCreateService.create(createRequest);
        return HotelResponse.success();
    }

    @PutMapping("/department/{id}")
    public HotelResponse<Void> update(@PathVariable(value = "id") @Positive Long id
            , @RequestBody @Valid DepartmentUpdateRequest departmentUpdateRequest) {
        departmentCreateService.update(id, departmentUpdateRequest);
        return HotelResponse.success();
    }

    @DeleteMapping("/department/{id}")
    public HotelResponse<Void> delete(@PathVariable(value = "id") @Positive Long id) {
        departmentCreateService.delete(id);
        return HotelResponse.success();
    }

}
