package org.defix.services.calculator.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SequenceExpressionToken {
    private ExpressionToken token;
    private int orderId;
}
