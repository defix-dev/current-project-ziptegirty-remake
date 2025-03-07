package org.defix.services.weather.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.defix.services.weather.WeatherApiFacade;
import org.defix.services.weather.objects.CurrentWeatherApiDTO;
import org.defix.services.weather.objects.DailyForecastApiDTO;
import org.defix.services.weather.objects.HourlyWeatherApiDTO;

import java.util.List;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherControllerV1 {
    private final WeatherApiFacade apiFacade;

    @Autowired
    public WeatherControllerV1(WeatherApiFacade apiFacade) {
        this.apiFacade = apiFacade;
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CurrentWeatherApiDTO> getCurrentWeather(@RequestParam("countryName") String name) {
        return ResponseEntity.ok(apiFacade.getCurrentWeather(name));
    }

    @GetMapping("/daily")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<HourlyWeatherApiDTO>> getDailyWeather(@RequestParam("countryName") String name) {
        return ResponseEntity.ok(apiFacade.getDailyWeather(name));
    }

    @GetMapping("/forecast")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DailyForecastApiDTO>> getForecastWeather(@RequestParam("countryName") String name, @RequestParam("dayLimit") int dayLimit) {
        return ResponseEntity.ok(apiFacade.getForecastWeather(name, dayLimit));
    }
}
