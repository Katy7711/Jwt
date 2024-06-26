package com.example.jwt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

//Авторизация пользователя
@Data
public class SignInRequest {
  @Size(min = 2, max = 50)
  @NotBlank()
  private String userName;

  @Size(min = 8, max = 255)
  @NotBlank()
  private String password;

}
