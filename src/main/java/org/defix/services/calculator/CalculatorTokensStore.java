package org.defix.services.calculator;

import org.defix.services.calculator.abstractions.Function;
import org.defix.services.calculator.abstractions.Operator;
import org.defix.services.calculator.exceptions.TokenNotAllowedException;
import org.defix.services.calculator.functions.CosinusFunction;
import org.defix.services.calculator.functions.PowerFunction;
import org.defix.services.calculator.functions.SinusFunction;
import org.defix.services.calculator.functions.SqrtFunction;
import org.defix.services.calculator.objects.TokenDetails;
import org.defix.services.calculator.operators.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CalculatorTokensStore {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorTokensStore.class);

    public static final Map<String, TokenDetails<Function>> functions = Map.ofEntries(
            Map.entry("sqrt", new TokenDetails<>(new SqrtFunction(), 1)),
            Map.entry("pow", new TokenDetails<>(new PowerFunction(), 1)),
            Map.entry("sin", new TokenDetails<>(new SinusFunction(), 1)),
            Map.entry("cos", new TokenDetails<>(new CosinusFunction(), 1))
    );

    public static final Map<String, TokenDetails<Operator>> operators = Map.ofEntries(
            Map.entry("+", new TokenDetails<>(new PlusOperator(), 1)),
            Map.entry("-", new TokenDetails<>(new MinusOperator(), 1)),
            Map.entry("*", new TokenDetails<>(new MultiplyOperator(), 2)),
            Map.entry("/", new TokenDetails<>(new DivideOperator(), 2)),
            Map.entry("^", new TokenDetails<>(new PowerOperator(), 2))
    );

    static {
        try {
            TokensStoreValidator.validate(functions);
            TokensStoreValidator.validate(operators);
        } catch (TokenNotAllowedException e) {
            logger.debug(e.getMessage());
            System.exit(1);
        }
    }
}
