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
class GenericTest {


    LocalDateTime time;
    Location loc;

    Location loc2;

    Child child;

    Generic generic;

    Generic generic2;
    @BeforeAll
    public void setup() {
        time = LocalDateTime.now();
        float lon = 51.1916602952191f;
        float lat = 3.2137996967862925f;
        loc = new Location(lon, lat);
        child = new Child(3, "victor", 1, time);
        generic = new Generic(1, loc, child, time);
        generic2 = new Generic(2, loc, child, time.plusSeconds(12));
        loc2 = new Location(lon, lat + 1);
        generic.setBodyTemp(10);
        generic.setDescription("abc");
        generic.setHearthRate(110);
        generic2.setBodyTemp(10);
        generic2.setDescription("abc");
        generic2.setHearthRate(110);
    }

    @Test
    void getDescription() {
        Assertions.assertNotNull(generic.getDescription());
    }

    @Test
    void getBodyTemp() {
        Assertions.assertNotEquals(12, generic.getBodyTemp());
    }

    @Test
    void getHearthRate() {
        Assertions.assertNotEquals(12, generic.getHearthRate());
    }


    @Test
    void getEventType() {
        Assertions.assertNotEquals(EventType.EATING.getType(), generic.getEventType());
    }

    @Test
    void getEventID() {
        Assertions.assertNotEquals(12, generic.getEventID());
    }

    @Test
    void notEqualEvents() {
        Assertions.assertNotEquals(generic, generic2);
    }

    @Test
    void getEventChild() {
        Assertions.assertNotNull(generic.getChild());
    }

    @Test
    void getEventTime() {
        Assertions.assertNotNull(generic.getTime());
    }

    @Test
    void getEventLocation() {
        Assertions.assertNotNull(generic.getLocation());

    }

    @Test
    void compareToEvent() {
        Assertions.assertEquals(-1, generic.compareTo(generic2));
    }

    @Test
    void testEqualsAndHashCode() {
        Child testchild2 = new Child(5, "victor", 1, time);
        EqualsTester<Generic> equalsTester = EqualsTester.newInstance(generic);
        equalsTester.assertEqual(generic, generic);
        Generic test2 = new Generic(0, loc2, child, time);
        test2.setBodyTemp(10);
        test2.setDescription("abc");
        test2.setHearthRate(110);
        Generic test3 = new Generic(0, loc, testchild2, time);
        test3.setBodyTemp(10);
        test3.setDescription("abc");
        test3.setHearthRate(110);
        Generic test4 = new Generic(0, loc, child, time.minusHours(1));
        test4.setBodyTemp(10);
        test4.setDescription("abc");
        test4.setHearthRate(110);
        Generic compareevent2 = new Generic(0, loc, child, time);
        compareevent2.setHearthRate(100);
        Generic compareevent3 = new Generic(0, loc, child, time);
        Generic compareevent4 = new Generic(0, loc, child, time);
        compareevent4.setBodyTemp(12);
        equalsTester.assertNotEqual(generic2, generic);
        equalsTester.assertNotEqual(test2, generic);
        equalsTester.assertNotEqual(test3, generic);
        equalsTester.assertNotEqual(test4, generic);
        equalsTester.assertNotEqual(compareevent2, generic);
        equalsTester.assertNotEqual(compareevent3, generic);
        equalsTester.assertNotEqual(compareevent4, generic);
    }
}