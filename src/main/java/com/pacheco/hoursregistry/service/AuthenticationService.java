package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.exception.BusinessException;
import com.pacheco.hoursregistry.model.jwt.JwtRequest;
import com.pacheco.hoursregistry.model.jwt.JwtResponse;

public interface AuthenticationService {

    public JwtResponse authenticate(JwtRequest jwtRequest) throws BusinessException;

}
