package com.pacheco.hoursregistry.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtil {

    public String currentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
