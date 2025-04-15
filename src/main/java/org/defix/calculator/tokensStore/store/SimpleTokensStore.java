package org.defix.calculator.tokensStore.store;

import org.defix.calculator.function.simple.CosinusFunction;
import org.defix.calculator.function.simple.PowerFunction;
import org.defix.calculator.function.simple.SinusFunction;
import org.defix.calculator.function.simple.SqrtFunction;
import org.defix.calculator.tokensStore.OperatorTokenDetails;
import org.defix.calculator.operator.simple.*;
import org.defix.calculator.function.Function;
import org.defix.calculator.tokensStore.TokensStore;

import java.util.Map;

public class SimpleTokensStore implements TokensStore {

    @Override
    public Map<String, OperatorTokenDetails> getOperators() {
        return Map.ofEntries(
                Map.entry("+", new OperatorTokenDetails(new PlusOperator(), 1)),
                Map.entry("-", new OperatorTokenDetails(new MinusOperator(), 1)),
                Map.entry("*", new OperatorTokenDetails(new MultiplyOperator(), 2)),
                Map.entry("/", new OperatorTokenDetails(new DivideOperator(), 2)),
                Map.entry("^", new OperatorTokenDetails(new PowerOperator(), 2))
        );
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Map.ofEntries(
                Map.entry("sqrt", new SqrtFunction()),
                Map.entry("pow", new PowerFunction()),
                Map.entry("sin", new SinusFunction()),
                Map.entry("cos", new CosinusFunction())
        );
    }
}
