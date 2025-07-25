package com.flz.model.mapper;

import com.flz.model.EmployeeOldExperience;
import com.flz.model.entity.EmployeeOldExperienceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeOldExperienceToEntityMapper extends BaseMapper<EmployeeOldExperience, EmployeeOldExperienceEntity> {

    EmployeeOldExperienceToEntityMapper INSTANCE = Mappers.getMapper(EmployeeOldExperienceToEntityMapper.class);

}
