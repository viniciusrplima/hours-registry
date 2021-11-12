package com.pacheco.hoursregistry.exception;

public class AuthenticationErrorException extends RuntimeException {

    public AuthenticationErrorException(String message) {
        super(message);
    }
}
