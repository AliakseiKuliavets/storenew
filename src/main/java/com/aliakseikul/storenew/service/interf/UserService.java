package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    UserDto findById(String id);

    UserDto addUser(UserCreateDto userDto);

    ResponseEntity<String> updateProductParamById(String userId, String property, String value);

    void deleteUserById(String userId);

}
