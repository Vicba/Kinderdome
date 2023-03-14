package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.exceptions.RepositoryException;

import java.util.Objects;

public enum EventType {

    EATING("Eating"),
    SPOKEN_WORD("SpokenWord"),
    GENERIC("Generic"),

    SLEEPING("Sleeping"),
    EMERGENCY("Emergency");
    //

    private final String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static EventType fromString(String type) {
        for(EventType eventType: EventType.values()){
            if (Objects.equals(eventType.type, type)) {
                return eventType;
            }
        }
        throw new RepositoryException("Cannot find Event-type");
    }
}
