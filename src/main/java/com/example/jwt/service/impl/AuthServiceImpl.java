package com.example.jwt.service.impl;


import com.example.jwt.dto.JwtAuthenticationResponse;
import com.example.jwt.dto.SignInRequest;
import com.example.jwt.dto.SignUpRequest;
import com.example.jwt.mapper.UserMapper;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.AuthService;
import com.example.jwt.service.JwtService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {


  private final UserRepository userRepository;
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final UserMapper userMapper;
  private final AuthenticationManager authenticationManager;



  //Аутентификация
  @Override
  public JwtAuthenticationResponse signIn(SignInRequest request) {
    UserDetails user = userDetailsService.loadUserByUsername(request.getUserName());

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new BadCredentialsException("Неверно указан пароль!");
    }
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUserName(),
        request.getPassword()
    ));
    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }

  //Регистрация
  @Override
  public JwtAuthenticationResponse signUp(SignUpRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new ValidationException(
          String.format("Пользователь \"%s\" уже зарегистрирован!", request.getEmail()));
    }
    UserDetails user = User.builder()
        .username(request.getUserName())
        .password(passwordEncoder.encode(request.getPassword()))
        .roles("USER")
        .build();

    request.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(userMapper.createSignUpRequestToEntity(request));
    var jwt = jwtService.generateToken(user);
    return new JwtAuthenticationResponse(jwt);
  }
}