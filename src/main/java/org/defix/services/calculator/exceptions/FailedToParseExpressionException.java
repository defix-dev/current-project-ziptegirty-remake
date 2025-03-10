package org.defix.services.calculator.exceptions;

public class FailedToParseExpressionException extends RuntimeException {
    public FailedToParseExpressionException() {
        super("Failed to parse expression.");
    }
}
