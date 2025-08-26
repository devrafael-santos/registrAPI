package com.raffasdev.registrapi.application.service;

import com.raffasdev.registrapi.application.exception.UserNotFoundException;
import com.raffasdev.registrapi.application.exception.WrongCredentialsException;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.repository.UserRepository;
import com.raffasdev.registrapi.infrastructure.security.TokenService;
import com.raffasdev.registrapi.util.UserCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private TokenService tokenServiceMock;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("loginUser returns a token when credentials are valid")
    void loginUser_returnsToken_whenCredentialsAreValid() {

        String email = "test@email.com";
        String rawPassword = "password123";
        String expectedToken = "token";

        User foundUser = UserCreator.createUser();

        given(userRepositoryMock.findByEmail(email)).willReturn(Optional.of(foundUser));
        given(passwordEncoderMock.matches(rawPassword, foundUser.getEncodedPassword())).willReturn(true);
        given(tokenServiceMock.generateToken(foundUser.getEmail())).willReturn(expectedToken);

        String actualToken = authenticationService.loginUser(email, rawPassword);

        assertThat(actualToken).isEqualTo(expectedToken);

        verify(userRepositoryMock).findByEmail(email);
        verify(passwordEncoderMock).matches(rawPassword, foundUser.getEncodedPassword());
        verify(tokenServiceMock, times(1)).generateToken(foundUser.getEmail());
    }

    @Test
    @DisplayName("loginUser throws UserNotFoundException when user does not exist")
    void loginUser_throwsUserNotFoundException_whenUserDoesNotExist() {

        String email = "test@email.com";
        String rawPassword = "password123";
        given(userRepositoryMock.findByEmail(email)).willReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationService.loginUser(email, rawPassword))
                .isInstanceOf(UserNotFoundException.class);

        verify(passwordEncoderMock, never()).matches(anyString(), anyString());
        verify(tokenServiceMock, never()).generateToken(anyString());
    }

    @Test
    @DisplayName("loginUser throws BadCredentialsException when password is incorrect")
    void loginUser_throwsBadCredentialsException_whenPasswordIsIncorrect() {

        String email = "test@email.com";
        String rawPassword = "password123";

        User foundUser = UserCreator.createUser();

        given(userRepositoryMock.findByEmail(email)).willReturn(Optional.of(foundUser));
        given(passwordEncoderMock.matches(rawPassword, foundUser.getEncodedPassword())).willReturn(false);

        assertThatThrownBy(() -> authenticationService.loginUser(email, rawPassword))
                .isInstanceOf(WrongCredentialsException.class);

        verify(tokenServiceMock, never()).generateToken(anyString());
    }
}