package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.Event;
import be.howest.ti.mars.logic.domain.Location;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class Eating extends Event {

    @JsonProperty("EventType")
    private static final EventType EVENT_TYPE = EventType.EATING;
    @JsonProperty("nutritionalValue")
    private final int nutritionalValue;

    public Eating(int eventId, Location location, Child child, int nutritionalValue, LocalDateTime localDateTime) {
        super(eventId, location, child,localDateTime, EVENT_TYPE);
        this.nutritionalValue = nutritionalValue;
    }

    public int getNutritionalValue() {
        return nutritionalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Eating eating = (Eating) o;
        return nutritionalValue == eating.nutritionalValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nutritionalValue);
    }
}
