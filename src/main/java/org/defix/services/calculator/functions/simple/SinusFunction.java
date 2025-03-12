package org.defix.services.calculator.functions.simple;

import org.defix.services.calculator.abstractions.Function;

public class SinusFunction implements Function {
    @Override
    public double calculate(double... args) {
        return Math.sin(args[0]);
    }
}
