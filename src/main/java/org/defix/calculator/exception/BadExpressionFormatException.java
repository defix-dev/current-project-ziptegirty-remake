package org.defix.calculator.exception;

public class BadExpressionFormatException extends RuntimeException {
    public BadExpressionFormatException() {
        super("Bad expression format.");
    }
}
