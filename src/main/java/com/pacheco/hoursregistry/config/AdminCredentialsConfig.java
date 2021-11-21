package com.pacheco.hoursregistry.config;

import com.pacheco.hoursregistry.dto.UserDTO;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.RoleTypes;
import com.pacheco.hoursregistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@DependsOn("userRolesConfig")
public class AdminCredentialsConfig {

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Value("${github.auth.token}")
    private String githubToken;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void setupAdminCredentials() {
        try {
            userService.find(username);
        } catch (NoEntityFoundException e) {
            UserDTO user = new UserDTO(username, password, githubToken);
            userService.register(user, List.of(RoleTypes.ADMIN, RoleTypes.USER));
        }
    }

}
