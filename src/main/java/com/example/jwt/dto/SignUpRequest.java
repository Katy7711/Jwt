package com.example.jwt.dto;


import static com.example.jwt.constant.Regexp.EMAIL_REGEXP;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Data;

//Регистрация пользователя
@Data
public class SignUpRequest {

  @Size(min = 2, max = 50)
  @NotBlank()
  private String userName;

  @Email(regexp = EMAIL_REGEXP)
  @Size(min = 5, max = 255)
  @NotBlank()
  private String email;

  @Size(max = 255)
  private String password;

}
