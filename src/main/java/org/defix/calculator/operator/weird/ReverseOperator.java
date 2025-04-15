package org.defix.calculator.operator.weird;

import org.defix.calculator.operator.Operator;

public class ReverseOperator implements Operator {
    public double calculate(double a, double b) {
        return reverseNumber((int) a);
    }

    private int reverseNumber(int n) {
        return Integer.parseInt(new StringBuilder(String.valueOf(n)).reverse().toString());
    }
}
