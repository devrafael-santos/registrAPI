package com.raffasdev.registrapi.infrastructure.web.rest.controller;

import com.raffasdev.registrapi.application.service.UserApplicationService;
import com.raffasdev.registrapi.domain.model.Email;
import com.raffasdev.registrapi.domain.model.EntityId;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.model.Username;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.request.RegisterUserRequest;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.response.RegisterUserResponse;
import com.raffasdev.registrapi.infrastructure.web.rest.mapper.UserDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserApplicationService userServiceMock;

    @Mock
    private UserDtoMapper userDtoMapperMock;

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("registerUser returnsResponseEntity with RegisterUserResponse and 201 WhenSuccessful")
    void registerUser_ReturnsResponseEntityWithRegisterUserResponseAnd201_WhenSuccessful() {
        RegisterUserRequest request = new RegisterUserRequest(
                "test",
                "test@gmail.com",
                "password",
                "password"
        );

        var id = EntityId.newId();

        User user = User.create(
                id,
                Username.newUsername("test"),
                Email.newEmail("test@gmail.com"),
                "encodedPassword123"
        );

        var expectedResponse = new RegisterUserResponse(
                "test",
                "test@gmail.com"
        );

        when(userServiceMock.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        )).thenReturn(user);

        when(userDtoMapperMock.toRegisterUserResponse(user)).thenReturn(expectedResponse);

        ResponseEntity<RegisterUserResponse> response = authController.registerUser(request);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}