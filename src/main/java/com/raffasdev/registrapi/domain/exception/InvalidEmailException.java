package com.raffasdev.registrapi.domain.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String email) {
        super("Invalid email address: " + email);
    }
}
