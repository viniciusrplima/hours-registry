package com.pacheco.hoursregistry.util;

import com.pacheco.hoursregistry.dto.UserDTO;
import com.pacheco.hoursregistry.model.RoleTypes;
import com.pacheco.hoursregistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@DependsOn("userRolesConfig")
public class DummyData {

    @Autowired
    private UserService userService;

    @PostConstruct
    public void setupUsers() {
        UserDTO userDto = new UserDTO("vinicius", "vinicius123", null);
        userService.register(userDto, List.of(RoleTypes.USER));
    }

}
