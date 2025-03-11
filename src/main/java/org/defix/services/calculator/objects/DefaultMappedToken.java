package org.defix.services.calculator.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.defix.services.calculator.abstractions.MappedToken;
import org.defix.services.calculator.abstractions.TokenType;

@AllArgsConstructor
@Getter
public class DefaultMappedToken implements MappedToken {
    private final TokenType tokenType;
    private final String value;

    @Override
    public TokenType identify() {
        return tokenType;
    }
}
