package org.ziptegrity.services.currencyConverter.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ziptegrity.services.currencyConverter.CurrencyConverter;
import org.ziptegrity.services.currencyConverter.objects.ConverterOptions;

import java.io.IOException;

@RestController
@RequestMapping("/api/currency_converter")
public class CurrencyConverterController {
    @GetMapping("/get")
    public ResponseEntity<Float> get(@RequestParam("fromType") String fromType, @RequestParam("toType") String toType,
                                     @RequestParam("fromValue") float fromValue) throws IOException, InterruptedException {
        return ResponseEntity.ok(CurrencyConverter.convert(new ConverterOptions(
                fromValue,
                fromType,
                toType
        )));
    }
}
