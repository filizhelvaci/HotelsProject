package com.flz.model.mapper;

import com.flz.model.EmployeeOldExperience;
import com.flz.model.response.EmployeeOldExperienceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeOldExperienceToResponseMapper extends BaseMapper<EmployeeOldExperience, EmployeeOldExperienceResponse> {

    EmployeeOldExperienceToResponseMapper INSTANCE = Mappers.getMapper(EmployeeOldExperienceToResponseMapper.class);

    @Mapping(target = "positionName", source = "position.name")
    @Mapping(target = "departmentName", source = "position.department.name")
    @Mapping(target = "supervisorName", expression = "java(source.getSupervisor().getFirstName() + \" \" + source.getSupervisor().getLastName())")
    EmployeeOldExperienceResponse map(EmployeeOldExperience source);

}
