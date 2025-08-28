package com.account.service.mapper;

import com.account.service.domain.User;
import com.account.service.dto.RegisterResponseDTO;
import com.account.service.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponseDTO UserToUserResponseDTO(User user);
    @Mapping(target = "username", source = "email" )
    RegisterResponseDTO UserToRegisterResponseDTO(User user);
}
