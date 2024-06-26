package com.example.jwt.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.example.jwt.service.impl.UserServiceImpl;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class Config {
  private final UserServiceImpl userService;

  public Config(UserServiceImpl userService) {
    this.userService = userService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private static final String[] AUTH_WHITELIST = {
      "/swagger-resources/**",
      "/swagger-ui.html",
      "/v3/api-docs",
      "/auth/sign-in/",
      "/auth/sign-up/"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        // Своего рода отключение CORS (разрешение запросов со всех доменов)
        .cors(cors -> cors.configurationSource(request -> {
          var corsConfiguration = new CorsConfiguration();
          corsConfiguration.setAllowedOriginPatterns(List.of("*"));
          corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
          corsConfiguration.setAllowedHeaders(List.of("*"));
          corsConfiguration.setAllowCredentials(true);
          return corsConfiguration;
        }));
    return http.authorizeHttpRequests((auth) ->
            auth
                .requestMatchers(HttpMethod.GET, "/getUsers").hasRole("ADMIN")
                .requestMatchers(AUTH_WHITELIST).permitAll()
        )
        .httpBasic(withDefaults())
        .build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService.userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}

