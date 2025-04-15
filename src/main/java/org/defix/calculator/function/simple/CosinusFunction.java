package org.defix.calculator.function.simple;

import org.defix.calculator.function.Function;

public class CosinusFunction implements Function {
    @Override
    public double calculate(double... args) {
        return Math.cos(args[0]);
    }
}
