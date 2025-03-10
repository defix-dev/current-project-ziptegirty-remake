package org.defix.services.calculator.exceptions;

public class EmptyExpressionException extends RuntimeException {
    public EmptyExpressionException() {
        super("Empty expression.");
    }
}
