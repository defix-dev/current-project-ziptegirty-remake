package org.defix.services.calculator.operators;

import org.defix.services.calculator.abstractions.Operator;

public class DivideOperator implements Operator {
    @Override
    public double calculate(double a, double b) {
        return a / b;
    }
}
