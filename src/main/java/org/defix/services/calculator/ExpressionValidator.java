package org.defix.services.calculator;

import org.defix.services.calculator.exceptions.BadExpressionFormatException;
import org.defix.services.calculator.exceptions.EmptyExpressionException;
import org.defix.services.calculator.objects.RawToken;

import java.util.LinkedList;

public class ExpressionValidator {
    public static void validateRawExpression(String expression) {
        if(expression == null || expression.isEmpty()) throw new EmptyExpressionException();
    }

    public static void validateTokenizedExpression(LinkedList<RawToken> tokens) {
        if(tokens.size() % 2 == 0) throw new BadExpressionFormatException();
    }
}
