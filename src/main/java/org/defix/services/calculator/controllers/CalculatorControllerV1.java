package org.defix.services.calculator.controllers;

import org.defix.services.calculator.abstractions.Calculator;
import org.defix.services.calculator.objects.ExpressionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/calculator")
public class CalculatorControllerV1 {
    private final Calculator simpleCalculator;

    @Autowired
    public CalculatorControllerV1(@Qualifier("simpleCalculator") Calculator simpleCalculator) {
        this.simpleCalculator = simpleCalculator;
    }

    @GetMapping("/simple")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Double> calculateSimple(@RequestBody ExpressionWrapper expression) {
        return ResponseEntity.ok(
                simpleCalculator.calculate(decodeExpression(expression.getExpression()))
        );
    }

    private static String decodeExpression(String expression) {
        return new String(Base64.getDecoder().decode(expression));
    }
}
