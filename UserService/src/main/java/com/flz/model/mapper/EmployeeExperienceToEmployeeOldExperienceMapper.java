package com.flz.model.mapper;

import com.flz.model.EmployeeExperience;
import com.flz.model.EmployeeOldExperience;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeExperienceToEmployeeOldExperienceMapper extends BaseMapper<EmployeeExperience, EmployeeOldExperience> {

    @Mapping(target = "employeeOld", ignore = true)
    EmployeeOldExperience map(EmployeeExperience source);

    EmployeeExperienceToEmployeeOldExperienceMapper INSTANCE = Mappers.getMapper(EmployeeExperienceToEmployeeOldExperienceMapper.class);

}
