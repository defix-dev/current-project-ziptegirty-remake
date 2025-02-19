package org.ziptegrity.services.weather.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HourlyWeatherApiDTO {
    private LocalTime time;
    private double temperature;
    private int state;
}
