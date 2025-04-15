package org.defix.calculator.tokensStore.store;

import org.defix.calculator.function.Function;
import org.defix.calculator.tokensStore.TokensStore;
import org.defix.calculator.function.programmer.AndFunction;
import org.defix.calculator.function.programmer.NotFunction;
import org.defix.calculator.function.programmer.OrFunction;
import org.defix.calculator.function.programmer.XorFunction;
import org.defix.calculator.tokensStore.OperatorTokenDetails;
import org.defix.calculator.operator.programmer.*;
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
