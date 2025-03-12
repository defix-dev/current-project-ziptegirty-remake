package org.defix.services.calculator.operators.simple;

import org.defix.services.calculator.abstractions.Operator;

public class MultiplyOperator implements Operator {
    @Override
    public double calculate(double a, double b) {
        return a*b;
    }
}
