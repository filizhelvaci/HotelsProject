package com.flz.model.mapper;

import com.flz.model.EmployeeExperience;
import com.flz.model.response.EmployeeExperienceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeExperienceToResponseMapper extends BaseMapper<EmployeeExperience, EmployeeExperienceResponse> {

    EmployeeExperienceToResponseMapper INSTANCE = Mappers.getMapper(EmployeeExperienceToResponseMapper.class);

    @Mapping(target = "positionName", source = "position.name")
    @Mapping(target = "departmentName", source = "position.department.name")
    @Mapping(target = "managerName", expression = "java(source.position.department.getManager().getFirstName() + \" \" + source.position.department.getManager().getLastName())")
    EmployeeExperienceResponse map(EmployeeExperience source);

}
