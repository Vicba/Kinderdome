package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.exceptions.RepositoryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTypeTest {

    @Test
    void fromString() {
        String wrongtype = "lala";
        assertThrows(RepositoryException.class,() -> EventType.fromString(wrongtype));

    }
}