package be.howest.ti.mars.logic.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParentTest {

    @Test
    void getParentID() {
        List<Child> children = Collections.emptyList();
        Parent parent = new Parent(1, "Matthias", 1994, children);

        Assertions.assertEquals(1, parent.getParentID());
    }

    @Test
    void getName() {
        List<Child> children = Collections.emptyList();
        Parent parent = new Parent(1, "Matthias", 1994, children);

        Assertions.assertEquals("Matthias", parent.getName());
    }

    @Test
    void getYear() {
        List<Child> children = Collections.emptyList();
        Parent parent = new Parent(1, "Matthias", 1994, children);

        Assertions.assertEquals(1994, parent.getYear());
    }

    @Test
    void getChildren() {
        List<Child> children = new ArrayList<>();

        children.add(new Child(1,"victor", 1, LocalDateTime.now()));
        children.add(new Child(2,"seba", 1, LocalDateTime.now()));

        Parent parent = new Parent(1, "Matthias", 1994, children);

        Assertions.assertEquals(2, parent.getChildren().size());
    }
}