package com.raffasdev.registrapi.infrastructure.persistence.jpa.mapper;

import com.raffasdev.registrapi.domain.model.Email;
import com.raffasdev.registrapi.domain.model.EntityId;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.model.Username;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    @DisplayName("toEntity should map all fields from User to UserEntity correctly")
    void toEntity_shouldMapAllFields_fromDomainToEntity() {

        var id = EntityId.newId();
        User userDomain = User.create(
                id,
                Username.newUsername("test"),
                Email.newEmail("test@gmail.com"),
                "encodedPassword123"
        );

        UserEntity resultEntity = userMapper.toEntity(userDomain);

        assertNotNull(resultEntity);
        assertEquals(id.getValue(), resultEntity.getUserId());
        assertEquals("test", resultEntity.getUsername());
        assertEquals("test@gmail.com", resultEntity.getEmail());
        assertEquals("encodedPassword123", resultEntity.getPassword());
    }

    @Test
    @DisplayName("toDomain should map all fields from UserEntity to User correctly")
    void toDomain_shouldMapAllFields_fromEntityToDomain() {

        var id = EntityId.newId().getValue();
        UserEntity userEntity = UserEntity.create(
                id,
                "test",
                "test@gmail.com",
                "encodedPassword123"
        );

        User resultDomain = userMapper.toDomain(userEntity);

        assertNotNull(resultDomain);
        assertEquals(id, resultDomain.getId().getValue());
        assertEquals("test", resultDomain.getUsername());
        assertEquals("test@gmail.com", resultDomain.getEmail());
        assertEquals("encodedPassword123", resultDomain.getEncodedPassword());
    }
}