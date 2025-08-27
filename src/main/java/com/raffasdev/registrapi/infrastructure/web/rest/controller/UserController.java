package com.raffasdev.registrapi.infrastructure.web.rest.controller;

import com.raffasdev.registrapi.application.service.UserApplicationService;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.entity.UserEntity;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.response.GetUserProfileResponse;
import com.raffasdev.registrapi.infrastructure.web.rest.mapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserApplicationService userService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping("/profile")
    public ResponseEntity<GetUserProfileResponse> getUserProfile(Authentication authentication) {
        UserEntity userEntity = (UserEntity) authentication.getPrincipal();

        User user = userService.getUserProfile(userEntity.getEmail());

        GetUserProfileResponse profileResponse = userDtoMapper.toGetUserProfileResponse(user);

        return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }
}
