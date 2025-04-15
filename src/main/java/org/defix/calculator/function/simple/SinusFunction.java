package org.defix.calculator.function.simple;

import org.defix.calculator.function.Function;

public class SinusFunction implements Function {
    @Override
    public double calculate(double... args) {
        return Math.sin(args[0]);
    }
}
