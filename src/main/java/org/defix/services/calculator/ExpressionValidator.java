package org.defix.services.calculator;

import org.defix.services.calculator.exceptions.BadExpressionFormatException;
import org.defix.services.calculator.exceptions.EmptyExpressionException;
import org.defix.services.calculator.objects.RawToken;

import java.util.LinkedList;
import java.util.List;

public class ExpressionValidator {
    public static void validateRawExpression(String expression) {
        if(expression == null || expression.isEmpty()) throw new EmptyExpressionException();
        if(charCount(expression, '(') != charCount(expression, ')')) throw new BadExpressionFormatException();
    }

    private static int charCount(String expression, Character target) {
        return (int) expression.chars().filter(c -> c == target).count();
    }

    public static void validateTokenizedExpression(List<RawToken> tokens) {
        if(tokens.size() % 2 == 0) throw new BadExpressionFormatException();
    }
}
