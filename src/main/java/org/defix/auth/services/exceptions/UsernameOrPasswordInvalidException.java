package org.defix.auth.services.exceptions;

public class UsernameOrPasswordInvalidException extends RuntimeException {
    public UsernameOrPasswordInvalidException() {
        super("Username or password is invalid.");
    }
}
