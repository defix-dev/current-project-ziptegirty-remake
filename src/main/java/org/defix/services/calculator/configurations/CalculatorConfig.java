package org.defix.services.calculator.configurations;

import org.defix.services.calculator.ProgrammerTokensStore;
import org.defix.services.calculator.SimpleTokensStore;
import org.defix.services.calculator.abstractions.Calculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorConfig {
    @Bean(name = "simpleCalculator")
    public Calculator simpleCalculator() {
        return new Calculator(new SimpleTokensStore());
    }

    @Bean(name = "programmerCalculator")
    public Calculator programmerCalculator() {
        return new Calculator(new ProgrammerTokensStore());
    }
}
