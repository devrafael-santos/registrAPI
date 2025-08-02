package com.raffasdev.registrapi.application.service;

import com.raffasdev.registrapi.application.exception.EmailAlreadyExistsException;
import com.raffasdev.registrapi.application.exception.UsernameAlreadyExistsException;
import com.raffasdev.registrapi.domain.model.Email;
import com.raffasdev.registrapi.domain.model.EntityId;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.model.Username;
import com.raffasdev.registrapi.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserApplicationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public User registerUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException(username);
        }

        String encodedPassword = passwordEncoder.encode(password);

        EntityId entityId = EntityId.newId();

        User user = User.create(
                entityId,
                Username.newUsername(username),
                Email.newEmail(email),
                encodedPassword
        );

        return userRepository.save(user);
    }

}
