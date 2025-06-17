package com.flz.model.mapper;

import com.flz.model.Employee;
import com.flz.model.response.EmployeeSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeToEmployeeSummaryResponseMapper extends BaseMapper<Employee, EmployeeSummaryResponse> {

    EmployeeToEmployeeSummaryResponseMapper INSTANCE = Mappers.getMapper(EmployeeToEmployeeSummaryResponseMapper.class);

}
