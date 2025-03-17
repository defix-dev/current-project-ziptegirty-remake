package org.defix.services.calculator.abstractions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CalculatorType {
    SIMPLE("simple"),
    PROGRAMMER("programmer"),
    COMBINED("combined");

    private final String type;
}
