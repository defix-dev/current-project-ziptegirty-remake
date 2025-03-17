
package org.defix.services.calculator.functions.weird;

import org.defix.services.calculator.abstractions.Function;

public class CollatzFunction implements Function {
    public double calculate(double... args) {
        int n = (int) args[0];
        int count = 0;
        while (n != 1) {
            n = (n % 2 == 0) ? n / 2 : 3 * n + 1;
            count++;
        }
        return count;
    }
}
