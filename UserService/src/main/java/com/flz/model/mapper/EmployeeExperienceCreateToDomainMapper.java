package com.flz.model.mapper;

import com.flz.model.EmployeeExperience;
import com.flz.model.request.EmployeeExperienceCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeExperienceCreateToDomainMapper extends BaseMapper<EmployeeExperienceCreateRequest, EmployeeExperience> {

    EmployeeExperienceCreateToDomainMapper INSTANCE = Mappers.getMapper(EmployeeExperienceCreateToDomainMapper.class);

}
