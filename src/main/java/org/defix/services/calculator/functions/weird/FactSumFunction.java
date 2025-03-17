package org.defix.services.calculator.functions.weird;

import org.defix.services.calculator.abstractions.Function;

public class FactSumFunction implements Function {
    public double calculate(double... args) {
        int n = (int) args[0];
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += factorial(i);
        }
        return sum;
    }

    private int factorial(int n) {
        return (n == 0) ? 1 : n * factorial(n - 1);
    }
}
