package be.howest.ti.mars.logic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Objects;

public class Child implements Comparable<Child> {

    private static final int NO_ID_ASSIGNED = -1;
    //id
    @JsonProperty("childID") private int childID;
    @JsonProperty("childName") private String childName;
    @JsonProperty("birthdate")
    @JsonSerialize(using = LocalDateTimeSerializer.class) //IMPORTANT FOR OUTPUTTING DATE -> otherwise error in JSON output
    @JsonFormat(pattern = "dd-MM-yyyy") //IMPORTANT FOR OUTPUTTING DATE in right format
    private LocalDateTime birthdate;
    private int childcareCenterID;


    public Child(int childID, String childName, int childcareCenterID, LocalDateTime birthdate) {
        this.childID = childID;
        this.childName = childName;
        this.childcareCenterID = childcareCenterID;
        this.birthdate = birthdate;
    }


    public Child(String childName, int childcareCenterID, LocalDateTime birthdate){
        this(NO_ID_ASSIGNED, childName, childcareCenterID, birthdate);
    }

    public Child(int childID, String childName, LocalDateTime birthdate){
        this(childID, childName, 0, birthdate);
    }

    public int getChildID() {
        return childID;
    }

    public int getChildcareCenterID() {
        return childcareCenterID;
    }

    public String getChildName() {
        return childName;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setChildcareCenterID(Integer childcareCenterID) {
        this.childcareCenterID = childcareCenterID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return childID == child.childID && childcareCenterID == child.childcareCenterID && Objects.equals(childName, child.childName) && Objects.equals(birthdate, child.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(childID, childName, birthdate, childcareCenterID);
    }

    @Override
    public int compareTo(Child o) {
        String thisChildName = this.getChildName();
        String otherChildName = o.getChildName();
        return thisChildName.compareTo(otherChildName);
    }
}
