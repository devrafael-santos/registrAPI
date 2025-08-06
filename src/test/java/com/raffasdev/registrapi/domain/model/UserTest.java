package com.raffasdev.registrapi.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private User reconstitutedUser;
    private EntityId id;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        id = EntityId.newId();
        Username username = Username.newUsername("test");
        Email email = Email.newEmail("test@test.com");
        encodedPassword = "encodedPassword123";
        user = User.create(id, username, email, encodedPassword);
        reconstitutedUser = User.reconstitute(id, username, email, encodedPassword);
    }

    @Test
    @DisplayName("create returns a valid User when correct state is provided")
    void create_returnsValidUser_WhenCorrectStateIsProvided() {
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(encodedPassword, user.getEncodedPassword());
    }

    @Test
    @DisplayName("reconstitue returns a valid User when correct state is provided")
    void reconstitue_returnsValidUser_WhenCorrectStateIsProvided() {
        assertNotNull(reconstitutedUser);
        assertEquals(id, reconstitutedUser.getId());
        assertEquals(encodedPassword, reconstitutedUser.getEncodedPassword());
        assertEquals(user.getUsername(), reconstitutedUser.getUsername());
        assertEquals(user.getEmail(), reconstitutedUser.getEmail());
        assertEquals(user.getEncodedPassword(), reconstitutedUser.getEncodedPassword());
    }

    @Test
    @DisplayName("hasUsername returns true when username matches")
    void hasUsername_returnsTrue_WhenUsernameMatches() {
        assertTrue(user.hasUsername(Username.newUsername("test")));
    }

    @Test
    @DisplayName("hasUsername returns false when username does not match")
    void hasUsername_returnsFalse_WhenUsernameDoesNotMatch() {
        assertFalse(user.hasUsername(Username.newUsername("anotherUsername")));
    }

    @Test
    @DisplayName("hasEmail returns true when email matches")
    void hasEmail_returnsTrue_WhenEmailMatches() {
        assertTrue(user.hasEmail(Email.newEmail("test@test.com")));
    }

    @Test
    @DisplayName("hasEmail returns false when email does not match")
    void hasEmail_returnsFalse_WhenEmailDoesNotMatch() {
        assertFalse(user.hasEmail(Email.newEmail("another@email.com")));
    }

    @Test
    @DisplayName("hasEncodedPassword returns true when password matches")
    void hasEncodedPassword_returnsTrue_WhenPasswordMatches() {
        assertTrue(user.hasEncodedPassword("encodedPassword123"));
    }

    @Test
    @DisplayName("hasEncodedPassword returns false when password does not match")
    void hasEncodedPassword_returnsFalse_WhenPasswordDoesNotMatch() {
        assertFalse(user.hasEncodedPassword("wrongPassword"));
    }

    @Test
    @DisplayName("getters return the correct primitive values always")
    void getters_returnCorrectPrimitiveValues_Always() {

        assertEquals("test", user.getUsername());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("encodedPassword123", user.getEncodedPassword());
    }

    @Test
    @DisplayName("equals returns true when User objects are equal")
    void equals_ReturnsTrue_WhenUserObjectsAreEqual() {
        User anotherUser = User.create(
                id,
                Username.newUsername("test"),
                Email.newEmail("test@test.com"),
                "encodedPassword123"
        );

        assertEquals(user, anotherUser);
        assertEquals(user.hashCode(), anotherUser.hashCode());
    }

    @Test
    @DisplayName("equals returns false when User objects are not equal")
    void equals_ReturnsFalse_WhenUserObjectsAreNotEqual() {
        User anotherUser = User.create(
                EntityId.newId(),
                Username.newUsername("anotherUsername"),
                Email.newEmail("another@email.com"),
                "encodedPassword123"
        );

        assertNotEquals(user, anotherUser);
        assertNotEquals(user.hashCode(), anotherUser.hashCode());
    }
}