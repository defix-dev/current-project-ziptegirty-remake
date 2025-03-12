package org.defix.services.calculator.functions.simple;

import org.defix.services.calculator.abstractions.Function;

public class PowerFunction implements Function {
    @Override
    public double calculate(double... args) {
        return Math.pow(args[0], args[1]);
    }
}
