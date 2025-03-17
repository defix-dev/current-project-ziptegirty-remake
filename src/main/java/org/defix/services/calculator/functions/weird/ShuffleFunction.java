package org.defix.services.calculator.functions.weird;

import org.defix.services.calculator.abstractions.Function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShuffleFunction implements Function {
    public double calculate(double... args) {
        List<Character> digits = new ArrayList<>();
        for (char c : String.valueOf((int) args[0]).toCharArray()) {
            digits.add(c);
        }
        Collections.shuffle(digits);
        return Double.parseDouble(digits.stream().map(String::valueOf).collect(Collectors.joining()));
    }
}
