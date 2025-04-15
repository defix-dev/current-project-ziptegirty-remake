package org.defix.calculator.operator.weird;

import org.defix.calculator.operator.Operator;

public class ConcatOperator implements Operator {
    public double calculate(double a, double b) {
        return Double.parseDouble("" + (int) a + (int) b);
    }
}
