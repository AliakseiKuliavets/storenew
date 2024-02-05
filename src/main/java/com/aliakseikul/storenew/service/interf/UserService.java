package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    UserDto findById(String id);

    User addUser(UserCreateDto userDto);

    ResponseEntity<String> updateProductParamById(String userId, String property, String value);

    ResponseEntity<String> changeRole(String userId, String userRole);

    void deleteUserById(String userId);

}
