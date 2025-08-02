package com.raffasdev.registrapi.infrastructure.web.rest.dto.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegisterUserResponseTest {

    @Test
    @DisplayName("constructor creates new RegisterUserResponse when successful")
    void constructor_CreatesNewRegisterUserResponse_WhenSuccessful() {

        var response = new RegisterUserResponse("test", "test@gmail.com");

        assertNotNull(response);
        assertEquals("test", response.username());
        assertEquals("test@gmail.com", response.email());
    }
}