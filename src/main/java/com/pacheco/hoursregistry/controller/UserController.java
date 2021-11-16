package com.pacheco.hoursregistry.controller;

import com.pacheco.hoursregistry.dto.UserDTO;
import com.pacheco.hoursregistry.exception.DuplicityEntityException;
import com.pacheco.hoursregistry.model.RoleTypes;
import com.pacheco.hoursregistry.model.User;
import com.pacheco.hoursregistry.repository.UserRepository;
import com.pacheco.hoursregistry.service.UserService;
import com.pacheco.hoursregistry.util.CustomErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDto) {
        try {
            User user = userService.register(userDto, userDto.getRoles());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (DuplicityEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomErrorMessage(e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCommonUser(@RequestBody UserDTO userDto) {
        try {
            User user = userService.register(userDto, List.of(RoleTypes.USER));
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (DuplicityEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new CustomErrorMessage(e.getMessage()));
        }
    }

}
