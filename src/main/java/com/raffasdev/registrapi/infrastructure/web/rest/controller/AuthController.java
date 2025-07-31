package com.raffasdev.registrapi.infrastructure.web.rest.controller;

import com.raffasdev.registrapi.application.service.UserApplicationService;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.request.RegisterUserRequest;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.response.RegisterUserResponse;
import com.raffasdev.registrapi.infrastructure.web.rest.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserApplicationService userService;

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

}
