package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.entity.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    User findById(String id);

    User addUser(User user);

    ResponseEntity<String> updateProductParamById(String userId, String property, String value);

    void deleteUserById(String userId);

}
