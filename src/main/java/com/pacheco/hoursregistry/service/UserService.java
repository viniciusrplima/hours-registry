package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.dto.UserDTO;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.User;

import java.util.List;

public interface UserService {

    public User find(String username) throws NoEntityFoundException;

    public User register(UserDTO userDto);

}
