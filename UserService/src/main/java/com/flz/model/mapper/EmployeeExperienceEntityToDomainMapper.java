package com.flz.model.mapper;

import com.flz.model.EmployeeExperience;
import com.flz.model.entity.EmployeeExperienceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeExperienceEntityToDomainMapper extends BaseMapper<EmployeeExperienceEntity, EmployeeExperience> {

    EmployeeExperienceEntityToDomainMapper INSTANCE = Mappers.getMapper(EmployeeExperienceEntityToDomainMapper.class);

    @Mapping(target = "employee", ignore = true)
    EmployeeExperience map(EmployeeExperienceEntity source);

}
