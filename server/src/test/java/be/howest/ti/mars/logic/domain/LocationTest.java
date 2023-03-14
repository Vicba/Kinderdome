package be.howest.ti.mars.logic.domain;

import com.sun.jdi.FloatValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    Double lon = 51.1916602952191;
    Double lat = 3.2137996967862925;
    Location loc = new Location(lon.floatValue(), lat.floatValue());

    @Test
    void getLongitude() {
        Assertions.assertNotNull(loc.getLongitude());
    }

    @Test
    void getLatitude() {
        Assertions.assertNotNull(loc.getLatitude());
    }
}