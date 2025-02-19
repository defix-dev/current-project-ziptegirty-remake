package org.ziptegrity.services.weather;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import org.ziptegrity.services.weather.objects.Location;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class LocationCache {
    private final Cache<String, Location> locationCache = Caffeine.newBuilder()
            .expireAfterWrite(2, TimeUnit.HOURS)
            .maximumSize(1000)
            .build();

    public Location getLocation(String countryName) {
        return locationCache.get(countryName, (name) -> {
            try {
                return LocationDecoder.decode(name);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
