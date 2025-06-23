package com.flz.model.mapper;

import com.flz.model.Employee;
import com.flz.model.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeEntityToDomainMapper extends BaseMapper<EmployeeEntity, Employee> {

    EmployeeEntityToDomainMapper INSTANCE = Mappers.getMapper(EmployeeEntityToDomainMapper.class);

    @Mapping(target = "experiences", ignore = true)
    Employee map(EmployeeEntity source);

}
