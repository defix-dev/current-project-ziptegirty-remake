
package org.defix.services.calculator.functions.weird;

import org.defix.services.calculator.abstractions.Function;

public class LogSumFunction implements Function {
    public double calculate(double... args) {
        return Math.log10(sumOfDigits((int) args[0]));
    }

    private int sumOfDigits(int n) {
        return String.valueOf(Math.abs(n)).chars().map(Character::getNumericValue).sum();
    }
}
