package com.raffasdev.registrapi.infrastructure.persistence.jpa.repository;

import com.raffasdev.registrapi.infrastructure.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
