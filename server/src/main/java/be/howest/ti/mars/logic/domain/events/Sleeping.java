package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.Event;
import be.howest.ti.mars.logic.domain.Location;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class Sleeping extends Event {
    @JsonProperty("EventType")

    private static final EventType EVENT_TYPE = EventType.SLEEPING;
    @JsonProperty("DepthScore")
    private final int depthScore;
    public Sleeping(int eventId, Location location, Child child, int depthScore, LocalDateTime localDateTime) {
        super(eventId, location, child,localDateTime, EVENT_TYPE);
        this.depthScore = depthScore;
    }

    public int getDepthScore() {
        return depthScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sleeping sleeping = (Sleeping) o;
        return depthScore == sleeping.depthScore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), depthScore);
    }
}
