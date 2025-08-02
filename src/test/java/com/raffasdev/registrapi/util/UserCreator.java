package com.raffasdev.registrapi.util;

import com.raffasdev.registrapi.domain.model.Email;
import com.raffasdev.registrapi.domain.model.EntityId;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.model.Username;

public final class UserCreator {

    private static final EntityId ENTITY_ID = EntityId.newId();

    private UserCreator() {
    }

    public static User createUser() {
        return User.create(
                ENTITY_ID,
                Username.of("testuser"),
                Email.of("teste@email.com"),
                "encodedPassword"
        );
    }
}
