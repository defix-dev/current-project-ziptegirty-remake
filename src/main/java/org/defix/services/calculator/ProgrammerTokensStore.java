package org.defix.services.calculator;

import org.defix.services.calculator.abstractions.Function;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.objects.OperatorTokenDetails;
import org.defix.services.calculator.functions.programmer.*;
import org.defix.services.calculator.operators.programmer.*;

import java.util.Map;

public class ProgrammerTokensStore implements TokensStore {

    @Override
    public Map<String, OperatorTokenDetails> getOperators() {
        return Map.ofEntries(
                Map.entry("&", new OperatorTokenDetails(new BitwiseAndOperator(), 3)),
                Map.entry("|", new OperatorTokenDetails(new BitwiseOrOperator(), 2)),
                Map.entry("^", new OperatorTokenDetails(new BitwiseXorOperator(), 2)),
                Map.entry("<<", new OperatorTokenDetails(new LeftShiftOperator(), 4)),
                Map.entry(">>", new OperatorTokenDetails(new RightShiftOperator(), 4))
        );
    }

    @Override
    public Map<String, Function> getFunctions() {
        return Map.ofEntries(
                Map.entry("not", new NotFunction()),
                Map.entry("and", new AndFunction()),
                Map.entry("or", new OrFunction()),
                Map.entry("xor", new XorFunction())
        );
    }
}
