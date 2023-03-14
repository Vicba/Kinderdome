package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.Repositories;
import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.Parent;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultMarsControllerTest {

    private static final String URL = "jdbc:h2:./db-16";

    private MarsController sut;

    @BeforeAll
    void setupTestSuite() {
        Repositories.shutdown();
        JsonObject dbProperties = new JsonObject(Map.of("url", "jdbc:h2:./db-16",
                "username", "",
                "password", "",
                "webconsole.port", 9000));
        Repositories.configure(dbProperties);
    }

    @BeforeEach
    void setupTest() {
        Repositories.getH2Repo().generateData();
        sut = new DefaultMarsController();
    }

    @Test
    void getAllChildren(){
        // Arrange

        assertNotEquals(0,sut.getAllChildren().size());
    }

    @Test
    void addChildToChildcareCenterAlreadyExists(){
        // Arrange
        int childId = 5;
        int ChildcareCenterId = 1;

        assertThrows(IllegalStateException.class, () -> sut.addChildToChildcareCenter(childId, ChildcareCenterId));
    }

    @Test
    void addChildToChildcareCenterNoChildID(){
        int childId = -2;
        int ChildcareCenterId = 1;

        assertThrows(NoSuchElementException.class, () -> sut.addChildToChildcareCenter(childId, ChildcareCenterId));
    }

    @Test
    void addChildToChildcareCenterNoCenter(){
        int childId = 2;
        int ChildcareCenterId = -2;

        assertThrows(NoSuchElementException.class, () -> sut.addChildToChildcareCenter(childId, ChildcareCenterId));
    }

    @Test
    void removeChildFromChildcareCenter(){
        int childId = 5;
        int childcareCenter = 1;

        sut.removeChildFromChildcareCenter(childId, childcareCenter);

        assertEquals(-1, sut.getChild(childId).getChildcareCenterID());

    }

    @Test
    void removeChildFromChildcareCenterWithNoChild(){
        int childId = -2;
        int childcareCenter = 1;

        assertThrows(NoSuchElementException.class, () -> sut.removeChildFromChildcareCenter(childId, childcareCenter));
    }

    @Test
    void removeChildFromCenterCenterDoesNotMatchCenterID(){
        int childId = 2;
        int childcareCenter = 2;

        assertThrows(IllegalStateException.class, () -> sut.removeChildFromChildcareCenter(childId, childcareCenter));
    }


    @Test
    void getParent() {
        // Arrange

        // Act
        Parent parent = sut.getParent(0);

        //Assert
        assertNotNull(parent);
        assertEquals(0,parent.getParentID());
    }

    @Test
    void getParentNull(){
        // Arrange

        //Assert
        assertThrows(NoSuchElementException.class, () -> sut.getParent(20));
    }

    @Test
    void getChild(){
        //Arrange

        // Act
        Child child = sut.getChild(0);

        //Assert
        assertNotNull(child);
        assertEquals(0,child.getChildID());
    }

    @Test
    void getChildNull(){
        //Arrange

        //Assert
        assertThrows(NoSuchElementException.class, () -> sut.getChild(50));
    }

    @Test
    void getChildFromParent(){
        //Arrange
        List<Child> children;

        // Act
        Parent parent = sut.getParent(1);
        children = parent.getChildren();

        Child firstChild = null;

        for (Child child : children) {
            firstChild = child;
            break;
        }

        //Assert
        assertNotNull(firstChild);
        assertEquals(4,firstChild.getChildID());
    }

    @Test
    void getEventsFromChildAndCurrentDay(){
        int childId = 13;

        assertNotEquals(0,sut.getEventsFromChildAndCurrentDay(childId).size());
    }

    @Test
    void getAllChildrenFromChildcareCenter(){
        int centerId = 1;

        assertNotEquals(0,sut.getAllChildrenFromChildcareCenter(centerId).size());
    }

    @Test
    void getAllCaretakersFromChildcareCenter(){
        int centerId = 1;

        assertNotEquals(0,sut.getAllCaretakersFromChildcareCenter(centerId).size());
    }

    @Test
    void getEventTypes(){

        assertNotEquals(0,sut.getEventTypes().size());
    }

    @Test
    void getChildcareCenter(){
        int centerId = 1;


        assertNotNull(sut.getChildcareCenter(centerId));
    }

    @Test
    void getNoChildcareCenter(){
        int centerId = -2;


        assertThrows(NoSuchElementException.class, () -> sut.getChildcareCenter(centerId));
    }

    @Test
    void getCaretakerFromChildcareCenter(){
        int centerId = 1;
        int caretakerId = 1;


        assertNotNull(sut.getCaretakerFromChildcareCenter(centerId, caretakerId));
    }

    @Test
    void getNoCaretakerFromChildcareCenter(){
        int centerId = 1;
        int caretakerId = -2;


        assertThrows(NoSuchElementException.class, () -> sut.getCaretakerFromChildcareCenter(centerId, caretakerId));
    }

    @Test
    void getParentsFromChildcareCenter(){
        int centerId = 1;

        assertNotEquals(0,sut.getParentsFromChildCareCenter(centerId).size());
    }

    @Test
    void getAllChildcareCenters(){
        assertEquals(4,sut.getAllChildcareCenters().size());
    }

    @Test
    void getAllChildrenChildcareCentersNoSuchElement(){
        assertThrows(NoSuchElementException.class,() -> sut.getAllChildrenFromChildcareCenter(50));
    }

    @Test
    void getAllCaretakersFromChildcareCenterNoSuchElement(){
        assertThrows(NoSuchElementException.class,() -> sut.getAllCaretakersFromChildcareCenter(50));
    }
}
