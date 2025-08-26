package com.raffasdev.registrapi.domain.repository;

import com.raffasdev.registrapi.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);
}
