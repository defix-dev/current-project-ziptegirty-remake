package org.ziptegrity.services.weather.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeatherApiDTO {
    private double temperature;
    private double windSpeed;
    private double humidityIntensive;
    private double pressure;
}
