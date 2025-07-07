package com.flz.controller;

import com.flz.model.Department;
import com.flz.model.request.DepartmentCreateRequest;
import com.flz.model.request.DepartmentUpdateRequest;
import com.flz.model.request.PageRequest;
import com.flz.model.response.DepartmentSummaryResponse;
import com.flz.model.response.HotelResponse;
import com.flz.service.DepartmentReadService;
import com.flz.service.DepartmentWriteService;
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
class DepartmentController {

    private final DepartmentReadService departmentReadService;
    private final DepartmentWriteService departmentWriteService;

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
        departmentWriteService.create(createRequest);
        return HotelResponse.success();
    }

    @PutMapping("/department/{id}")
    public HotelResponse<Void> update(@PathVariable(value = "id") @Positive Long id,
                                      @RequestBody @Valid DepartmentUpdateRequest departmentUpdateRequest) {
        departmentWriteService.update(id, departmentUpdateRequest);
        return HotelResponse.success();
    }

    @DeleteMapping("/department/{id}")
    public HotelResponse<Void> delete(@PathVariable(value = "id") @Positive Long id) {
        departmentWriteService.delete(id);
        return HotelResponse.success();
    }

}
