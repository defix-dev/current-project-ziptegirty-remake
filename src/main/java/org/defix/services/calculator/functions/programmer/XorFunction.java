package org.defix.services.calculator.functions.programmer;

import org.defix.services.calculator.abstractions.Function;

public class XorFunction implements Function {
    @Override
    public double calculate(double... args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("xor requires exactly two arguments");
        }
        return (long) args[0] ^ (long) args[1];
    }
}
