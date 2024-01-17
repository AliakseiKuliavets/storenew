package com.aliakseikul.storenew.service.interf;

import com.aliakseikul.storenew.entity.User;

public interface UserService {

    User findById(String id);

    User addUser(User user);

    void changeUserNameById(String userId, String userName);

    void changeLastNameUserById(String userId, String userLastName);

    void changeEmailUserById(String userId, String email);

    void changePhoneNumberUserById(String userId, String phoneNumber);

    void deleteUserById(String userId);

}
