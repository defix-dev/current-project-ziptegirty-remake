package org.defix.calculator.operator.programmer;

import org.defix.calculator.operator.Operator;

public class BitwiseOrOperator implements Operator {
    @Override
    public double calculate(double a, double b) {
        return (long) a | (long) b;
    }
}
