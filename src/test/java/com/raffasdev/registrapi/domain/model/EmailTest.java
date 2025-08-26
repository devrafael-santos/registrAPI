package com.raffasdev.registrapi.domain.model;

import com.raffasdev.registrapi.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    private final String validEmail = "teste@email.com";

    @Test
    @DisplayName("of returns valid Email when valid email is Provided")
    void emailOf_ReturnsValidEmail_WhenValidEmailIsProvided() {

        Email email = assertDoesNotThrow(() -> Email.of(validEmail));

        assertNotNull(email);
        assertEquals(validEmail, email.getValue());
    }

    @Test
    @DisplayName("newEmail returns valid Email when valid email is Provided")
    void newEmail_ReturnsValidEmail_WhenValidEmailIsProvided() {

        Email email = assertDoesNotThrow(() -> Email.newEmail(validEmail));

        assertNotNull(email);
        assertEquals(validEmail, email.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"invalidemail", "email@.com", "@teste.com"})
    @DisplayName("newEmail throws InvalidEmailException when invalid values are provided")
    void newEmail_ThrowsInvalidEmailException_WhenInvalidValuesEmailAreProvided(String invalidEmail) {

        assertThrows(InvalidEmailException.class, () -> Email.newEmail(invalidEmail));
    }

    @Test
    @DisplayName("equals returns true when emails are equal")
    void equals_ReturnsTrue_WhenEmailsAreEqual() {
        Email email1 = Email.of(validEmail);

        Email email2 = Email.of(validEmail);

        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }

    @Test
    @DisplayName("getValue returns the email value")
    void getValue_ReturnsTheEmailValue_WhenSuccessful() {
        Email email1 = Email.of(validEmail);

        assertEquals(email1.getValue(), validEmail);
    }


}