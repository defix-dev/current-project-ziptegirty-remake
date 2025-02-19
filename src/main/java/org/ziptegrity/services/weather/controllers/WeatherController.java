package org.ziptegrity.services.weather.controllers;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.ziptegrity.services.weather.WeatherApiFacade;
import org.ziptegrity.services.weather.objects.CurrentWeatherApiDTO;
import org.ziptegrity.services.weather.objects.DailyForecastApiDTO;
import org.ziptegrity.services.weather.objects.HourlyWeatherApiDTO;

import java.util.List;

@Controller
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherApiFacade apiFacade;

    @Autowired
    public WeatherController(WeatherApiFacade apiFacade) {
        this.apiFacade = apiFacade;
    }

    @GetMapping("/get_current_weather")
    public ResponseEntity<CurrentWeatherApiDTO> getCurrentWeather(@RequestParam("countryName") String name) {
        return null;
    }

    @GetMapping("/get_daily_weather")
    public ResponseEntity<List<HourlyWeatherApiDTO>> getDailyWeather(@RequestParam("countryName") String name) {
        return null;
    }

    @GetMapping("/get_forecast_weather")
    public ResponseEntity<List<DailyForecastApiDTO>> getForecastWeather(@RequestParam("countryName") String name, @RequestParam("dayLimit") int dayLimit) {
        return null;
    }
}
