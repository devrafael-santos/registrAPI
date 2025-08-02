package com.raffasdev.registrapi.infrastructure.persistence.jpa.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    @DisplayName("create returns a UserEntity with the provided parameters")
    void create_ReturnsUserEntityWithProvidedParameters() {
        var userEntity = UserEntity.create(
                UUID.randomUUID(),
                "test",
                "test@gmail.com",
                "password123"
        );

        assertNotNull(userEntity);
        assertNotNull(userEntity.getUserId());
        assertEquals("test", userEntity.getUsername());
        assertEquals("test@gmail.com" , userEntity.getEmail());
        assertEquals("password123", userEntity.getPassword());
    }

    @Test
    @DisplayName("default constructor creates an empty UserEntity")
    void defaultConstructor_CreatesEmptyUserEntity() {
        var userEntity = new UserEntity();

        assertNotNull(userEntity);
        assertNull(userEntity.getUserId());
        assertNull(userEntity.getUsername());
        assertNull(null, userEntity.getEmail());
        assertNull(null, userEntity.getPassword());
    }

}