package com.flz.model.mapper;

import com.flz.model.EmployeeOldExperience;
import com.flz.model.entity.EmployeeOldExperienceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeOldExperienceEntityToDomainMapper extends BaseMapper<EmployeeOldExperienceEntity, EmployeeOldExperience> {

    EmployeeOldExperienceEntityToDomainMapper INSTANCE = Mappers.getMapper(EmployeeOldExperienceEntityToDomainMapper.class);

}
