package org.defix.services.calculator;

import lombok.AllArgsConstructor;
import org.defix.services.calculator.abstractions.MappedToken;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.objects.DefaultMappedToken;
import org.defix.services.calculator.objects.RawToken;
import org.defix.services.calculator.objects.MappedExpressionToken;
import org.defix.services.calculator.objects.MappedFunctionToken;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class TokensTreeBuilder {
    private final TokensStore store;

    public List<MappedToken> build(List<RawToken> tokens) {
        List<MappedToken> result = new ArrayList<>();
        for (RawToken token : tokens) {
            switch (token.getType()) {
                case EXPRESSION -> result.add(new MappedExpressionToken(
                        build(
                                new ExpressionTokenizer(store).tokenizeExpression(token.getValue())
                        )
                ));
                case FUNCTION -> {
                    int index = token.getValue().indexOf('(');
                    String keyword = (index > 0) ? token.getValue().substring(0, index) : token.getValue();

                    List<List<MappedToken>> tokenizedParams = new ArrayList<>();
                    for (List<RawToken> paramExp : new FunctionTokenizer(store).tokenizeFunction(token.getValue())) {
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
