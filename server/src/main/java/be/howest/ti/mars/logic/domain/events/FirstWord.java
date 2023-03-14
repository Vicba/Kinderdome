package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.domain.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class FirstWord extends Event {

    @JsonProperty("word")
    private Words words;
    @JsonProperty("EventType")
    private static final EventType EVENT_TYPE = EventType.SPOKEN_WORD;
    public FirstWord(int eventId, Location location, Child child, LocalDateTime localDateTime, Words words) {
        super(eventId, location, child, localDateTime, EVENT_TYPE);
        this.words = words;
    }

    public Words getWord() {
        return words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FirstWord firstWord = (FirstWord) o;
        return words.equals(firstWord.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), words);
    }
}
