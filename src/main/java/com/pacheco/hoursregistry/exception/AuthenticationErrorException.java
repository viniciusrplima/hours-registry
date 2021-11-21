package com.pacheco.hoursregistry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationErrorException extends RuntimeException {

    public AuthenticationErrorException(String message) {
        super(message);
    }
}
