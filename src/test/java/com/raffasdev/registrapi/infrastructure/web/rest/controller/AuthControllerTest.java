package com.raffasdev.registrapi.infrastructure.web.rest.controller;

import com.raffasdev.registrapi.application.service.AuthenticationService;
import com.raffasdev.registrapi.application.service.UserApplicationService;
import com.raffasdev.registrapi.domain.model.Email;
import com.raffasdev.registrapi.domain.model.EntityId;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.model.Username;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.request.LoginUserRequest;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.request.RegisterUserRequest;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.response.LoginUserResponse;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserApplicationService userServiceMock;

    @Mock
    private UserDtoMapper userDtoMapperMock;

    @Mock
    private AuthenticationService authenticationServiceMock;

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("registerUser returnsResponseEntity with RegisterUserResponse and 201 WhenSuccessful")
    void registerUser_ReturnsResponseEntityWithRegisterUserResponseAnd201_WhenSuccessful() {
        RegisterUserRequest request = new RegisterUserRequest(
                "test",
                "test@email.com",
                "password",
                "password"
        );

        var id = EntityId.newId();

        User user = User.create(
                id,
                Username.newUsername("test"),
                Email.newEmail("test@email.com"),
                "encodedPassword123"
        );

        var expectedResponse = new RegisterUserResponse(
                "test",
                "test@email.com"
        );

        given(userServiceMock.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        )).willReturn(user);

        when(userDtoMapperMock.toRegisterUserResponse(user)).thenReturn(expectedResponse);

        ResponseEntity<RegisterUserResponse> response = authController.registerUser(request);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @DisplayName("loginUser returns LoginUserResponse when credentials are valid")
    void loginUser_returnsOkAndToken_whenCredentialsAreValid() {
        var request = new LoginUserRequest("test@email.com", "password123");

        var expectedResponse = new LoginUserResponse("test@email.com", "token");

        given(authenticationServiceMock.loginUser(
                request.getEmail(),
                request.getPassword()
        )).willReturn(expectedResponse.token());

        var response = authController.loginUser(request);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

}