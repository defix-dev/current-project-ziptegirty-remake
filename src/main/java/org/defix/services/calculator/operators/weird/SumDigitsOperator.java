package org.defix.services.calculator.operators.weird;

import org.defix.services.calculator.abstractions.Operator;

public class SumDigitsOperator implements Operator {
    public double calculate(double a, double b) {
        return sumOfDigits((int) a) + sumOfDigits((int) b);
    }

    private int sumOfDigits(int n) {
        return String.valueOf(Math.abs(n)).chars().map(Character::getNumericValue).sum();
    }
}
