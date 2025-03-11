package org.defix.services.calculator.exceptions;

public class BadExpressionFormatException extends RuntimeException {
    public BadExpressionFormatException() {
        super("Bad expression format.");
    }
}
