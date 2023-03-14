package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.EqualsTester;
import be.howest.ti.mars.logic.domain.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EatingTest {

    Child child;
    Eating eating;
    Eating eating2;

    LocalDateTime time;
    @BeforeAll
    public void setup () {
        float lon = 51.1916602952191f;
        float lat = 3.2137996967862925f;
        Location loc = new Location(lon, lat);

        time = LocalDateTime.now();

        child = new Child(3, "victor", 1, LocalDateTime.now());

        eating = new Eating(1, loc, child, 20, LocalDateTime.now());
    }

    @Test
    void getNutritionalValue() {
        Assertions.assertNotEquals(12, eating.getNutritionalValue());
    }

    @Test
    void noEqualEatings() {
        Assertions.assertNotEquals(eating, eating2);
    }

    @Test
    void testEqualsAndHashCode() {
        double lon = 51.1916602952191;
        double lat = 3.2137996967862925;
        Location loc = new Location((float) lon, (float) lat);
        Eating compareevent = new Eating(0, loc, child, 10, time);
        Eating compareevent3 = new Eating(0, loc, child, 12, time);
        EqualsTester<Eating> equalsTester = EqualsTester.newInstance(compareevent);
        equalsTester.assertEqual(compareevent, compareevent);
        equalsTester.assertNotEqual(compareevent, compareevent3);
    }
}