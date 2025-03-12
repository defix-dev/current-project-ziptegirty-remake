package org.defix.services.calculator.abstractions;

import org.defix.services.calculator.objects.OperatorTokenDetails;

import java.util.Map;

public interface TokensStore {
    Map<String, OperatorTokenDetails> getOperators();
    Map<String, Function> getFunctions();
}
