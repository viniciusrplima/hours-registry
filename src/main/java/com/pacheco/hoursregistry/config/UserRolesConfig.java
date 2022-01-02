package com.pacheco.hoursregistry.config;

import com.pacheco.hoursregistry.model.Role;
import com.pacheco.hoursregistry.model.RoleTypes;
import com.pacheco.hoursregistry.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserRolesConfig {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void setupRoles() {
        try {
            roleRepository.save(new Role(RoleTypes.ADMIN));
            roleRepository.save(new Role(RoleTypes.USER));
        }
        catch (DataIntegrityViolationException e) {
        }
    }

}
