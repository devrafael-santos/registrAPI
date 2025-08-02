package com.raffasdev.registrapi.domain.repository;

import com.raffasdev.registrapi.domain.model.User;

public interface UserRepository {

    User save(User user);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
