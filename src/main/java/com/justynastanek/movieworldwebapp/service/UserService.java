package com.justynastanek.movieworldwebapp.service;

import com.justynastanek.movieworldwebapp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepo userRepository;

    @Autowired
    public UserService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public long getUserByUsername(String username) {
        return userRepository.findByUsername(username).get().getUserId();
    }
}
