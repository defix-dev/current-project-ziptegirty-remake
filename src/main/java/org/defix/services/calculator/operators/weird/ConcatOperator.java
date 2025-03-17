package org.defix.services.calculator.operators.weird;

import org.defix.services.calculator.abstractions.Operator;

public class ConcatOperator implements Operator {
    public double calculate(double a, double b) {
        return Double.parseDouble("" + (int) a + (int) b);
    }
}
