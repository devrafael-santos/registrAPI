package com.raffasdev.registrapi.infrastructure.persistence.jpa.repository;

import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.repository.UserRepository;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        return userMapper.toDomain(jpaRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userMapper.toOptionalDomain(jpaRepository.findByEmail(email));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }
}
