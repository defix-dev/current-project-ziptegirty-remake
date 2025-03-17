package org.defix.services.calculator.exceptions;

public class CalculatorNotFoundException extends RuntimeException {
    public CalculatorNotFoundException() {
        super("Calculator not found.");
    }
}
