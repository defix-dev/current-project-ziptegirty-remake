package org.defix.calculator.function.simple;

import org.defix.calculator.function.Function;

public class PowerFunction implements Function {
    @Override
    public double calculate(double... args) {
        return Math.pow(args[0], args[1]);
    }
}
