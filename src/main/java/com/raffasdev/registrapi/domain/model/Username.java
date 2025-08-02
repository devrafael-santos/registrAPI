package com.raffasdev.registrapi.domain.model;

import com.raffasdev.registrapi.domain.exception.InvalidUsernameException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Username extends ValueObject {

    private final String username;

    private Username(String username) {
        this.username = username;
        validate();
    }

    public static Username newUsername(String username) {
        return new Username(username);
    }

    public static Username of(String username) {
        return new Username(username);
    }

    public String getValue() {
        return this.username;
    }

    private void validate() {
        if (username == null || username.isEmpty()) {
            throw new InvalidUsernameException(username);
        }
        if (username.length() < 2 || username.length() > 15) {
            throw new InvalidUsernameException(username);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Username username1 = (Username) o;
        return Objects.equals(username, username1.username);
    }

    @Override
    public int hashCode() {
        return 31 * (username != null ? username.hashCode() : 0);
    }
}
