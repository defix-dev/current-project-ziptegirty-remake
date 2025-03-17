package org.defix.services.calculator.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.defix.services.calculator.abstractions.MappedToken;
import org.defix.services.calculator.abstractions.TokenType;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Getter
public class MappedFunctionToken implements MappedToken {
    private String keyword;
    private List<List<MappedToken>> tokenizedParams;

    @Override
    public TokenType identify() {
        return TokenType.FUNCTION;
    }
}
