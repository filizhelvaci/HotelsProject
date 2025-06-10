package com.flz.model.mapper;

import com.flz.model.Department;
import com.flz.model.entity.DepartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentEntityToDomainMapper extends BaseMapper<DepartmentEntity, Department> {

    DepartmentEntityToDomainMapper INSTANCE = Mappers.getMapper(DepartmentEntityToDomainMapper.class);

}
