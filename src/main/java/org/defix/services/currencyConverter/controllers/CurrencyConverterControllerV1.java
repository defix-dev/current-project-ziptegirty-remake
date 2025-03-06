package org.defix.services.currencyConverter.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.defix.services.currencyConverter.CurrencyConverter;
import org.defix.services.currencyConverter.objects.ConverterOptions;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/currency_converter")
public class CurrencyConverterControllerV1 {
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Float> getCurrencyInfo(@RequestParam("fromType") String fromType, @RequestParam("toType") String toType,
                                     @RequestParam("fromValue") float fromValue) throws IOException, InterruptedException {
        return ResponseEntity.ok(CurrencyConverter.convert(new ConverterOptions(
                fromValue,
                fromType,
                toType
        )));
    }
}
