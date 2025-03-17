package org.defix.services.calculator.controllers;

import org.defix.services.WebUtils;
import org.defix.services.calculator.*;
import org.defix.services.calculator.abstractions.CalculatorType;
import org.defix.services.calculator.abstractions.TokensStore;
import org.defix.services.calculator.exceptions.EmptyExpressionException;
import org.defix.services.calculator.tokenStores.CombinedTokensStore;
import org.defix.services.calculator.tokenStores.SimpleTokensStore;
import org.defix.services.calculator.objects.ExpressionWrapper;
import org.defix.services.calculator.objects.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/calculator")
public class CalculatorControllerV1 {
    @PostMapping("/{type}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Double> calculate(@PathVariable String type, @RequestBody ExpressionWrapper expression) {
        return ResponseEntity.ok(Calculator.calculate(
                WebUtils.decodeFromBase64(expression.getExpression()),
                CalculatorUtils.getTokensStore(type)
        ));
    }

    @GetMapping("/{type}/tokens")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<TokenDTO>> getTokens(@PathVariable String type) {
        return ResponseEntity.ok(
                TokensStoreDataPreparer.prepare(CalculatorUtils.getTokensStore(type))
        );
    }
}