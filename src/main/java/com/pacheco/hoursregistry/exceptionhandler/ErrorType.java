package com.pacheco.hoursregistry.exceptionhandler;

public enum ErrorType {

    ENTITY_NOT_FOUND("Entity not found"),
    INTERNAL_ERROR("Internal error");

    private String title;

    ErrorType(String title) {
        this.title = title;
    }
}
