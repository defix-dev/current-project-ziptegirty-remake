package org.ziptegrity.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ziptegrity.services.weather.abstractions.WeatherApiClient;
import org.ziptegrity.services.weather.objects.CurrentWeatherApiDTO;
import org.ziptegrity.services.weather.objects.Location;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WeatherApiClientTest {
    @Autowired
    private WeatherApiClient apiClient;

    @Test
    public void getCurrentWeatherDataTest() throws IOException, InterruptedException {
        CurrentWeatherApiDTO data = apiClient.getCurrentWeatherData(new Location(22, 22));
        assertNotNull(data);
    }
}
