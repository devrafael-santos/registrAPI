package com.raffasdev.registrapi.infrastructure.web.rest.controller;

import com.raffasdev.registrapi.application.service.AuthenticationService;
import com.raffasdev.registrapi.application.service.UserApplicationService;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.request.LoginUserRequest;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.request.RegisterUserRequest;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.response.LoginUserResponse;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.response.RegisterUserResponse;
import com.raffasdev.registrapi.infrastructure.web.rest.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserApplicationService userService;

    private final AuthenticationService authenticationService;

    private final UserDtoMapper userDtoMapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(
            @RequestBody @Valid RegisterUserRequest registerUserRequest) {

        User userRegistered = userService.registerUser(
                registerUserRequest.getUsername(),
                registerUserRequest.getEmail(),
                registerUserRequest.getPassword()
        );

        return new ResponseEntity<>(
                userDtoMapper.toRegisterUserResponse(userRegistered),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(
            @RequestBody @Valid LoginUserRequest loginUserRequest) {

        String token = authenticationService.loginUser(
                loginUserRequest.getEmail(),
                loginUserRequest.getPassword()
        );

        return new ResponseEntity<>(
                new LoginUserResponse(loginUserRequest.getEmail(), token),
                HttpStatus.OK
        );
    }

}
