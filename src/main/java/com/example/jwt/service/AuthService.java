package com.example.jwt.service;

import com.example.jwt.dto.JwtAuthenticationResponse;
import com.example.jwt.dto.SignInRequest;
import com.example.jwt.dto.SignUpRequest;

public interface AuthService {


    JwtAuthenticationResponse signIn(SignInRequest request);

    JwtAuthenticationResponse signUp(SignUpRequest user);
}