package com.aliakseikul.storenew.mapper;

import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User byId);
}
