package com.raffasdev.registrapi.domain.model;

import com.raffasdev.registrapi.domain.exception.InvalidUsernameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {

    @Test
    @DisplayName("newUsername creates Username when valid value is provided")
    void newUsername_CreatesUsername_WhenValidValueIsProvided() {

        var result = assertDoesNotThrow(() -> Username.newUsername("test"));

        assertEquals("test", result.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "a", "usernameTooooLong"})
    @DisplayName("newUsername throws InvalidUsernameException when invalid values are provided")
    void newUsername_ThrowsInvalidUsernameException_WhenInvalidValuesAreProvided(String invalidUsername) {
        assertThrows(InvalidUsernameException.class, () -> Username.newUsername(invalidUsername));
    }

    @Test
    @DisplayName("equals returns true when usernames are equal")
    void equals_returnsTrue_WhenUsernamesAreEqual() {

        Username username1 = Username.newUsername("test1");
        Username username2 = Username.newUsername("test1");

        assertEquals(username1, username2);
        assertEquals(username1.hashCode(), username2.hashCode());
    }

    @Test
    @DisplayName("equals returns false when usernames are not equal")
    void equals_returnsFalse_WhenUsernamesAreNotEqual() {

        Username username1 = Username.newUsername("test1");
        Username username2 = Username.newUsername("test2");

        assertNotEquals(username1, username2);
        assertNotEquals(username1.hashCode(), username2.hashCode());
    }

    @Test
    @DisplayName("getValue returns the username value")
    void getValue_returnsTheUsernameValue() {
        String expectedUsername = "test";
        Username username = Username.newUsername(expectedUsername);

        String actualUsername = username.getValue();

        assertEquals(expectedUsername, actualUsername);
    }
}