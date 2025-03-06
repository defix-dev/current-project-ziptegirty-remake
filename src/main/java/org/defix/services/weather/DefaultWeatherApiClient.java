package org.defix.services.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.defix.services.weather.abstractions.WeatherApiClient;
import org.defix.services.weather.objects.CurrentWeatherApiDTO;
import org.defix.services.weather.objects.DailyForecastApiDTO;
import org.defix.services.weather.objects.HourlyWeatherApiDTO;
import org.defix.services.weather.objects.Location;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class DefaultWeatherApiClient implements WeatherApiClient {
    private static final Logger logger = LoggerFactory.getLogger(DefaultWeatherApiClient.class);
    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public CurrentWeatherApiDTO getCurrentWeatherData(Location location) throws IOException, InterruptedException {
        logger.debug(STR."Location adapted: \{location.getLatitude()}:\{location.getLongitude()}");
        HttpRequest req = HttpRequest.newBuilder().GET()
                .uri(URI.create(STR."https://api.open-meteo.com/v1/forecast?current_weather=true&latitude=\{location.getLatitude()}&longitude=\{location.getLongitude()}&hourly=temperature,windspeed,relative_humidity_2m,pressure_msl")).build();
        String body = sendWeatherRequest(req).body();
        logger.debug(STR."Current weather source data: \{body}");
        JsonNode root = mapper.readTree(body);
        JsonNode current = root.path("current_weather");
        JsonNode hourly = root.path("hourly");
        String curTime = current.get("time").asText();

        int index = -1;
        Iterator<JsonNode> elements = hourly.get("time").elements();

        LocalDateTime barrier = LocalDateTime.parse(curTime);
        while (elements.hasNext()) {
            JsonNode timeElement = elements.next();
            LocalDateTime t = LocalDateTime.parse(timeElement.asText());
            logger.debug(STR."Parsed time: \{t}");
            if (t.isAfter(barrier)) { break; }
            index++;
        }

        logger.debug(STR."Current time: \{curTime}, Index:\{index}");

        return new CurrentWeatherApiDTO(
                current.get("temperature").asDouble(),
                current.get("windspeed").asDouble(),
                hourly.get("relative_humidity_2m").get(index).asDouble(),
                hourly.get("pressure_msl").get(index).asDouble()
                );
    }

    @Override
    public List<HourlyWeatherApiDTO> getDailyWeatherData(Location location) throws IOException, InterruptedException {
        String curDate = LocalDate.now().toString();
        HttpRequest req = HttpRequest.newBuilder().GET()
                .uri(URI.create(
                        STR."https://api.open-meteo.com/v1/forecast?latitude=\{location.getLatitude()}&longitude=\{location.getLongitude()}&hourly=temperature_2m,weathercode&timezone=auto&start_date=\{curDate}&end_date=\{curDate}"
                )).build();
        JsonNode hourly = mapper.readTree(sendWeatherRequest(req).body()).path("hourly");
        List<HourlyWeatherApiDTO> dailyData = new ArrayList<>();
        IntStream.range(0, hourly.get("time").size()).forEach(i -> {
            LocalTime time = LocalDateTime.parse(hourly.get("time").get(i).asText()).toLocalTime();
            logger.debug(STR."Parsed time: \{time}");
            dailyData.add(new HourlyWeatherApiDTO(
                    time,
                    hourly.get("temperature_2m").get(i).asDouble(),
                    hourly.get("weathercode").get(i).asInt()
            ));
        });

        return dailyData;
    }

    @Override
    public List<DailyForecastApiDTO> getForecastWeatherData(Location location, int dayLimit) throws IOException, InterruptedException {
        String startDate = LocalDate.now().plusDays(1).toString();
        String endDate = LocalDate.now().plusDays(dayLimit).toString();
        HttpRequest req = HttpRequest.newBuilder().GET()
                .uri(URI.create(
                        STR."https://api.open-meteo.com/v1/forecast?latitude=\{location.getLatitude()}&longitude=\{location.getLongitude()}&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=auto&start_date=\{startDate}&end_date=\{endDate}"
                )).build();
        JsonNode daily = mapper.readTree(sendWeatherRequest(req).body()).path("daily");
        List<DailyForecastApiDTO> forecastData = new ArrayList<>();
        IntStream.range(0, daily.get("time").size()).forEach(i -> {
            LocalDate date = LocalDate.parse(daily.get("time").get(i).asText());
            logger.debug(STR."Parsed date: \{date}");
            forecastData.add(new DailyForecastApiDTO(
                    date,
                    daily.get("weathercode").get(i).asInt(),
                    daily.get("temperature_2m_max").get(i).asDouble(),
                    daily.get("temperature_2m_min").get(i).asDouble()
            ));
        });

        return forecastData;
    }

    private HttpResponse<String> sendWeatherRequest(HttpRequest req) throws IOException, InterruptedException {
        return client.send(req, HttpResponse.BodyHandlers.ofString());
    }
}
