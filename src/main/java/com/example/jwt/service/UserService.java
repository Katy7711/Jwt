package com.example.jwt.service;

import com.example.jwt.entity.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  //Получение пользователя по имени пользователя
  UserDetailsService userDetailsService();

  //Получение пользователя по имени пользователя
  User getByUsername(String username);

  List<User> getUsers();
}
