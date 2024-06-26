package com.example.jwt.repository;

import com.example.jwt.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

  boolean existsByEmail(String email);

  Optional<User> findByUserName(String username);

  Optional<User> findByEmail(String username);
}
