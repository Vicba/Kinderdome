package be.howest.ti.mars.web;

import be.howest.ti.mars.logic.controller.MarsController;
import be.howest.ti.mars.logic.domain.*;

import java.util.List;
import java.util.Set;

public abstract class TestService implements MarsController {
    @Override
    public List<Child> getAllChildren() {
        return null;
    }

    @Override
    public Child getChild(int childId) {
        return null;
    }

    @Override
    public void addChildToChildcareCenter(int childId, int childCareCenterId) {

    }

    @Override
    public void removeChildFromChildcareCenter(int childId, int childCareCenterId) {

    }

    @Override
    public Parent getParent(int parentId) {
        return null;
    }

    @Override
    public ChildcareCenter getChildcareCenter(int centerId) {
        return null;
    }

    @Override
    public List<ChildcareCenter> getAllChildcareCenters() {
        return null;
    }

    @Override
    public List<Event> getEventsFromChildAndCurrentDay(int childId) {
        return null;
    }

    @Override
    public List<Child> getAllChildrenFromChildcareCenter(int centerId) {
        return null;
    }

    @Override
    public List<Caretaker> getAllCaretakersFromChildcareCenter(int centerId) {
        return null;
    }

    @Override
    public List<String> getEventTypes() {
        return null;
    }

    @Override
    public Caretaker getCaretakerFromChildcareCenter(int centerId, int caretakerId) {
        return null;
    }
}
