package org.defix.services.calculator.operators.programmer;

import org.defix.services.calculator.abstractions.Operator;

public class BitwiseAndOperator implements Operator {
    @Override
    public double calculate(double a, double b) {
        return (long) a & (long) b;
    }
}
