package org.ziptegrity.services.weather;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ziptegrity.services.weather.abstractions.WeatherApiClient;
import org.ziptegrity.services.weather.exceptions.FailedToExecuteWeatherApiRequestException;
import org.ziptegrity.services.weather.objects.CurrentWeatherApiDTO;
import org.ziptegrity.services.weather.objects.HourlyWeatherApiDTO;
import org.ziptegrity.services.weather.objects.DailyForecastApiDTO;

import java.io.IOException;
import java.util.List;

@Service
public class WeatherApiFacade {
    private static final Logger logger = LoggerFactory.getLogger(WeatherApiFacade.class);
    private final LocationCache locCache;
    private final WeatherApiClient apiClient;

    @Autowired
    public WeatherApiFacade(LocationCache locCache,
                            WeatherApiClient apiClient) {
        this.locCache = locCache;
        this.apiClient = apiClient;
    }

    public CurrentWeatherApiDTO getCurrentWeather(String countryName) {
        try {
            return apiClient.getCurrentWeatherData(locCache.getLocation(countryName));
        } catch (Exception e) {
            logger.error(STR."Failed to get current weather data: \{countryName}");
            throw new FailedToExecuteWeatherApiRequestException(e.getMessage());
        }
    }

    public List<HourlyWeatherApiDTO> getDailyWeather(String countryName) {
        try {
            return apiClient.getDailyWeatherData(locCache.getLocation(countryName));
        } catch (Exception e) {
            logger.error(STR."Failed to get daily weather data: \{countryName}");
            throw new FailedToExecuteWeatherApiRequestException(e.getMessage());
        }
    }

    public List<DailyForecastApiDTO> getForecastWeather(String countryName, int dayLimit) {
        try {
            return apiClient.getForecastWeatherData(locCache.getLocation(countryName), dayLimit);
        } catch (Exception e) {
            logger.error(STR."Failed to get forecast weather data: \{countryName}");
            throw new FailedToExecuteWeatherApiRequestException(e.getMessage());
        }
    }
}
