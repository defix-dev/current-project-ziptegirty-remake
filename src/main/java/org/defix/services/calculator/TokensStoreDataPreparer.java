package org.defix.services.calculator;

import org.defix.services.calculator.abstractions.TokenType;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.objects.TokenDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TokensStoreDataPreparer {
    public static List<TokenDTO> prepare(TokensStore store) {
        List<TokenDTO> tokens = new ArrayList<>(store.getFunctions().keySet().stream().map(
                v -> new TokenDTO("function", v)
        ).toList());
        tokens.addAll(
                store.getOperators().keySet().stream().map(
                        v -> new TokenDTO("operator", v)
                ).toList()
        );
        return tokens;
    }
}
