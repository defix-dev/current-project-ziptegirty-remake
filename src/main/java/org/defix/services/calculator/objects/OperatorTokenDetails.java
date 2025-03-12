package org.defix.services.calculator.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.defix.services.calculator.abstractions.Operator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperatorTokenDetails {
    private Operator action;
    private int order;
}
