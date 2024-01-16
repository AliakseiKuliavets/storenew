package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.entity.User;

public interface UserService {

    User findById(String id);

    User addUser(User user);

    void deleteUserById(String userId);
}
