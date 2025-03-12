package org.defix.services.calculator;

public class NumberParser {
    public static double parseNumber(String rawNumber) {
        rawNumber = rawNumber.replace(",", ".");
        return Double.parseDouble(rawNumber);
    }
}
