package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FirstWordTest {

    LocalDateTime time;
    Location loc;

    Child child;

    FirstWord firstWord;

    Words word;

    @BeforeAll
    public void setup() {
        time = LocalDateTime.now();
        float lon = 51.1916602952191f;
        float lat = 3.2137996967862925f;
        loc = new Location(lon, lat);
        child = new Child(3, "victor", 1, time);
        word = new Words(1, "howest");
        firstWord = new FirstWord(1, loc, child, time, word);
    }

    @Test
    void getWord() {
        Assertions.assertNotNull(firstWord.getWord());
    }

    @Test
    void noEqualFirstWord() {
        FirstWord firstWord2 = new FirstWord(2, loc, child, LocalDateTime.now(), word);
        Assertions.assertNotEquals(firstWord, firstWord2);
    }

    @Test
    void testEqualsAndHashCode() {
        Words word2 = new Words(13, "tata");
        FirstWord tested2 = new FirstWord(12, loc, child, time, word2);
        EqualsTester<FirstWord> firstWordEqualsTester = EqualsTester.newInstance(firstWord);
        firstWordEqualsTester.assertEqual(firstWord, firstWord);
        firstWordEqualsTester.assertNotEqual(firstWord, tested2);
    }

}

