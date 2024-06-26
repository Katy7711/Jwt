package com.example.jwt.service.impl;

import com.example.jwt.entity.User;
import com.example.jwt.repository.UserRepository;
import com.example.jwt.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  //Получение пользователя по имени пользователя
  @Override
  public UserDetailsService userDetailsService() {
    return this::getByUsername;
  }
  //Получение пользователя по имени пользователя
  @Override
  public User getByUsername(String username) {
    return userRepository.findByUserName(username)
        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
  }

  @Override
  public List<User> getUsers() {
    return userRepository.findAll();
  }
}
