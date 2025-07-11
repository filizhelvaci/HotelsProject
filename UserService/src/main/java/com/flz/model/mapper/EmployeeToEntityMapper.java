package com.flz.model.mapper;

import com.flz.model.Employee;
import com.flz.model.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeToEntityMapper extends BaseMapper<Employee, EmployeeEntity> {

    EmployeeToEntityMapper INSTANCE = Mappers.getMapper(EmployeeToEntityMapper.class);

    @Mapping(target = "experiences", ignore = true)
    EmployeeEntity map(Employee source);

}
