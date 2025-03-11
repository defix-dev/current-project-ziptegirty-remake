package org.defix.services.calculator.objects;

import lombok.AllArgsConstructor;
import org.defix.services.calculator.abstractions.Operator;

@AllArgsConstructor
public class SequenceOperator implements Comparable<SequenceOperator> {
    private final TokenDetails<Operator> operatorDetails;

    public double calculate(double a, double b) {
        return operatorDetails.getTokenAction().calculate(a, b);
    }

    @Override
    public int compareTo(SequenceOperator o) {
        return Integer.compare(this.operatorDetails.getOrder(), o.operatorDetails.getOrder());
    }
}
