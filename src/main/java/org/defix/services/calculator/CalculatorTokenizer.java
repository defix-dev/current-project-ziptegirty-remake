package org.defix.services.calculator;

import org.defix.services.calculator.objects.ExpressionToken;
import org.defix.services.calculator.abstractions.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CalculatorTokenizer {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorTokenizer.class);

    public static LinkedList<ExpressionToken> tokenizeExpression(String expression) {
        LinkedList<ExpressionToken> tokens = new LinkedList<>();
        expression = expression.replace(" ", "");
        Queue<Character> expQueue = createQueueByExpression(expression);
        StringBuilder tokenBuilder = new StringBuilder();
        TokenType expectedTokenType = TokenType.NONE;
        while (true) {
            if(tokenBuilder.toString().equals("(") && expectedTokenType == TokenType.FUNCTION) expectedTokenType = TokenType.EXPRESSION;
            if (expectedTokenType == TokenType.EXPRESSION || expectedTokenType == TokenType.FUNCTION) {
                int depth = 1;
                while (depth != 0) {
                    String s = String.valueOf(expQueue.poll());
                    if (s.equals(")")) depth--;
                    if (s.equals("(")) depth++;
                    tokenBuilder.append(s);
                }
            }

            Character current = expQueue.poll();
            if (current == null) {
                tokens.add(new ExpressionToken(expectedTokenType, getTokenValue(expectedTokenType, tokenBuilder.toString())));
                break;
            }
            TokenType innerExpectedType = getExpectedTokenType(current, expectedTokenType);

            final boolean isException = !(expectedTokenType == TokenType.OPERATOR && innerExpectedType == TokenType.FUNCTION)
                    && expectedTokenType != TokenType.NONE;
            final boolean isOperatorExist = expectedTokenType == TokenType.OPERATOR && CalculatorTokensStore.operators.containsKey(tokenBuilder.toString());
            if ((expectedTokenType != innerExpectedType && isException) || isOperatorExist) {
                tokens.add(new ExpressionToken(expectedTokenType, getTokenValue(expectedTokenType, tokenBuilder.toString())));
                tokenBuilder = new StringBuilder();
            }

            expectedTokenType = innerExpectedType;
            tokenBuilder.append(current);
        }
        return tokens;
    }

    public static LinkedList<LinkedList<ExpressionToken>> tokenizeFunctionParams(String function) {
        int start = function.indexOf('(') + 1;
        int end = function.lastIndexOf(')');
        if (start <= 0 || end <= start) return new LinkedList<>();

        String content = function.substring(start, end);
        LinkedList<String> paramList = new LinkedList<>();
        StringBuilder currentParam = new StringBuilder();
        int depth = 0;

        for (char c : content.toCharArray()) {
            if (c == '(') depth++;
            if (c == ')') depth--;

            if (c == ',' && depth == 0) {
                paramList.add(currentParam.toString().trim());
                currentParam.setLength(0);
            } else {
                currentParam.append(c);
            }
        }
        if (!currentParam.isEmpty()) paramList.add(currentParam.toString().trim());

        return paramList.stream()
                .map(CalculatorTokenizer::tokenizeExpression)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    private static Queue<Character> createQueueByExpression(String expression) {
        return new ArrayDeque<>(expression.chars().mapToObj(c -> (char) c).toList());
    }

    private static String getTokenValue(TokenType expectedType, String interimTV) {
        return expectedType != TokenType.EXPRESSION ? interimTV : deleteBrackets(interimTV);
    }

    private static String deleteBrackets(String expression) {
        return expression.substring(1, expression.length()-1);
    }

    private static TokenType getExpectedTokenType(char current, TokenType defaultType) {
        if (isOperator(current)) return TokenType.OPERATOR;
        else if (isOperand(current)) return TokenType.OPERAND;
        else if (isFunction(current)) return TokenType.FUNCTION;
        return defaultType;
    }

    private static boolean isOperator(char current) {
        return !Character.isDigit(current) && ",.()".indexOf(current) == -1;
    }

    private static boolean isOperand(char current) {
        return Character.isDigit(current) || current == ',' || current == '.';
    }

    private static boolean isFunction(char current) {
        return !Character.isDigit(current) && ",.".indexOf(current) == -1;
    }
}

