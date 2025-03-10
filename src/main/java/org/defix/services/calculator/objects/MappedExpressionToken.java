package org.defix.services.calculator.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.defix.services.calculator.abstractions.MappedToken;
import org.defix.services.calculator.abstractions.TokenType;

import java.util.LinkedList;

@AllArgsConstructor
@Getter
public class MappedExpressionToken implements MappedToken {
    private LinkedList<MappedToken> tokens;

    @Override
    public TokenType identify() {
        return TokenType.EXPRESSION;
    }
}
