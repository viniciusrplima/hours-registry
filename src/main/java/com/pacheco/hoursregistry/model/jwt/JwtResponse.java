package com.pacheco.hoursregistry.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
public class JwtResponse implements Serializable {

    @Getter
    private final String jwttoken;

}
