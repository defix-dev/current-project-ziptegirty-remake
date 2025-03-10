package org.defix.services.calculator;

import org.defix.services.calculator.abstractions.MappedToken;
import org.defix.services.calculator.abstractions.TokenType;
import org.defix.services.calculator.objects.DefaultMappedToken;
import org.defix.services.calculator.objects.ExpressionToken;
import org.defix.services.calculator.objects.MappedExpressionToken;
import org.defix.services.calculator.objects.MappedFunctionToken;

import java.util.LinkedList;
import java.util.List;

public class TokenTreeBuilder {
    public static LinkedList<MappedToken> build(LinkedList<ExpressionToken> tokens) {
        LinkedList<MappedToken> result = new LinkedList<>();
        for (ExpressionToken token : tokens) {
            switch (token.getType()) {
                case EXPRESSION -> result.add(new MappedExpressionToken(
                        build(
                                CalculatorTokenizer.tokenizeExpression(token.getValue())
                        )
                ));
                case FUNCTION -> {
                    int index = token.getValue().indexOf('(');
                    String keyword = (index > 0) ? token.getValue().substring(0, index) : token.getValue();

                    LinkedList<LinkedList<MappedToken>> tokenizedParams = new LinkedList<>();
                    for (LinkedList<ExpressionToken> paramExp : CalculatorTokenizer.tokenizeFunctionParams(token.getValue())) {
                        tokenizedParams.add(build(paramExp));
                    }

                    result.add(new MappedFunctionToken(keyword, tokenizedParams));
                }
                default -> result.add(new DefaultMappedToken(token.getType(), token.getValue()));
            }
        }

        return result;
    }
}
