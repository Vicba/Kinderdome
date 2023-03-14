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
class SleepingTest {

    LocalDateTime time;

    Location loc;

    Location loc2;

    Child child;

    Sleeping sleeping;

    Sleeping sleeping2;

    @BeforeAll
    public void setup() {
        time = LocalDateTime.now();
        float lon = 51.1916602952191f;
        float lat = 3.2137996967862925f;
        loc = new Location(lon, lat);
        child = new Child(3, "victor", 1, time);
        sleeping = new Sleeping(1, loc, child, 20, time);
        sleeping2 = new Sleeping(2, loc, child, 25, time);
        loc2 = new Location(lon,lat+1);
    }

    @Test
    void getDepthScore() {
        Assertions.assertNotEquals(12,sleeping.getDepthScore());
    }

    @Test
    void notEqualSleepings(){
        Assertions.assertNotEquals(sleeping, sleeping2);
    }

    @Test
    void testEqualsAndHashCode() {
        Sleeping compareevent1 = new Sleeping(1, loc2, child,10, time);
        Child child2 = new Child(3, "daan", 1, time);
        Sleeping compareevent2 = new Sleeping(1, loc, child2,12, time);
        Sleeping compareevent3 = new Sleeping(1, loc, child,30, time);
        EqualsTester<Sleeping> equalsTester = EqualsTester.newInstance(sleeping);
        equalsTester.assertEqual(sleeping, sleeping);
        equalsTester.assertNotEqual(sleeping, sleeping2);
        equalsTester.assertNotEqual(sleeping, compareevent1);
        equalsTester.assertNotEqual(sleeping, compareevent2);
        equalsTester.assertNotEqual(sleeping, compareevent3);
    }
}