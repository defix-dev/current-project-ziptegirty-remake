package org.defix.services.calculator.tokenStores;

import org.defix.services.calculator.abstractions.Function;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.objects.OperatorTokenDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombinedTokensStore implements TokensStore {
    private final List<TokensStore> storeList = List.of(
            new SimpleTokensStore(),
            new ProgrammerTokensStore(),
            new WeirdTokensStore()
    );

    @Override
    public Map<String, OperatorTokenDetails> getOperators() {
        Map<String, OperatorTokenDetails> operators = new HashMap<>();
        for(TokensStore store : storeList) operators.putAll(store.getOperators());
        return operators;
    }

    @Override
    public Map<String, Function> getFunctions() {
        Map<String, Function> functions = new HashMap<>();
        for(TokensStore store : storeList) functions.putAll(store.getFunctions());
        return functions;
    }
}
