package org.defix.calculator.function.simple;

import org.defix.calculator.function.Function;

public class SqrtFunction implements Function {

    @Override
    public double calculate(double... args) {
        return Math.sqrt(args[0]);
    }
}
