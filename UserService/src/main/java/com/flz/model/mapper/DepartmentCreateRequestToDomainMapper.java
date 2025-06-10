package com.flz.model.mapper;

import com.flz.model.Department;
import com.flz.model.request.DepartmentCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentCreateRequestToDomainMapper extends BaseMapper<DepartmentCreateRequest, Department> {

    DepartmentCreateRequestToDomainMapper INSTANCE = Mappers.getMapper(DepartmentCreateRequestToDomainMapper.class);

}
