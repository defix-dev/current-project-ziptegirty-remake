package org.defix.services.calculator;

import org.defix.services.calculator.exceptions.TokenNotAllowedException;

import java.util.Map;

public class TokensStoreValidator {
    public static void validate(Map<String, ?> tokensMap) throws TokenNotAllowedException {
        for(String token : tokensMap.keySet()) {
            if(token.isEmpty() || !token.replace(" ", "").equals(token))
                throw new TokenNotAllowedException();
        }
    }
}
