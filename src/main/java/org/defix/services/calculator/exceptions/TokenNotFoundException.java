package org.defix.services.calculator.exceptions;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Token not found.");
    }
}
