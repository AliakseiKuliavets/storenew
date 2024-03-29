package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.config.service.JwtService;
import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.dto.auth.AuthenticationRequest;
import com.aliakseikul.storenew.dto.auth.AuthenticationResponse;
import com.aliakseikul.storenew.dto.auth.RegisterRequest;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.UserRole;
import com.aliakseikul.storenew.exception.exeptions.UserNotFoundException;
import com.aliakseikul.storenew.exception.exeptions.UserRoleNotFoundException;
import com.aliakseikul.storenew.exception.message.ErrorMessage;
import com.aliakseikul.storenew.mapper.UserMapper;
import com.aliakseikul.storenew.repository.UserRepository;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService service;

    private final AuthenticationManager manager;

    private final UserMapper userMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public UserDto findById(String id) {
        return userMapper.toDto(userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND)));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public User addUser(UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            throw new IllegalArgumentException(ErrorMessage.NULL_OR_EMPTY);
        }
        User userCheck = userRepository.findUserByNickName(userCreateDto.getUserNickname()).orElse(null);
        if (userCheck != null) {
            throw new UserNotFoundException(ErrorMessage.USER_WITH_NAME);
        }
        User user = User.builder()
                .userNickname(userCreateDto.getUserNickname())
                .userPassword(userCreateDto.getUserPassword())
                .userFirstName(userCreateDto.getUserFirstName())
                .userLastName(userCreateDto.getUserLastName())
                .userEmail(userCreateDto.getUserEmail())
                .userRole(UserRole.USER)
                .build();
        return userRepository.save(user);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        if (userRepository.findUserByNickName(request.getUserNickname()).isPresent()) {
            throw new UserNotFoundException(ErrorMessage.USER_WITH_NAME);
        }
        User user = User.builder()
                .userFirstName(request.getUserFirstName())
                .userLastName(request.getUserLastName())
                .userNickname(request.getUserNickname())
                .userEmail(request.getUserEmail())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .userRole(UserRole.USER)
                .build();
        userRepository.save(user);
        var jwtToken = service.generateToken(user);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepository.findUserByNickName(principal.getName()).orElse(new User());
    }

    @Override
    public void authenticate(AuthenticationRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserNickname(),
                        request.getUserPassword()
                )
        );
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public ResponseEntity<String> changeRole(String userId, String userRole) {
        String userRoleUp = userRole.toUpperCase();
        if (checkRole(userRoleUp)) {
            throw new UserRoleNotFoundException(ErrorMessage.ROLE_NOT_FOUND);
        }
        findById(userId);
        userRepository.changeRole(UUID.fromString(userId), UserRole.valueOf(userRoleUp));
        return ResponseEntity.ok("User with ID " + userId + " has change user role to " + userRole);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteUserById(String userId) {
        findById(userId);
        userRepository.deleteById(UUID.fromString(userId));
    }

    private boolean checkRole(String userRoleUp) {
        return !(UserRole.getListRole().contains(userRoleUp));
    }
}
