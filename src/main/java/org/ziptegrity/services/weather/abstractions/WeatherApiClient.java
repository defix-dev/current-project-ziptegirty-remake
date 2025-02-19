package org.ziptegrity.services.weather.abstractions;

import org.ziptegrity.services.weather.objects.CurrentWeatherApiDTO;
import org.ziptegrity.services.weather.objects.DailyForecastApiDTO;
import org.ziptegrity.services.weather.objects.HourlyWeatherApiDTO;
import org.ziptegrity.services.weather.objects.Location;

import java.io.IOException;
import java.util.List;

public interface WeatherApiClient {
    CurrentWeatherApiDTO getCurrentWeatherData(Location location) throws IOException, InterruptedException;
    List<HourlyWeatherApiDTO> getDailyWeatherData(Location location) throws IOException, InterruptedException;
    List<DailyForecastApiDTO> getForecastWeatherData(Location location, int dayLimit) throws IOException, InterruptedException;
}
