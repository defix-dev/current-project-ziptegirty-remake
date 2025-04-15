package org.defix.calculator.operator.programmer;

import org.defix.calculator.operator.Operator;

public class LeftShiftOperator implements Operator {
    @Override
    public double calculate(double a, double b) {
        return (long) a << (long) b;
    }
}
