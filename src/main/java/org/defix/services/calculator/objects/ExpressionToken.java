package org.defix.services.calculator.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.defix.services.calculator.abstractions.TokenType;

@Getter
@Setter
@AllArgsConstructor
public class ExpressionToken {
    private TokenType type;
    private String value;
}
