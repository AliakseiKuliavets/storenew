package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.dto.auth.AuthenticationRequest;
import com.aliakseikul.storenew.dto.auth.RegisterRequest;
import com.aliakseikul.storenew.entity.User;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface UserService {

    UserDto findById(String id);

    User addUser(UserCreateDto userDto);

    User getUserByPrincipal(Principal principal);

    ResponseEntity<?> register(RegisterRequest request);

    void authenticate(AuthenticationRequest request);


    ResponseEntity<String> changeRole(String userId, String userRole);

    void deleteUserById(String userId);

}
