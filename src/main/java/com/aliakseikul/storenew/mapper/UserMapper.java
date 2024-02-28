package com.aliakseikul.storenew.mapper;

import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userVerifiedAccount", target = "verifiedAccount")
    UserDto toDto(User byId);
}
