package com.pacheco.hoursregistry.controller;

import com.pacheco.hoursregistry.model.jwt.JwtRequest;
import com.pacheco.hoursregistry.model.jwt.JwtResponse;
import com.pacheco.hoursregistry.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthorizationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/auth")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest jwtRequest) {
        return authenticationService.authenticate(jwtRequest);
    }
}
