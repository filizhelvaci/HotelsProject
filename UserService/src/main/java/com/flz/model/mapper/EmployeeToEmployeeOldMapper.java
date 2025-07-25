package com.flz.model.mapper;

import com.flz.model.Employee;
import com.flz.model.EmployeeOld;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeToEmployeeOldMapper extends BaseMapper<Employee, EmployeeOld> {

    EmployeeToEmployeeOldMapper INSTANCE = Mappers.getMapper(EmployeeToEmployeeOldMapper.class);

}
