package org.defix.calculator.function.programmer;

import org.defix.calculator.function.Function;

public class NotFunction implements Function {
    @Override
    public double calculate(double... args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("not requires exactly one argument");
        }
        return ~((long) args[0]);
    }
}
