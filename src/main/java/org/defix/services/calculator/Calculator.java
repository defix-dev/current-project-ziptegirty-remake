package org.defix.services.calculator;

import org.defix.services.calculator.abstractions.TokensStore;

public class Calculator {
    public static double calculate(String expression, TokensStore store) {
        return new TokensTreeCalculator(store).calculateExpression(
                new TokensTreeBuilder(store).build(
                        new ExpressionTokenizer(store).tokenizeExpression(expression)
                )
        );
    }
}
