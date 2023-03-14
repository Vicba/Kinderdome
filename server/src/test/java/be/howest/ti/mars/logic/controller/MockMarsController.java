package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.domain.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;


public class MockMarsController implements MarsController {
    private static final String SOME_NAME = "mats";
    private static final int SOME_PARENT_NAME = 2003;
    private static final String SOME_BIRTHDATE = "2002-06-06 00:00:00";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String SOME_CENTER = "childcareCenterName";
    private static final int SOME_LIMIT = 69;

    @Override
    public List<Child> getAllChildren() {
        List<Child>arraylist = new LinkedList<>();
        arraylist.add(new Child(0,"lala", LocalDateTime.parse(SOME_BIRTHDATE,formatter)));
        arraylist.add(new Child(1,"lala",LocalDateTime.parse(SOME_BIRTHDATE,formatter)));
        return arraylist;
    }
    @Override
    public Parent getParent(int parentID) {
        List<Child>childList = new LinkedList<>();
        childList.add(new Child(0,"lala",LocalDateTime.parse(SOME_BIRTHDATE,formatter)));
        childList.add(new Child(1,"lala",LocalDateTime.parse(SOME_BIRTHDATE,formatter)));
        return new Parent(parentID, SOME_NAME, SOME_PARENT_NAME, childList);}

    public Child getChild(int childId) {
        return new Child(childId, SOME_NAME, LocalDateTime.parse(SOME_BIRTHDATE,formatter));
    }

    @Override
    public void addChildToChildcareCenter(int childId, int childCareCenterId) {

    }

    @Override
    public void removeChildFromChildcareCenter(int childId, int childCareCenterId) {
    }


    @Override
    public ChildcareCenter getChildcareCenter(int centerId) {
        return new ChildcareCenter(centerId, SOME_CENTER,  SOME_LIMIT);
    }

    @Override
    public List<ChildcareCenter> getAllChildcareCenters() {
        return Collections.emptyList();
    }

    @Override
    public List<Child> getAllChildrenFromChildcareCenter(int centerId) {
        return Collections.emptyList();
    }

    @Override
    public List<Caretaker> getAllCaretakersFromChildcareCenter(int centerId) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getEventTypes() {
        return Collections.emptyList();
    }

    @Override
    public List<Event> getEventsFromChildAndCurrentDay(int childId) {
        return Collections.emptyList();
    }

    @Override
    public Caretaker getCaretakerFromChildcareCenter(int centerId, int caretakerId) {
        ChildcareCenter center = new ChildcareCenter("lala",20);
        return new Caretaker(SOME_NAME, center, 12.99);
    }

    @Override
    public List<Parent> getParentsFromChildCareCenter(int caretakerId) {
        List<Parent> parents = new LinkedList<>();
        parents.add(new Parent(1,"daan",2002,new ArrayList<>() ));
        parents.add(new Parent(2,"jona",2002,new ArrayList<>() ));
        return parents;
    }

}
