package org.defix.services.calculator.functions;

import org.defix.services.calculator.abstractions.Function;

public class CosinusFunction implements Function {
    @Override
    public double calculate(double... args) {
        return Math.cos(args[0]);
    }
}
