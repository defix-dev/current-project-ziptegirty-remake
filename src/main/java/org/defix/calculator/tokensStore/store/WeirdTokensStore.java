package org.defix.calculator.tokensStore.store;

import org.defix.calculator.function.weird.*;
import org.defix.calculator.tokensStore.OperatorTokenDetails;
import org.defix.calculator.operator.weird.*;
import org.defix.calculator.function.Function;
import org.defix.calculator.tokensStore.TokensStore;

import java.util.Map;

public class WeirdTokensStore implements TokensStore {
    @Override
    public Map<String, OperatorTokenDetails> getOperators() {
        return Map.of(
                "⊕", new OperatorTokenDetails(new ConcatOperator(), 2),
                "∑", new OperatorTokenDetails(new SumDigitsOperator(), 2),
                "↔", new OperatorTokenDetails(new ReverseOperator(), 1),
                "⇡", new OperatorTokenDetails(new HighestDigitOperator(), 1),
                "⨀", new OperatorTokenDetails(new FuzzySumOperator(), 2)
        );
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Map.ofEntries(
                Map.entry("factorize", new FactorizeFunction()),
                Map.entry("logsum", new LogSumFunction()),
                Map.entry("ispalindrome", new IsPalindromeFunction()),
                Map.entry("shuffle", new ShuffleFunction()),
                Map.entry("collatz", new CollatzFunction()),
                Map.entry("fibdigits", new FibDigitsFunction()),
                Map.entry("factsum", new FactSumFunction())
        );
    }
}
