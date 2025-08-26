package com.raffasdev.registrapi.application.service;

import com.raffasdev.registrapi.application.exception.UserNotFoundException;
import com.raffasdev.registrapi.application.exception.WrongCredentialsException;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.repository.UserRepository;
import com.raffasdev.registrapi.infrastructure.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Transactional
    public String loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        if (!passwordEncoder.matches(password, user.getEncodedPassword())) {
            throw new WrongCredentialsException();
        }

        return tokenService.generateToken(user.getEmail());
    }
}
