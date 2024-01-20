package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.entity.enums.ProductBrand;
import com.aliakseikul.storenew.entity.enums.ProductCategory;
import com.aliakseikul.storenew.exeption.exeptions.*;
import com.aliakseikul.storenew.exeption.message.ErrorMessage;
import com.aliakseikul.storenew.repository.UserRepository;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(String id) {
        checkIdLength(id);
        return userRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<String> updateProductParamById(String userId, String property, String value) {
        checkId(userId);
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
                userRepository.changeEmailUserById(userUuid, value);
                responseMessage = "set new email " + value;
                break;
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
    public void deleteUserById(String userId) {
        checkId(userId);
        userRepository.deleteById(UUID.fromString(userId));
    }

    private boolean valueNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private void checkIdLength(String productId) {
        if (valueNullOrEmpty(productId)) {
            throw new ProductNotFoundException(ErrorMessage.NULL_OR_EMPTY);
        }
        if (productId.length() != 36) {
            throw new ProductNotFoundException(ErrorMessage.WRONG_ID_LENGTH);
        }
    }

    private void checkId(String productId) {
        if (findById(productId) == null) {
            throw new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND);
        }
    }
}
