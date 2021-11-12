package com.pacheco.hoursregistry.service.impl;

import com.pacheco.hoursregistry.dto.UserDTO;
import com.pacheco.hoursregistry.exception.DuplicityEntityException;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.User;
import com.pacheco.hoursregistry.repository.UserRepository;
import com.pacheco.hoursregistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User find(String username) throws NoEntityFoundException {
        Optional<User> opUser = repository.findByUsername(username);

        if (opUser.isEmpty()) {
            throw new NoEntityFoundException("Couldn't found user with username: " + username);
        }

        return opUser.get();
    }

    @Override
    public User register(UserDTO userDto) throws DataIntegrityViolationException {
        try {
            String passwordEncoded = passwordEncoder.encode(userDto.getPassword());
            User user = new User(userDto.getUsername(), passwordEncoded, userDto.getGithubToken());

            return repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicityEntityException(String.format("Username %s already exists", userDto.getUsername()));
        }
    }
}
