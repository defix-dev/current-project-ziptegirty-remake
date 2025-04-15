package org.defix.calculator.tokensStore.store;

import org.defix.calculator.tokensStore.OperatorTokenDetails;
import org.defix.calculator.function.Function;
import org.defix.calculator.tokensStore.TokensStore;

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
