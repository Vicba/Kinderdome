package be.howest.ti.mars.logic.data;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.domain.events.EmergencyStatus;
import be.howest.ti.mars.logic.exceptions.RepositoryException;
import be.howest.ti.mars.logic.util.LocalDateTimeCalculator;
import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.ChildcareCenter;
import be.howest.ti.mars.logic.domain.Parent;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

class MarsH2RepositoryTest {
    private static final String URL = "jdbc:h2:./db-16";

    @BeforeEach
    void setupTestSuite() {
        Repositories.shutdown();
        JsonObject dbProperties = new JsonObject(Map.of("url",URL,
                "username", "",
                "password", "",
                "webconsole.port", 9000 ));
        Repositories.configure(dbProperties);
    }

    @Test
    void getAllChildren(){
        // Act
        List<Child> children = Repositories.getH2Repo().getAllChildren();

        // Assert
        Assertions.assertEquals(25,children.size());
    }

    @Test
    void getParent() {
        // Arrange
        int id = 1;

        // Act
        Parent parent = Repositories.getH2Repo().getParent(id);

        // Assert
        Assertions.assertNotNull(parent);

    }

    @Test
    void getNoParent(){
        // Arrange
        int id = -1;

        //Assert
        Assertions.assertNull(Repositories.getH2Repo().getParent(id));
    }


    @Test
    void getParentInfo(){
        // Arrange
        int id = 1;

        // Act
        Parent parent = Repositories.getH2Repo().getParent(id);

        // Assert
        Assertions.assertEquals("Mats", parent.getName());
        Assertions.assertEquals(2002, parent.getYear());
    }

    @Test
    void getChildrenSizeOfParent(){
        // Arrange
        int id = 1;

        // Act
        Parent parent = Repositories.getH2Repo().getParent(id);

        // Assert
        Assertions.assertEquals(10, parent.getChildren().size());
    }

    @Test
    void getChild() {
        // Arrange
        int id = 1;

        // Act
        Child child = Repositories.getH2Repo().getChild(id);

        // Assert
        Assertions.assertNotNull(child);

    }

    @Test
    void getNoChild(){
        // Arrange
        int id = -1;

        // Assert
        Assertions.assertNull(Repositories.getH2Repo().getChild(id));
    }

    @Test
    void getChildInfo(){
        // Arrange
        int id = 1;

        // Act
        Child child = Repositories.getH2Repo().getChild(id);

        // Assert
        Assertions.assertEquals("Mats", child.getChildName());
        Assertions.assertNotNull(child.getBirthdate());
        Assertions.assertEquals(1, child.getChildcareCenterID());
    }

    @Test
    void getChildcareCenter() {
        // Arrange
        int id = 1;

        // Act
        ChildcareCenter childcareCenter = Repositories.getH2Repo().getChildcareCenter(id);

        // Assert
        Assertions.assertNotNull(childcareCenter);
    }

    @Test
    void getNoChildcareCenter(){
        // Arrange
        int id = -2;

        // Assert
        Assertions.assertNull(Repositories.getH2Repo().getChildcareCenter(id));
    }

    @Test
    void getChildcareCenterInfo(){
        // Arrange
        int id = 1;

        // Act
        ChildcareCenter childcareCenter = Repositories.getH2Repo().getChildcareCenter(id);

        // Assert
        Assertions.assertEquals("HOWEST", childcareCenter.getChildcareCenterName());
        Assertions.assertEquals(25, childcareCenter.getChildLimit());

    }

    @Test
    void getAllChildcareCenters(){
        // Act
        List<ChildcareCenter> centers = Repositories.getH2Repo().getAllChildcareCenters();

        // Assert
        Assertions.assertEquals(4,centers.size());
    }


    @Test
    void getChildrenFromParent() {
        // Arrange
        int id = 1;
        // Act
        Parent parent = Repositories.getH2Repo().getParent(id);

        // Assert
        Assertions.assertFalse(parent.getChildren().isEmpty());
    }

    @Test
    void getAllChildrenFromChildcareCenter() {
        // Arrange
        int centerId = 1;
        // Act
        List<Child> children = Repositories.getH2Repo().getAllChildrenFromChildcareCenter(centerId);

        // Assert
        Assertions.assertEquals(25,children.size());
    }

    @Test
    void getNoChildrenFromChildcareCenter(){
        // Arrange
        int centerId = 2;
        // Act
        List<Child> children = Repositories.getH2Repo().getAllChildrenFromChildcareCenter(centerId);

        // Assert
        Assertions.assertTrue(children.isEmpty());
    }

    @Test
    void getNoEventsFromPeriod() {
        // Arrange
        int childId = 0;

        // Act
        List<LocalDateTime> range = LocalDateTimeCalculator.getMockDayRange();
        List<Event> events = Repositories.getH2Repo().getEventsFromPeriod(range.get(0), range.get(1), childId);

        //Assert
        Assertions.assertFalse(events.isEmpty());
    }

    @Test
    void getEventsFromPeriod(){
        // Arrange
        int childId = 8;

        // Act
        List<LocalDateTime> range = LocalDateTimeCalculator.getMockDayRange();
        List<Event> events = Repositories.getH2Repo().getEventsFromPeriod(range.get(0), range.get(1), childId);

        // Assert
        Assertions.assertEquals(14, events.size());
    }

    @Test
    void getAllCaretakersFromChildcareCenter() {
        // Arrange
        int centerId = 1;
        // Act
        List<Caretaker> caretakers = Repositories.getH2Repo().getAllCaretakersFromChildcareCenter(centerId);

        // Assert
        Assertions.assertEquals(6,caretakers.size());
    }

    @Test
    void getNoCaretakersFromChildcareCenter(){
        // Arrange
        int centerId = 2;
        // Act
        List<Caretaker> caretakers = Repositories.getH2Repo().getAllCaretakersFromChildcareCenter(centerId);

        // Assert
        Assertions.assertTrue(caretakers.isEmpty());
    }

    @Test
    void getCaretakerFromChildcareCenter() {
        // Arrange
        int centerId = 1;
        int caretakerId = 4;

        // Act
        Repositories.getH2Repo().getCaretakerFromChildcareCenter(centerId, caretakerId);

        // Assert
        Assertions.assertNotNull(Repositories.getH2Repo().getCaretakerFromChildcareCenter(centerId, caretakerId));
    }


    @Test
    void deleteChildcareCenter() {
        // Arrange
        int id = 0;

        // Act
        Repositories.getH2Repo().deleteChildcareCenter(id);

        // Assert
        Assertions.assertNull(Repositories.getH2Repo().getChildcareCenter(id));
    }

    @Test
    void getEventTypeFromId(){
        // Arrange
        int eventType = 1;
        // Act
        String emergencyType = Repositories.getH2Repo().getEventTypeFromId(eventType);

        // Assert
        Assertions.assertEquals("Emergency", emergencyType);

    }

    @Test
    void getEventTypeFromIdException(){
        // Arrange
        int eventType = -1;

        //Assert
        Assertions.assertThrows(RepositoryException.class, () -> Repositories.getH2Repo().getEventTypeFromId(eventType));
    }

    @Test
    void getEmergencyTypeFromEventId(){
        // Arrange
        int eventId = 12;
        // Act
        EmergencyStatus EmergencyStatus = Repositories.getH2Repo().getEmergencyTypeFromEventId(eventId);

        // Assert
        Assertions.assertNotNull(EmergencyStatus);
    }

    @Test
    void getNoEmergencyTypeFromEventId(){
        // Arrange
        int eventId = -1;
        // Act
        EmergencyStatus EmergencyStatus = Repositories.getH2Repo().getEmergencyTypeFromEventId(eventId);

        //Assert
        Assertions.assertNull(EmergencyStatus);
    }

    @Test
    void getWordFromEventId(){ //has word
        // Arrange
        int eventId = 1;
        // Assert
        Assertions.assertNotNull(Repositories.getH2Repo().getWordFromEventId(eventId));
    }

    @Test
    void getWordFromEventIdException(){
        // Arrange
        int eventId = -1;
        // Assert
        Assertions.assertThrows(RepositoryException.class, () -> Repositories.getH2Repo().getWordFromEventId(eventId));
    }

    @Test
    void getEventTypes(){
        // Act
        List<String> eventTypes = Repositories.getH2Repo().getEventTypes();

        // Assert
        Assertions.assertEquals(5, eventTypes.size());
    }

    @Test
    void getParentsFromChild(){
        List<Parent> parents = Repositories.getH2Repo().getParentsFromChild(1);

        Assertions.assertEquals(1, parents.size());
    }
}
