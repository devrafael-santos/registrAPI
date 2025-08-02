package com.raffasdev.registrapi.infrastructure.web.rest.mapper;

import com.raffasdev.registrapi.domain.model.Email;
import com.raffasdev.registrapi.domain.model.EntityId;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.model.Username;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.response.RegisterUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoMapperTest {

    private UserDtoMapper userDtoMapper;

    @BeforeEach
    void setUp() {
        userDtoMapper = new UserDtoMapper();
    }

    @Test
    @DisplayName("toRegisterUserResponse maps User to RegisterUserResponse when successful")
    void toRegisterUserResponse_MapsUserToRegisterUserResponse_WhenSuccessful() {
        User user = User.create(
                EntityId.newId(),
                Username.newUsername("test"),
                Email.newEmail("test@gmail.com"),
                "encodedPassword123"
        );

        RegisterUserResponse expectedResponse = new RegisterUserResponse(
                "test",
                "test@gmail.com"
        );

        RegisterUserResponse response = userDtoMapper.toRegisterUserResponse(user);

        assertEquals(expectedResponse, response);
    }
}