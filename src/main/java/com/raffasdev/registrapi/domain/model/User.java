package com.raffasdev.registrapi.domain.model;

public class User extends Entity<EntityId> {

    private final Username username;
    private final Email email;
    private final String encodedPassword;

    private User(EntityId id, Username username, Email email, String encodedPassword) {
        super(id);
        this.username = username;
        this.email = email;
        this.encodedPassword = encodedPassword;
    }

    public static User create(EntityId id, Username username, Email email, String encodedPassword) {
        return new User(id, username, email, encodedPassword);
    }

    public static User reconstitue(EntityId id, Username username, Email email, String encodedPassword) {
        return new User(id, username, email, encodedPassword);
    }

    public boolean hasUsername(Username username) {
        return this.username.equals(username);
    }

    public boolean hasEmail(Email email) {
        return this.email.equals(email);
    }

    public boolean hasEncodedPassword(String encodedPassword) {
        return this.encodedPassword.equals(encodedPassword);
    }

    public String getUsername() {
        return this.username.getValue();
    }

    public String getEmail() {
        return this.email.getValue();
    }

    public String getEncodedPassword() {
        return this.encodedPassword;
    }
}
