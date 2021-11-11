package com.pacheco.hoursregistry.controller;

import com.pacheco.hoursregistry.model.User;
import com.pacheco.hoursregistry.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

}
