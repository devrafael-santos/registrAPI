package com.raffasdev.registrapi.application.service;

import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.repository.UserRepository;
import com.raffasdev.registrapi.util.UserCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("registerUser registers new user when valid data provided")
    void registerUser_RegistersNewUser_WhenValidDataProvided() {

        var expectedUser = UserCreator.createUser();

        given(userRepositoryMock.existsByEmail(ArgumentMatchers.anyString())).willReturn(false);
        given(userRepositoryMock.existsByUsername(ArgumentMatchers.anyString())).willReturn(false);
        given(userRepositoryMock.save(ArgumentMatchers.any(User.class))).willReturn(UserCreator.createUser());

        given(passwordEncoderMock.encode(ArgumentMatchers.anyString())).willReturn("encodedPassword");

        User user = userService.registerUser("testuser", "teste@email.com", "encodedPassword");

        assertNotNull(user);
        assertEquals(expectedUser.getEmail(), user.getEmail());
        assertEquals(expectedUser.getUsername(), user.getUsername());
        assertEquals(expectedUser.getEncodedPassword(), user.getEncodedPassword());

    }


}