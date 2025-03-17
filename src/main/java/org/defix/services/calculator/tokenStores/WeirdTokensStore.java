package org.defix.services.calculator.tokenStores;

import org.defix.services.calculator.abstractions.Function;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.functions.weird.*;
import org.defix.services.calculator.objects.OperatorTokenDetails;
import org.defix.services.calculator.operators.simple.*;
import org.defix.services.calculator.functions.simple.*;
import org.defix.services.calculator.objects.OperatorTokenDetails;
import org.defix.services.calculator.operators.weird.*;

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
