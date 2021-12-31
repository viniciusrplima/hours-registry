package com.pacheco.hoursregistry.controller;

import com.pacheco.hoursregistry.dto.UserDTO;
import com.pacheco.hoursregistry.model.RoleTypes;
import com.pacheco.hoursregistry.model.User;
import com.pacheco.hoursregistry.repository.UserRepository;
import com.pacheco.hoursregistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody UserDTO userDto) {
        return userService.register(userDto, userDto.getRoles());
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerCommonUser(@RequestBody UserDTO userDto) {
        return userService.register(userDto, List.of(RoleTypes.USER));
    }

}
