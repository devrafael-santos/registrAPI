package com.raffasdev.registrapi.util;

import com.raffasdev.registrapi.domain.model.Email;
import com.raffasdev.registrapi.domain.model.Username;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.entity.UserEntity;

import java.util.UUID;

public class UserEntityCreator {

    private static final UUID ENTITY_ID = UUID.randomUUID();

    private UserEntityCreator() {
    }

    public static UserEntity createEntity() {
        return UserEntity.create(
                ENTITY_ID,
                "testuser",
                "teste@email.com",
                "encodedPassword"
        );
    }

}
