package com.example.jwt.controller;

import com.example.jwt.entity.User;
import com.example.jwt.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/getUsers")
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

}
