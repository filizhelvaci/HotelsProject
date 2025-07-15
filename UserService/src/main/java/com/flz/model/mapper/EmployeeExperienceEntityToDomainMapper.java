package com.flz.model.mapper;

import com.flz.model.EmployeeExperience;
import com.flz.model.entity.EmployeeExperienceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeExperienceEntityToDomainMapper extends BaseMapper<EmployeeExperienceEntity, EmployeeExperience> {

    EmployeeExperienceEntityToDomainMapper INSTANCE = Mappers.getMapper(EmployeeExperienceEntityToDomainMapper.class);

}
