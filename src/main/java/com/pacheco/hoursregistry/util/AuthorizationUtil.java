package com.pacheco.hoursregistry.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationUtil {

    public static String currentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
