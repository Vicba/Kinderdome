package be.howest.ti.mars.logic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Parent {
    @JsonProperty("parentID")
    private final int parentID;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("birthyear")
    private final int year;
    @JsonIgnore
    private final List<Child> children;

    public Parent(int parentID, String name, int year, List<Child> children) {
        this.parentID = parentID;
        this.name = name;
        this.year = year;
        this.children = children;
    }

    public int getParentID() {
        return parentID;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public List<Child> getChildren() {
        return children;
    }
}
