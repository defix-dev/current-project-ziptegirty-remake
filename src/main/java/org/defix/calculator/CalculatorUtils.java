package org.defix.calculator;

import org.defix.calculator.exception.CalculatorNotFoundException;
import org.defix.calculator.interpretator.TokensTreeBuilder;
import org.defix.calculator.interpretator.TokensTreeCalculator;
import org.defix.calculator.interpretator.tokenizer.ExpressionTokenizer;
import org.defix.calculator.api.dto.response.TokenDTO;
import org.defix.calculator.tokensStore.GlobalTokensStore;
import org.defix.calculator.tokensStore.TokensStore;

import java.util.ArrayList;
import java.util.List;

public class CalculatorUtils {
    public static double calculateExpression(String expression, TokensStore store) {
        return new TokensTreeCalculator(store).calculateExpression(
                new TokensTreeBuilder(store).build(
                        new ExpressionTokenizer(store).tokenizeExpression(expression)
                )
        );
    }

    public static double calculateExpression(String expression, String calcType) {
        TokensStore typedStore = getTokensStoreFromString(calcType);
        return new TokensTreeCalculator(typedStore).calculateExpression(
                new TokensTreeBuilder(typedStore).build(
                        new ExpressionTokenizer(typedStore).tokenizeExpression(expression)
                )
        );
    }

    public static List<TokenDTO> prepareTokensStoreToDTO(String calcType) {
        TokensStore typedStore = getTokensStoreFromString(calcType);
        List<TokenDTO> tokens = new ArrayList<>(typedStore.getFunctions().keySet().stream().map(
                v -> new TokenDTO("function", v)
        ).toList());
        tokens.addAll(
                typedStore.getOperators().keySet().stream().map(
                        v -> new TokenDTO("operator", v)
                ).toList()
        );
        return tokens;
    }

    private static TokensStore getTokensStoreFromString(String calcType) {
        try {
            return GlobalTokensStore.tokens.get(CalculatorType.valueOf(calcType.toUpperCase()));
        } catch (Exception e) {
            throw new CalculatorNotFoundException();
        }
    }
}
