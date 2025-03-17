package org.defix.services.calculator;

import org.defix.services.calculator.abstractions.CalculatorType;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.tokenStores.CombinedTokensStore;
import org.defix.services.calculator.tokenStores.ProgrammerTokensStore;
import org.defix.services.calculator.tokenStores.SimpleTokensStore;

import java.util.Map;

public class GlobalTokensStore {
    public static final Map<CalculatorType, TokensStore> tokens = Map.ofEntries(
            Map.entry(CalculatorType.SIMPLE, new SimpleTokensStore()),
            Map.entry(CalculatorType.PROGRAMMER, new ProgrammerTokensStore()),
            Map.entry(CalculatorType.COMBINED, new CombinedTokensStore())
    );
}
