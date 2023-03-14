package be.howest.ti.mars.logic.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ChildTest {
    @Test
    void getChildName(){
        Child child = new Child("victor", 1, LocalDateTime.now());

        Assertions.assertEquals("victor",child.getChildName());
    }

    @Test
    void getChildId(){
        Child child = new Child(3,"victor", 1, LocalDateTime.now());
        Assertions.assertEquals(3, child.getChildID());
    }

    @Test
    void getChildCareCenterID(){
        Child child = new Child(3,"victor", 1, LocalDateTime.now());
        Assertions.assertEquals(1, child.getChildcareCenterID());
    }

    @Test
    void getChildBirthday(){
        Child child = new Child(3,"victor", 1, LocalDateTime.now());
        Assertions.assertNotNull(child.getBirthdate());
    }

    @Test
    void setChildCareCenterID(){
        Child child = new Child(3,"victor", 1, LocalDateTime.now());
        ChildcareCenter childcareCenter = new ChildcareCenter(2, "center1", 25);

        child.setChildcareCenterID(childcareCenter.getChildcareCenterID());

        Assertions.assertEquals(2, child.getChildcareCenterID());
    }

    @Test
    void noEqualChild(){
        Child child = new Child(3,"victor", 1, LocalDateTime.now());
        Child child2 = new Child(4,"seba", 1, LocalDateTime.now());

        Assertions.assertNotEquals(child,child2);
    }

    @Test
    void compareTo(){
        Child child = new Child(3,"victor", 1, LocalDateTime.now());
        Child child2 = new Child(4,"victor", 1, LocalDateTime.now());

        Assertions.assertEquals(0, child.compareTo(child2));
    }

    @Test
    void testEqualsAndHashCode(){
        LocalDateTime time = LocalDateTime.now();
        Child testchild = new Child(3,"victor", 1, time);
        EqualsTester<Child> equalsTester = EqualsTester.newInstance( testchild);
        equalsTester.assertEqual( testchild, testchild );
        equalsTester.assertNotEqual( new Child(3,"victor", 2, time), testchild );
        equalsTester.assertNotEqual( new Child(4,"victor", 1, time), testchild );
        equalsTester.assertNotEqual( new Child(3,"daan", 1, time), testchild );
        equalsTester.assertNotEqual( new Child(3,"victor", 1, time.minusHours(1)), testchild );

    }
}