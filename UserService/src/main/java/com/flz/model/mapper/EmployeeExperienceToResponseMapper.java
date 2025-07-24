package com.flz.model.mapper;

import com.flz.model.EmployeeExperience;
import com.flz.model.response.EmployeeExperienceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeExperienceToResponseMapper extends BaseMapper<EmployeeExperience, EmployeeExperienceResponse> {

    EmployeeExperienceToResponseMapper INSTANCE = Mappers.getMapper(EmployeeExperienceToResponseMapper.class);

    @Mapping(target = "positionId", source = "position.id")
    @Mapping(target = "positionName", source = "position.name")
    @Mapping(target = "supervisorId", source = "supervisor.id")
    @Mapping(target = "supervisorName", expression = "java(source.getSupervisor().getFirstName() + \" \" + source.getSupervisor().getLastName())")
    EmployeeExperienceResponse map(EmployeeExperience source);

}
