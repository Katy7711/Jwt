package com.example.jwt.service;

import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String generateToken(UserDetails userDetails);

  String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

  //Проверка токена на просроченность
  boolean isTokenExpired(String token);

  //Извлечение даты истечения токена
  Date extractExpiration(String token);

  //Извлечение данных из токена
  <T> T extractClaim(String token, Function<Claims, T> claimsResolvers);

  //Извлечение всех данных из токена
  Claims extractAllClaims(String token);

  //Извлечение имени пользователя из токена
  String extractUserName(String token);

  //Проверка токена на валидность
  boolean isTokenValid(String token, UserDetails userDetails);

}
