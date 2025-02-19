package org.ziptegrity.services.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ziptegrity.services.weather.exceptions.FailedToDecodeLocationException;
import org.ziptegrity.services.weather.objects.Location;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LocationDecoder {
    private final static String DECODE_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%d&appid=%s";
    private final static String DECODE_API_KEY = System.getenv("WEATHER_API_KEY");

    public static Location decode(String name) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest req = HttpRequest.newBuilder().GET()
                .uri(URI.create(String.format(DECODE_API_URL, name, 1, DECODE_API_KEY)))
                .build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(res.body()).get(0);
            return new Location(root.get("lat").asDouble(), root.get("lon").asDouble());
        } catch (Exception e) {
            throw new FailedToDecodeLocationException();
        }
    }
}
