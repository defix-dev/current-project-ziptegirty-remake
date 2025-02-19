package org.ziptegrity.services.currencyConverter.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConverterOptions {
    private float fromValue;
    private String fromType;
    private String toType;
}
