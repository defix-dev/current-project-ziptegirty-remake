package org.defix.services.calculator.exceptions;

public class FunctionNotFoundException extends RuntimeException {
    public FunctionNotFoundException() {
        super("Function not found.");
    }
}
