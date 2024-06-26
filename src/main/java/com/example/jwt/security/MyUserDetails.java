package com.example.jwt.security;

import com.example.jwt.entity.User;
import java.util.List;
import lombok.Getter;

@Getter
public class MyUserDetails extends org.springframework.security.core.userdetails.User {

    private final Long id;

    public MyUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.id = user.getId();
    }

    @Override
    public void eraseCredentials() {
    }
}