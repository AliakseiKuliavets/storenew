package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.dto.UserDto;
import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.exeption.checkMethods.Check;
import com.aliakseikul.storenew.exeption.exeptions.EmailExceptions;
import com.aliakseikul.storenew.exeption.exeptions.NumberExceptions;
import com.aliakseikul.storenew.exeption.exeptions.StringIsNullExceptions;
import com.aliakseikul.storenew.exeption.exeptions.UserNotFoundException;
import com.aliakseikul.storenew.exeption.message.ErrorMessage;
import com.aliakseikul.storenew.mapper.UserMapper;
import com.aliakseikul.storenew.repository.UserRepository;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.aliakseikul.storenew.exeption.checkMethods.Check.checkNumber;
import static com.aliakseikul.storenew.exeption.checkMethods.Check.valueNullOrEmpty;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserDto findById(String id) {
        if (Check.checkIdLength(id)) {
            throw new UserNotFoundException(ErrorMessage.WRONG_ID_LENGTH);
        }
        return userMapper.toDto(userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND)));
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException(ErrorMessage.NULL_OR_EMPTY);
        }
        User user = User.builder()
                .userFirstName(userDto.getUserFirstName())
                .userLastName(userDto.getUserLastName())
                .userPhoneNumber(userDto.getUserPhoneNumber())
                .userEmail(userDto.getUserEmail())
                .userVerifiedAccount(userDto.isVerifiedAccount())
                .build();
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public ResponseEntity<String> updateProductParamById(String userId, String property, String value) {
        if (checkId(userId)) {
            throw new UserNotFoundException(ErrorMessage.USER_NOT_FOUND);
        }
        if (valueNullOrEmpty(property) || valueNullOrEmpty(value)) {
            throw new StringIsNullExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
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
                    throw new NumberExceptions(ErrorMessage.WRONG_EMAIL);
                } else {
                    userRepository.changeEmailUserById(userUuid, value);
                    responseMessage = "set new email " + value;
                    break;
                }
            case "phonenumber":
                if (checkNumber(value)) {
                    throw new EmailExceptions(ErrorMessage.NUMBER_ERROR);
                } else {
                    userRepository.changePhoneNumberUserById(userUuid, value);
                    responseMessage = "set new phone number " + value;
                    break;
                }
            default:
                return ResponseEntity.badRequest().body("Invalid property: " + property);
        }
        return ResponseEntity.ok("User with ID " + userId + " " + responseMessage);
    }

    @Override
    public void deleteUserById(String userId) {
        if (checkId(userId)) {
            throw new UserNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        }
        userRepository.deleteById(UUID.fromString(userId));
    }

    private boolean checkEmail(String value) {
        if (valueNullOrEmpty(value)) {
            throw new EmailExceptions(ErrorMessage.NULL_OR_EMPTY);
        }
        String patter = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b";

        Pattern pattern = Pattern.compile(patter);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    private boolean checkId(String userId) {
        if (valueNullOrEmpty(userId)) {
            throw new UserNotFoundException(ErrorMessage.NULL_OR_EMPTY);
        }
        return findById(userId) == null;
    }
}
