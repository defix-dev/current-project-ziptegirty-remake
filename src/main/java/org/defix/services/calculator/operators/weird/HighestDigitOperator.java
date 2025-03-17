package org.defix.services.calculator.operators.weird;

import org.defix.services.calculator.abstractions.Operator;

public class HighestDigitOperator implements Operator {
    public double calculate(double a, double b) {
        return highestDigit((int) a);
    }

    private int highestDigit(int n) {
        return (int) Math.pow(10, (int) Math.log10(n));
    }
}
