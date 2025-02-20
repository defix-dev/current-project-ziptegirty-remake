package org.ziptegrity.tests;

import org.junit.jupiter.api.Test;
import org.ziptegrity.services.weather.LocationDecoder;
import org.ziptegrity.services.weather.objects.Location;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class LocationDecoderTest {
    @Test
    public void decodeTest() throws IOException, InterruptedException {
        Location location = LocationDecoder.decode("Vologda");
        assertEquals(location.getLatitude(), 59.218876);
        assertEquals(location.getLongitude(), 39.893276);
    }
}
