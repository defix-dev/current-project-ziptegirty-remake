package org.defix.calculator.tokensStore;

import org.defix.calculator.CalculatorType;
import org.defix.calculator.tokensStore.store.CombinedTokensStore;
import org.defix.calculator.tokensStore.store.ProgrammerTokensStore;
import org.defix.calculator.tokensStore.store.SimpleTokensStore;

import java.util.Map;

public class GlobalTokensStore {
    public static final Map<CalculatorType, TokensStore> tokens = Map.ofEntries(
            Map.entry(CalculatorType.SIMPLE, new SimpleTokensStore()),
            Map.entry(CalculatorType.PROGRAMMER, new ProgrammerTokensStore()),
            Map.entry(CalculatorType.COMBINED, new CombinedTokensStore())
    );
}
