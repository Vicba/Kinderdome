package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmergencyTest {

    LocalDateTime time;
    Location loc;

    Location loc2;

    Child child;
    ChildcareCenter childcareCenter;

    Caretaker caretaker;

    Emergency em;
    @BeforeAll
    public void setup () {
        time = LocalDateTime.now();
        float lon = 51.1916602952191f;
        float lat = 3.2137996967862925f;
        loc = new Location(lon, lat);
        loc2 = new Location(lon,lat+1);
        child = new Child(3, "victor", 1, LocalDateTime.now());
        childcareCenter = new ChildcareCenter(12,"lala",12);
        caretaker = new Caretaker(3, "seba", childcareCenter, 2500);
        em = new Emergency(1, loc, child, time, EmergencyStatus.ONGOING);
        em.setCaretaker(caretaker);
    }

    @Test
    void getCaretaker() {
        Assertions.assertNotNull(em.getCaretaker());
    }

    @Test
    void testEqualsAndHashCode() {
        EqualsTester<Emergency> equalsTester = EqualsTester.newInstance(em);
        Emergency test2 = new Emergency(0,loc2, child, time,EmergencyStatus.RESOLVED);
        Emergency compareevent2 = new Emergency(1,loc,child,time,EmergencyStatus.ONGOING);
        Caretaker caretaker2 = new Caretaker("nanzfezfzefa",new ChildcareCenter(12,"nana",12),12);
        equalsTester.assertEqual(em, em);
        compareevent2.setCaretaker(caretaker2);
        test2.setCaretaker(caretaker);
        equalsTester.assertNotEqual(test2, em);
        equalsTester.assertNotEqual(em, compareevent2);
    }
}