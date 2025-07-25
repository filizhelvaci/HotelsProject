package com.flz.model.mapper;

import com.flz.model.EmployeeOld;
import com.flz.model.entity.EmployeeOldEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeOldToEntityMapper extends BaseMapper<EmployeeOld, EmployeeOldEntity> {

    EmployeeOldToEntityMapper INSTANCE = Mappers.getMapper(EmployeeOldToEntityMapper.class);

}
