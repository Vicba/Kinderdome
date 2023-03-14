package be.howest.ti.mars.logic.domain;

import be.howest.ti.mars.logic.domain.events.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Event implements Comparable<Event>{

    public static final int NO_ID_ASSIGNED = -1;

    @JsonProperty("eventID")
    private int eventID;
    @JsonProperty("description")
    private String description = "";
    @JsonProperty("dateTime")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    //IMPORTANT FOR OUTPUTTING DATE -> otherwise error in JSON output
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss") //IMPORTANT FOR OUTPUTTING DATE in right format
    private LocalDateTime time;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("bodyTemp")
    private float bodyTemp;
    @JsonProperty("heartRate")
    private int hearthRate;
    @JsonIgnore
    private final Child child;
    @JsonProperty("eventType")
    private EventType eventType;


    protected Event(int eventID, Location location, Child child, LocalDateTime localDateTime, EventType eventType) {
        this.eventID = eventID;
        this.location = location;
        this.time = localDateTime;
        this.child = child;
        this.eventType = eventType;
    }

    public void setBodyTemp(float bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public void setHearthRate(int hearthRate) {
        this.hearthRate = hearthRate;
    }

    public void setDescription(String description) { this.description = description; }

    public String getDescription() {
        return description;
    }

    public Child getChild() { return child;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public float getBodyTemp() {
        return bodyTemp;
    }

    public int getHearthRate() {
        return hearthRate;
    }

    public Location getLocation() {
        return location;
    }

    public String getEventType() {
        return eventType.getType();
    }

    public int getEventID() {
        return eventID;
    }

    @Override
    public int compareTo(Event o) {
        LocalDateTime thisDateTime = this.getTime();
        LocalDateTime otherDateTime = o.getTime();
        return thisDateTime.compareTo(otherDateTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventID == event.eventID && Float.compare(event.bodyTemp, bodyTemp) == 0 && hearthRate == event.hearthRate && Objects.equals(description, event.description) && time.equals(event.time) && location.equals(event.location) && child.equals(event.child) && eventType == event.eventType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID, description, time, location, bodyTemp, hearthRate, child, eventType);
    }
}
