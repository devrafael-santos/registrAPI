package com.raffasdev.registrapi.infrastructure.persistence.jpa.repository;

import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.repository.UserRepository;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.entity.UserEntity;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public User save(User user) {

        UserEntity userEntity = UserMapper.toEntity(user);

        UserEntity savedUserEntity = jpaRepository.save(userEntity);

        return UserMapper.toDomain(savedUserEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }
}
