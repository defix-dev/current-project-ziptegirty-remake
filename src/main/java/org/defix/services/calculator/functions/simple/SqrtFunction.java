package org.defix.services.calculator.functions.simple;

import org.defix.services.calculator.abstractions.Function;

public class SqrtFunction implements Function {

    @Override
    public double calculate(double... args) {
        return Math.sqrt(args[0]);
    }
}
