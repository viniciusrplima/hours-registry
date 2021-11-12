package com.pacheco.hoursregistry.config;

import com.pacheco.hoursregistry.dto.UserDTO;
import com.pacheco.hoursregistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdminCredentialsConfig {

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void setupAdminCredentials() {
        UserDTO user = new UserDTO(username, password, null);
        userService.register(user);
    }

}
