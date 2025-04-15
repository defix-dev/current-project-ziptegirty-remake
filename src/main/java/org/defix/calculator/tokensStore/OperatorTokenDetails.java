package org.defix.calculator.tokensStore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.defix.calculator.operator.Operator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperatorTokenDetails {
    private Operator action;
    private int order;
}
