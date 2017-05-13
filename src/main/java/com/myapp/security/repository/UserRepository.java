package com.myapp.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myapp.security.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
