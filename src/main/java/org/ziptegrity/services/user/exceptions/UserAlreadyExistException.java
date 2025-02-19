package org.ziptegrity.services.user.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("User already exist.");
    }
}
