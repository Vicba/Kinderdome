package be.howest.ti.mars.logic.domain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CaretakerTest {

    ChildcareCenter childcareCenter = new ChildcareCenter(1, "center1", 25);

    Caretaker caretaker = new Caretaker(3,"seba", childcareCenter, 2500);

    @Test
    void getCaretakerID() {
        Assertions.assertEquals(3, caretaker.getCaretakerID());
    }

    @Test
    void getName() {
        Assertions.assertEquals("seba", caretaker.getName());
    }

    @Test
    void getChildcareCenter() {
        Assertions.assertEquals(childcareCenter, caretaker.getChildcareCenter());
    }

    @Test
    void getSalary() {
        Assertions.assertEquals(2500, caretaker.getSalary());
    }
}