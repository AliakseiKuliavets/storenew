package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.repository.UserRepository;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(String id) {
        return userRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeUserNameById(String userId, String userName) {
        userRepository.changeUserNameById(UUID.fromString(userId),userName);
    }

    @Override
    @Transactional
    public void changeLastNameUserById(String userId, String userLastName) {
        userRepository.changeLastNameUserById(UUID.fromString(userId),userLastName);
    }

    @Override
    @Transactional
    public void changeEmailUserById(String userId, String email) {
        userRepository.changeEmailUserById(UUID.fromString(userId),email);
    }

    @Override
    @Transactional
    public void changePhoneNumberUserById(String userId, String phoneNumber) {
        userRepository.changePhoneNumberUserById(UUID.fromString(userId),phoneNumber);
    }

    @Override
    public void deleteUserById(String userId) {
        userRepository.deleteById(UUID.fromString(userId));
    }

}
