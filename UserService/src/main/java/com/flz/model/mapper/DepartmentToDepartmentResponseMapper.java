package com.flz.model.mapper;

import com.flz.model.Department;
import com.flz.model.response.DepartmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentToDepartmentResponseMapper extends BaseMapper<Department, DepartmentResponse> {

    DepartmentToDepartmentResponseMapper INSTANCE = Mappers.getMapper(DepartmentToDepartmentResponseMapper.class);

}
