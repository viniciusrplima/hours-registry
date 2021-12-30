package com.pacheco.hoursregistry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class DuplicityEntityException extends RuntimeException {
    public DuplicityEntityException(String message) {
        super(message);
    }
}
