package com.flz.model.mapper;

import com.flz.model.Department;
import com.flz.model.response.DepartmentSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentToDepartmentSummaryResponseMapper extends BaseMapper<Department, DepartmentSummaryResponse> {

    DepartmentToDepartmentSummaryResponseMapper INSTANCE = Mappers.getMapper(DepartmentToDepartmentSummaryResponseMapper.class);

}
