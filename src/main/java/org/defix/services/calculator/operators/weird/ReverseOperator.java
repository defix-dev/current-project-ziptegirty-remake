package org.defix.services.calculator.operators.weird;

import org.defix.services.calculator.abstractions.Operator;

public class ReverseOperator implements Operator {
    public double calculate(double a, double b) {
        return reverseNumber((int) a);
    }

    private int reverseNumber(int n) {
        return Integer.parseInt(new StringBuilder(String.valueOf(n)).reverse().toString());
    }
}
