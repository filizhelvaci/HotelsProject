package com.flz.model.mapper;

import com.flz.model.Employee;
import com.flz.model.request.EmployeeCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeCreateRequestToDomainMapper extends BaseMapper<EmployeeCreateRequest, Employee> {

    EmployeeCreateRequestToDomainMapper INSTANCE = Mappers.getMapper(EmployeeCreateRequestToDomainMapper.class);

}
