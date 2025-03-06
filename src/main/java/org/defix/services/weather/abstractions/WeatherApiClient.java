package org.defix.services.weather.abstractions;

import org.defix.services.weather.objects.CurrentWeatherApiDTO;
import org.defix.services.weather.objects.DailyForecastApiDTO;
import org.defix.services.weather.objects.HourlyWeatherApiDTO;
import org.defix.services.weather.objects.Location;

import java.io.IOException;
import java.util.List;

public interface WeatherApiClient {
    CurrentWeatherApiDTO getCurrentWeatherData(Location location) throws IOException, InterruptedException;
    List<HourlyWeatherApiDTO> getDailyWeatherData(Location location) throws IOException, InterruptedException;
    List<DailyForecastApiDTO> getForecastWeatherData(Location location, int dayLimit) throws IOException, InterruptedException;
}
