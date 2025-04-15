package org.defix.calculator.interpretator.tokenizer;

import org.defix.calculator.exception.BadExpressionFormatException;
import org.defix.calculator.exception.EmptyExpressionException;
import org.defix.calculator.interpretator.token.RawToken;

import java.util.List;

public class ExpressionValidator {
    public static void validateRawExpression(String expression) {
        if(expression == null || expression.isEmpty()) throw new EmptyExpressionException();
        if(charCount(expression, '(') != charCount(expression, ')')) throw new BadExpressionFormatException();
    }

    public static void validateTokenizedExpression(List<RawToken> tokens) {
        if(tokens.size() % 2 == 0) throw new BadExpressionFormatException();
    }

    private static int charCount(String expression, Character target) {
        return (int) expression.chars().filter(c -> c == target).count();
    }
}
