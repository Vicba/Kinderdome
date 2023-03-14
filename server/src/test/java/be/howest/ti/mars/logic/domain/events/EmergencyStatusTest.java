package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.exceptions.RepositoryException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmergencyStatusTest {

    @Test
    void fromString() {
        String test = "lala";
        assertThrows(RepositoryException.class, () -> EmergencyStatus.fromString(test));
    }
}