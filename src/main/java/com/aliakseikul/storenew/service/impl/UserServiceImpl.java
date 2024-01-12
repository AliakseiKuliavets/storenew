package com.aliakseikul.storenew.service.impl;

import com.aliakseikul.storenew.entity.User;
import com.aliakseikul.storenew.repository.UserRepository;
import com.aliakseikul.storenew.service.interf.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(String id) {
        return userRepository.findById(UUID.fromString(id)).orElse(null);
    }
}
