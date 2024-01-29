package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.dto.UserCreateDto;
import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.UserRole;
import com.aliakseikul.storenew.exception.exeptions.EmailExceptions;
import com.aliakseikul.storenew.exception.exeptions.UserNotFoundException;
import com.aliakseikul.storenew.exception.exeptions.UserRoleNotFoundException;
import com.aliakseikul.storenew.exception.message.ErrorMessage;
import com.aliakseikul.storenew.mapper.UserMapper;
import com.aliakseikul.storenew.repository.UserRepository;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto findById(String id) {
        return userMapper.toDto(userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND)));
    }

    @Override
    public User addUser(UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            throw new IllegalArgumentException(ErrorMessage.NULL_OR_EMPTY);
        }
        User userCheck = userRepository.findUserByNickName(userCreateDto.getUserNickname());
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

    @Override
    @Transactional
    public ResponseEntity<String> updateProductParamById(String userId, String property, String value) {
        findById(userId);
        UUID userUuid = UUID.fromString(userId);

        String responseMessage;
        switch (property.toLowerCase()) {
            case "name":
                userRepository.changeUserNameById(userUuid, value);
                responseMessage = "set new name " + value;
                break;
            case "lastname":
                userRepository.changeLastNameUserById(userUuid, value);
                responseMessage = "set new lastname " + value;
                break;
            case "email":
                if (checkEmail(value)) {
                    throw new EmailExceptions(ErrorMessage.WRONG_EMAIL);
                } else {
                    userRepository.changeEmailUserById(userUuid, value);
                    responseMessage = "set new email " + value;
                    break;
                }
            case "phonenumber":
                userRepository.changePhoneNumberUserById(userUuid, value);
                responseMessage = "set new phone number " + value;
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid property: " + property);
        }
        return ResponseEntity.ok("User with ID " + userId + " " + responseMessage);
    }

    @Override
    @Transactional
    public ResponseEntity<String> changeRole(String userId, String userRole) {
        String userRoleUp = userRole.toUpperCase();
        if (checkRole(userRoleUp)) {
            throw new UserRoleNotFoundException(ErrorMessage.ROLE_NOT_FOUND);
        }
        userRepository.changeRole(UUID.fromString(userId), UserRole.valueOf(userRoleUp));
        return ResponseEntity.ok("User with ID " + userId + " has change user role to " + userRole);
    }

    @Override
    public void deleteUserById(String userId) {
        findById(userId);
        userRepository.deleteById(UUID.fromString(userId));
    }

    private boolean checkEmail(String value) {
        String patter = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";

        Pattern pattern = Pattern.compile(patter);
        Matcher matcher = pattern.matcher(value);
        return !(matcher.matches());
    }

    private boolean checkRole(String userRoleUp) {
        return !(UserRole.getListRole().contains(userRoleUp));
    }
}
