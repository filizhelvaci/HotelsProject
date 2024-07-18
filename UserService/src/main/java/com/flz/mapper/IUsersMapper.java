package com.flz.mapper;

import com.flz.dto.request.DoCustomerRegisterRequestDto;
import com.flz.dto.request.DoEmployeeRegisterRequestDto;
import com.flz.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUsersMapper {

    IUsersMapper INSTANCE= Mappers.getMapper(IUsersMapper.class);
    Users toUserE(final DoEmployeeRegisterRequestDto dto);

    Users toUserC(final DoCustomerRegisterRequestDto dto);


}
