package org.defix.services.calculator;

import org.defix.services.calculator.abstractions.CalculatorType;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.exceptions.CalculatorNotFoundException;

public class CalculatorUtils {
    public static TokensStore getTokensStore(String type) {
        try {
            return GlobalTokensStore.tokens.get(CalculatorType.valueOf(type.toUpperCase()));
        } catch (Exception e) {
            throw new CalculatorNotFoundException();
        }
    }
}
