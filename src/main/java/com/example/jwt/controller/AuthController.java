package com.example.jwt.controller;

import com.example.jwt.dto.JwtAuthenticationResponse;
import com.example.jwt.dto.SignInRequest;
import com.example.jwt.dto.SignUpRequest;
import com.example.jwt.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authenticationService;

  @PostMapping("/auth/sign-in")
  public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
    return authenticationService.signIn(request);
  }

  @PostMapping("/auth/sign-up")
  public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
    return authenticationService.signUp(request);
  }
}
