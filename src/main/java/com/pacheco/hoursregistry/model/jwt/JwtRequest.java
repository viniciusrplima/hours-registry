package com.pacheco.hoursregistry.model.jwt;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {

    private String username;

    private String password;

}
