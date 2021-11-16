package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.AuthUserDetails;
import com.pacheco.hoursregistry.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;

        try {
            user = userService.find(username);
        } catch (NoEntityFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        if (user.getUsername().equals(username)) {
            return new AuthUserDetails(user);
        } else {
            throw new UsernameNotFoundException("Couldn't found user with username: " + username);
        }
    }
}
