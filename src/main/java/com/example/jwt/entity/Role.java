package com.example.jwt.entity;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  USER,
  ADMIN,
  MODERATOR,
  SUPER_ADMIN;

  @Override
  public String getAuthority() {
    return name();
  }
}
