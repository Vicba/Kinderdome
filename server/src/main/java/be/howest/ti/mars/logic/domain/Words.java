package be.howest.ti.mars.logic.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Words {
    @JsonProperty("wordId")
    private int wordID;

    @JsonProperty("word")
    private String word;

    public Words(int wordID, String word) {
        this.wordID = wordID;
        this.word = word;
    }

    public int getWordID() {
        return wordID;
    }

    public String getWord() {
        return word;
    }
}
