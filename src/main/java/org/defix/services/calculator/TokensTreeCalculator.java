package org.defix.services.calculator;

import lombok.AllArgsConstructor;
import org.defix.services.calculator.abstractions.Function;
import org.defix.services.calculator.abstractions.MappedToken;
import org.defix.services.calculator.abstractions.TokenType;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.objects.*;

import java.util.*;

@AllArgsConstructor
public class TokensTreeCalculator {
    private final TokensStore store;

    public double calculateExpression(LinkedList<MappedToken> tokens) {
        Stack<Double> values = new Stack<>();
        Stack<String> operators = new Stack<>();

        while (!tokens.isEmpty()) {
            MappedToken token = tokens.poll();

            if (token instanceof DefaultMappedToken operandToken) {
                if (operandToken.identify() == TokenType.OPERAND) {
                    values.push(NumberParser.parseNumber(operandToken.getValue()));
                } else if (operandToken.identify() == TokenType.OPERATOR) {
                    while (!operators.isEmpty() && store.getOperators().get(operators.peek()).getOrder()
                            >= store.getOperators().get(operandToken.getValue()).getOrder()) {
                        double b = values.pop();
                        double a = values.pop();
                        values.push(store.getOperators().get(operators.pop()).getAction().calculate(a, b));
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
            values.push(store.getOperators().get(operators.pop()).getAction().calculate(a, b));
        }

        return values.pop();
    }

    public double calculateFunction(MappedFunctionToken functionToken) {
        Function func = store.getFunctions().get(functionToken.getKeyword());
        double[] params = new double[functionToken.getTokenizedParams().size()];
        for (int i = 0; i < params.length; i++) {
            params[i] = calculateExpression(functionToken.getTokenizedParams().get(i));
        }
        return func.calculate(params);
    }
}
