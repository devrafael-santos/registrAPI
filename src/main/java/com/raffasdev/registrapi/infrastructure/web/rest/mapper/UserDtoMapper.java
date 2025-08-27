package com.raffasdev.registrapi.infrastructure.web.rest.mapper;

import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.response.GetUserProfileResponse;
import com.raffasdev.registrapi.infrastructure.web.rest.dto.response.RegisterUserResponse;
import org.springframework.stereotype.Component;

@Component
public final class UserDtoMapper {

    public RegisterUserResponse toRegisterUserResponse(User user) {
        return new RegisterUserResponse(
                user.getUsername(),
                user.getEmail()
        );
    }

    public GetUserProfileResponse toGetUserProfileResponse(User user) {
        return new GetUserProfileResponse(
                user.getUsername(),
                user.getEmail()
        );
    }

}
