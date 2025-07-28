package com.raffasdev.registrapi.domain.model;

import com.raffasdev.registrapi.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    private final String validEmail = "teste@email.com";

    private final String invalidEmail = "testeemail.com";

    private final String blankEmail = "";

    @Test
    @DisplayName("of returns valid Email when valid email is Provided")
    void emailOf_ReturnsValidEmail_WhenValidEmailIsProvided() {

        Email email = assertDoesNotThrow(() -> Email.of(validEmail));

        assertNotNull(email);
        assertEquals(validEmail, email.getValue());
    }

    @Test
    @DisplayName("of throws InvalidEmailException when invalid email is Provided")
    void emailOf_ThrowsInvalidEmailException_WhenInvalidEmailIsProvided() {

        assertThrows(InvalidEmailException.class, () -> Email.of(invalidEmail));
    }

    @Test
    @DisplayName("of throws InvalidEmailException when empty email is Provided")
    void emailOf_ThrowsInvalidEmailException_WhenEmptyEmailIsProvided() {

        assertThrows(InvalidEmailException.class, () -> Email.of(blankEmail));
    }

    @Test
    @DisplayName("newEmail returns valid Email when valid email is Provided")
    void newEmail_ReturnsValidEmail_WhenValidEmailIsProvided() {

        Email email = assertDoesNotThrow(() -> Email.newEmail(validEmail));

        assertNotNull(email);
        assertEquals(validEmail, email.getValue());
    }

    @Test
    @DisplayName("newEmail throws InvalidEmailException when invalid email is Provided")
    void newEmail_ThrowsInvalidEmailException_WhenInvalidEmailIsProvided() {

        assertThrows(InvalidEmailException.class, () -> Email.newEmail(invalidEmail));
    }

    @Test
    @DisplayName("newEmail throws InvalidEmailException when empty email is Provided")
    void newEmail_ThrowsInvalidEmailException_WhenEmptyEmailIsProvided() {

        assertThrows(InvalidEmailException.class, () -> Email.newEmail(blankEmail));
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