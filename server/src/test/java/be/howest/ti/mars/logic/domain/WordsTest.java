package be.howest.ti.mars.logic.domain;

import be.howest.ti.mars.logic.domain.events.FirstWord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WordsTest {

    Words word = new Words(1, "howest");

    @Test
    void getWordID() {
        Assertions.assertEquals(1, word.getWordID());
    }

    @Test
    void getWord() {
        Assertions.assertEquals("howest", word.getWord());
    }

    @Test
    void testEqualsAndHashCode(){
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime time2 = time.plusSeconds(4000);
        Child testchild = new Child(3,"victor", 2, time);
        Child testchild2 = new Child(4,"victor", 2, time);
        Words word = new Words(0,"2");
        Words word2 = new Words(0,"3");
        double lon = 51.1916602952191;
        double lat = 3.2137996967862925;
        Location loc = new Location((float) lon, (float) lat);
        Location loc2 = new Location((float) lon, (float) lat+1);
        FirstWord testWord = new FirstWord(0,loc,testchild,time,word);


        EqualsTester<FirstWord> equalsTester = EqualsTester.newInstance( testWord);
        equalsTester.assertEqual( testWord, testWord );
        equalsTester.assertNotEqual( new FirstWord(1,loc,testchild,time,word), testWord );
        equalsTester.assertNotEqual( new FirstWord(0,loc2,testchild,time,word), testWord );
        equalsTester.assertNotEqual( new FirstWord(1,loc,testchild2,time,word), testWord );
        equalsTester.assertNotEqual( new FirstWord(1,loc,testchild2,time2,word), testWord );
        equalsTester.assertNotEqual( new FirstWord(1,loc,testchild2,time,word2), testWord );

    }
}