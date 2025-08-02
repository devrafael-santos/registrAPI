package com.raffasdev.registrapi.infrastructure.web.rest.dto.request;

import com.raffasdev.registrapi.infrastructure.web.rest.validator.PasswordsMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@PasswordsMatch
public class RegisterUserRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 2, max = 15, message = "Username must be between 2 and 15 characters")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotBlank(message = "Confirm Password cannot be blank")
    private String confirmPassword;
}
