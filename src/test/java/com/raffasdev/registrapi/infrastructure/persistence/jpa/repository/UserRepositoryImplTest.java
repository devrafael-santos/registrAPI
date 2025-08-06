package com.raffasdev.registrapi.infrastructure.persistence.jpa.repository;

import com.raffasdev.registrapi.domain.model.Email;
import com.raffasdev.registrapi.domain.model.EntityId;
import com.raffasdev.registrapi.domain.model.User;
import com.raffasdev.registrapi.domain.model.Username;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.entity.UserEntity;
import com.raffasdev.registrapi.infrastructure.persistence.jpa.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository jpaRepositoryMock;

    @Mock
    private UserMapper userMapperMock;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    @DisplayName("save returns a mapped domain User when save is successful")
    void save_returnsMappedUser_whenSaveIsSuccessful() {

        var id = EntityId.newId();

        User userToSave = User.create(
                id,
                Username.newUsername("test"),
                Email.newEmail("test@email.com"),
                "encodedPassword"
        );

        UserEntity userEntityToSave = new UserEntity();
        UserEntity savedUserEntity = new UserEntity();

        User expectedSavedUser = User.reconstitute(
                id,
                Username.newUsername("test"),
                Email.newEmail("test@email.com"),
                "encodedPassword"
        );

        when(userMapperMock.toEntity(userToSave)).thenReturn(userEntityToSave);
        when(jpaRepositoryMock.save(userEntityToSave)).thenReturn(savedUserEntity);
        when(userMapperMock.toDomain(savedUserEntity)).thenReturn(expectedSavedUser);

        User actualSavedUser = userRepositoryImpl.save(userToSave);

        assertNotNull(actualSavedUser);
        assertEquals(expectedSavedUser, actualSavedUser);

        verify(userMapperMock, times(1)).toEntity(userToSave);
        verify(jpaRepositoryMock, times(1)).save(userEntityToSave);
        verify(userMapperMock, times(1)).toDomain(savedUserEntity);
    }

    @Test
    @DisplayName("existsByEmail returns true when email exists")
    void existsByEmail_returnsTrue_whenEmailExists() {
        String email = "test@email.com";
        when(jpaRepositoryMock.existsByEmail(email)).thenReturn(true);

        boolean result = userRepositoryImpl.existsByEmail(email);

        assertTrue(result);
        verify(jpaRepositoryMock, times(1)).existsByEmail(email);
    }

    @Test
    @DisplayName("existsByEmail returns false when email does not exists")
    void existsByEmail_returnsFalse_whenEmailDoesNotExist() {
        String email = "test@email.com";
        when(jpaRepositoryMock.existsByEmail(email)).thenReturn(false);

        boolean result = userRepositoryImpl.existsByEmail(email);

        assertFalse(result);
        verify(jpaRepositoryMock, times(1)).existsByEmail(email);
    }

    @Test
    @DisplayName("existsByUsername returns true when username exists")
    void existsByUsername_returnsTrue_whenUsernameExists() {
        String username = "test";
        when(jpaRepositoryMock.existsByUsername(username)).thenReturn(true);

        boolean result = userRepositoryImpl.existsByUsername(username);

        assertTrue(result);
        verify(jpaRepositoryMock, times(1)).existsByUsername(username);
    }

    @Test
    @DisplayName("existsByUsername returns false when username does not exists")
    void existsByUsername_returnsFalse_whenUsernameDoesNotExist() {
        String username = "test";
        when(jpaRepositoryMock.existsByUsername(username)).thenReturn(false);

        boolean result = userRepositoryImpl.existsByUsername(username);

        assertFalse(result);
        verify(jpaRepositoryMock, times(1)).existsByUsername(username);
    }


}