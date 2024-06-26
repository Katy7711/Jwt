package com.example.jwt.service.impl;


import com.example.jwt.entity.User;
import com.example.jwt.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

  @Value("${token.signing.key}")
  private String jwtSigningKey;

  @Override
  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    if (userDetails instanceof User customUserDetails) {
      claims.put("id", customUserDetails.getId());
      claims.put("email", customUserDetails.getEmail());
      claims.put("role", customUserDetails.getRole());
    }
    return generateToken(claims, userDetails);
  }

  @Override
  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
        .signWith(getSigningKey()).compact();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Base64.getDecoder().decode(jwtSigningKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  //Проверка токена на просроченность
  @Override
  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  //Извлечение даты истечения токена
  @Override
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  //Извлечение данных из токена
  @Override
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  //Извлечение всех данных из токена
  @Override
  public Claims extractAllClaims(String token) {
    return Jwts.parser().build().parseSignedClaims(token)
        .getPayload();
  }

  //Извлечение имени пользователя из токена
  @Override
  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  //Проверка токена на валидность
  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }
}
