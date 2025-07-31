package com.raffasdev.registrapi.infrastructure.persistence.jpa.mapper;

import com.raffasdev.registrapi.domain.model.Email;
import com.raffasdev.registrapi.domain.model.EntityId;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.model.Username;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class UserMapper {

    private UserMapper() {}

    public UserEntity toEntity(User user) {
        return UserEntity.create(
                user.getId().getValue(),
                user.getUsername(),
                user.getEmail(),
                user.getEncodedPassword()
        );
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return User.reconstitue(
                EntityId.of(entity.getUserId()),
                Username.of(entity.getUsername()),
                Email.of(entity.getEmail()),
                entity.getPassword()
        );
    }
}
