package org.defix.services.calculator.tokenStores;

import org.defix.services.calculator.abstractions.Function;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.functions.simple.CosinusFunction;
import org.defix.services.calculator.functions.simple.PowerFunction;
import org.defix.services.calculator.functions.simple.SinusFunction;
import org.defix.services.calculator.functions.simple.SqrtFunction;
import org.defix.services.calculator.objects.OperatorTokenDetails;
import org.defix.services.calculator.operators.simple.*;

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
