package org.defix.services.calculator.exceptions;

public class TokenNotAllowedException extends Exception {
    public TokenNotAllowedException() {
        super("Token not allowed.");
    }
}
