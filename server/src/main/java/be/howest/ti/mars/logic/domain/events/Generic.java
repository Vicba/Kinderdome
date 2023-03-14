package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.Event;
import be.howest.ti.mars.logic.domain.Location;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Generic extends Event {
    @JsonProperty("EventType")
    private static final EventType EVENT_TYPE = EventType.GENERIC;
    public Generic(int eventID, Location location, Child child, LocalDateTime localDateTime) {
        super(eventID, location, child, localDateTime, EVENT_TYPE);
    }
}
