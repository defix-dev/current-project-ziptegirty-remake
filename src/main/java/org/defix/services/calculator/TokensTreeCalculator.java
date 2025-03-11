package org.defix.services.calculator;

import org.defix.services.calculator.abstractions.Function;
import org.defix.services.calculator.abstractions.MappedToken;
import org.defix.services.calculator.abstractions.Operator;
import org.defix.services.calculator.abstractions.TokenType;
import org.defix.services.calculator.objects.*;

import java.util.*;

public class TokensTreeCalculator {
    public static double calculateExpression(LinkedList<MappedToken> tokens) {
        Stack<Double> values = new Stack<>();
        Stack<String> operators = new Stack<>();

        while (!tokens.isEmpty()) {
            MappedToken token = tokens.poll();

            if (token instanceof DefaultMappedToken operandToken) {
                if (operandToken.identify() == TokenType.OPERAND) {
                    values.push(Double.parseDouble(operandToken.getValue()));
                } else if (operandToken.identify() == TokenType.OPERATOR) {
                    while (!operators.isEmpty() && CalculatorTokensStore.operators.get(operators.peek()).getOrder()
                            >= CalculatorTokensStore.operators.get(operandToken.getValue()).getOrder()) {
                        double b = values.pop();
                        double a = values.pop();
                        values.push(CalculatorTokensStore.operators.get(operators.pop()).getTokenAction().calculate(a, b));
                    }
                    operators.push(operandToken.getValue());
                }
            } else if (token instanceof MappedFunctionToken functionToken) {
                values.push(calculateFunction(functionToken));
            } else if (token instanceof MappedExpressionToken expressionToken) {
                values.push(calculateExpression(expressionToken.getTokens()));
            }
        }

        while (!operators.isEmpty()) {
            double b = values.pop();
            double a = values.pop();
            values.push(CalculatorTokensStore.operators.get(operators.pop()).getTokenAction().calculate(a, b));
        }

        return values.pop();
    }

    private static double calculateFunction(MappedFunctionToken functionToken) {
        TokenDetails<Function> func = CalculatorTokensStore.functions.get(functionToken.getKeyword());
        double[] params = new double[functionToken.getTokenizedParams().size()];
        for (int i = 0; i < params.length; i++) {
            params[i] = calculateExpression(functionToken.getTokenizedParams().get(i));
        }
        return func.getTokenAction().calculate(params);
    }
}
