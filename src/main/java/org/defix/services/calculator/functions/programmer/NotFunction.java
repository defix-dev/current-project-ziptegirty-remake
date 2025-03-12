package org.defix.services.calculator.functions.programmer;

import org.defix.services.calculator.abstractions.Function;

public class NotFunction implements Function {
    @Override
    public double calculate(double... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("not requires exactly one argument");
        }
        return ~((long) args[0]);
    }
}
