package io.github.GerdanyJr.Authentication.service.impl;

import org.springframework.stereotype.Service;

import io.github.GerdanyJr.Authentication.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
