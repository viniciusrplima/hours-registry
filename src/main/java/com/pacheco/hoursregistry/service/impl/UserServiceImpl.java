package com.pacheco.hoursregistry.service.impl;

import com.pacheco.hoursregistry.dto.UserDTO;
import com.pacheco.hoursregistry.exception.DuplicityEntityException;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Role;
import com.pacheco.hoursregistry.model.User;
import com.pacheco.hoursregistry.repository.RoleRepository;
import com.pacheco.hoursregistry.repository.UserRepository;
import com.pacheco.hoursregistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User find(String username) throws NoEntityFoundException {
        Optional<User> opUser = userRepository.findByUsername(username);

        if (opUser.isEmpty()) {
            throw new NoEntityFoundException("Couldn't found user with username: " + username);
        }

        return opUser.get();
    }

    @Override
    public User register(UserDTO userDto, List<String> roleNames) throws DataIntegrityViolationException {

        try {
            List<Role> roles = roleRepository.findByNameIn(roleNames);
            String passwordEncoded = passwordEncoder.encode(userDto.getPassword());
            
            User user = new User(userDto.getUsername(), passwordEncoded, userDto.getGithubToken(), roles);

            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicityEntityException(String.format("Username %s already exists", userDto.getUsername()));
        }
    }

}
