package org.defix.services.calculator.functions.weird;

import org.defix.services.calculator.abstractions.Function;

public class IsPalindromeFunction implements Function {
    public double calculate(double... args) {
        int n = (int) args[0];
        String s = String.valueOf(n);
        return s.equals(new StringBuilder(s).reverse().toString()) ? 1 : 0;
    }
}
