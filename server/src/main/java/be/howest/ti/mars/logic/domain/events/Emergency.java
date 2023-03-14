package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.domain.Caretaker;
import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.Event;
import be.howest.ti.mars.logic.domain.Location;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class Emergency extends Event {
    @JsonProperty("Caretaker")
    Caretaker caretaker;
    @JsonProperty("EventType")
    private static final EventType EVENT_TYPE = EventType.EMERGENCY;

    @JsonProperty("EmergencyStatus")
    private EmergencyStatus emergencyStatus;

    protected Emergency(int eventId, Location location, Child child, LocalDateTime localDateTime, EmergencyStatus emergencyStatus){
        super(eventId,  location, child, localDateTime, EVENT_TYPE);
        this.caretaker = null;
        this.emergencyStatus = emergencyStatus;
    }

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(Caretaker caretaker){
        this.caretaker = caretaker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Emergency emergency = (Emergency) o;
        return Objects.equals(caretaker, emergency.caretaker) && emergencyStatus == emergency.emergencyStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), caretaker, emergencyStatus);
    }
}
