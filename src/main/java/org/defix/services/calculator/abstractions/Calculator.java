package org.defix.services.calculator.abstractions;

import lombok.AllArgsConstructor;
import org.defix.services.calculator.ExpressionTokenizer;
import org.defix.services.calculator.TokensTreeBuilder;
import org.defix.services.calculator.TokensTreeCalculator;

@AllArgsConstructor
public class Calculator {

    private final TokensStore store;

    public double calculate(String expression) {
        return new TokensTreeCalculator(store).calculateExpression(
                new TokensTreeBuilder(store).build(
                        new ExpressionTokenizer(store).tokenizeExpression(expression)
                )
        );
    }
}
