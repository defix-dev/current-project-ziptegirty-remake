package org.defix.calculator.operator.simple;

import org.defix.calculator.operator.Operator;

public class DivideOperator implements Operator {
    @Override
    public double calculate(double a, double b) {
        return a / b;
    }
}
