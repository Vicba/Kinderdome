package be.howest.ti.mars.logic.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ChildcareCenterTest {
    ChildcareCenter childcareCenter = new ChildcareCenter(1, "center1", 25);

    @Test
    void getChildcareCenterID() {
        Assertions.assertEquals(1, childcareCenter.getChildcareCenterID());
    }

    @Test
    void getChildcareCenterName() {
        ChildcareCenter childcareCenter = new ChildcareCenter("bruh", 12);

        Assertions.assertEquals(-1, childcareCenter.getChildcareCenterID());
    }

    @Test
    void getChildLimit() {
        Assertions.assertEquals(25, childcareCenter.getChildLimit());
    }
}