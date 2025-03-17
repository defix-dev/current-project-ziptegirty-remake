package org.defix.services.calculator.functions.weird;

import org.defix.services.calculator.abstractions.Function;

import java.util.ArrayList;
import java.util.List;

public class FactorizeFunction implements Function {
    public double calculate(double... args) {
        return factorize((int) args[0]).size();
    }

    private List<Integer> factorize(int n) {
        List<Integer> factors = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        return factors;
    }
}
