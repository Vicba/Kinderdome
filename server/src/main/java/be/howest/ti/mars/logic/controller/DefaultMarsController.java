package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.data.Repositories;
import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.util.LocalDateTimeCalculator;
import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.ChildcareCenter;
import be.howest.ti.mars.logic.domain.Parent;

import java.time.LocalDateTime;
import java.util.*;

/**
 * DefaultMarsController is the default implementation for the MarsController interface.
 * The controller shouldn't even know that it is used in an API context.
 * This class and all other classes in the logic-package (or future sub-packages)
 * should use 100% plain old Java Objects (POJOs). The use of Json, JsonObject or
 * Strings that contain encoded/json data should be avoided here.
 * Keep libraries and frameworks out of the logic packages as much as possible.
 * Do not be afraid to create your own Java classes if needed.
 */
public class DefaultMarsController implements MarsController {

    private static final String MSG_PARENT_ID_UNKNOWN = "No parent with id: %d";
    private static final String MSG_CHILD_ID_UNKNOWN = "No child with id: %d";
    private static final String MSG_CENTER_ID_UNKNOWN = "No CENTER with id: %d";
    private static final String MSG_CENTER_NOT_FOUND = "No CENTER found.";
    private static final String MSG_CARETAKER_NOT_FOUND = "No caretaker found.";
    private static final int NO_CENTER_ASSIGNED = -1;


    @Override
    public List<Child> getAllChildren() {
        List<Child> children = Repositories.getH2Repo().getAllChildren();
        if (null == children) throw new NoSuchElementException("children not found");
        return children;
    }

    @Override
    public Child getChild(int childId) {
        Child child = Repositories.getH2Repo().getChild(childId);
        if (null == child) throw new NoSuchElementException(String.format(MSG_CHILD_ID_UNKNOWN, childId));
        return child;
    }

    @Override
    public void addChildToChildcareCenter(int childId, int childCareCenterId) {
        Child child = Repositories.getH2Repo().getChild(childId);
        ChildcareCenter center = Repositories.getH2Repo().getChildcareCenter(childCareCenterId);

        if (child == null) throw new NoSuchElementException(String.format(MSG_CHILD_ID_UNKNOWN, childId));
        if (center == null) throw new NoSuchElementException(String.format(MSG_CENTER_ID_UNKNOWN, childId));
        if (child.getChildcareCenterID() != NO_CENTER_ASSIGNED)
            throw new IllegalStateException("The child is already assigned to a childcare center.");

        Repositories.getH2Repo().setCenterId(childId, childCareCenterId);
    }

    @Override
    public void removeChildFromChildcareCenter(int childId, int childCareCenterId) {
        Child child = Repositories.getH2Repo().getChild(childId);

        if (child == null) throw new NoSuchElementException(String.format(MSG_CHILD_ID_UNKNOWN, childId));
        if (child.getChildcareCenterID() != childCareCenterId)
            throw new IllegalStateException(String.format("The child is not registered to this childcare center: %d", childCareCenterId));

        Repositories.getH2Repo().setCenterId(childId, NO_CENTER_ASSIGNED);
    }

    @Override
    public List<Event> getEventsFromChildAndCurrentDay(int childId) {
        List<LocalDateTime> localDateTimerange = LocalDateTimeCalculator.getMockDayRange();
        List<Event> events = Repositories.getH2Repo().getEventsFromPeriod(localDateTimerange.get(0), localDateTimerange.get(1), childId);
        if (events.isEmpty()) {
            throw new NoSuchElementException("No events found in range");
        }
        Collections.sort(events);
        return events;
    }

    @Override
    public List<ChildcareCenter> getAllChildcareCenters() {
        List<ChildcareCenter> centers = Repositories.getH2Repo().getAllChildcareCenters();
        if (centers.isEmpty()) {
            throw new NoSuchElementException(MSG_CENTER_NOT_FOUND);
        }
        return centers;
    }

    @Override
    public List<Child> getAllChildrenFromChildcareCenter(int centerId) {
        List<Child> children = Repositories.getH2Repo().getAllChildrenFromChildcareCenter(centerId);
        if (children.isEmpty()) {
            throw new NoSuchElementException(MSG_CENTER_NOT_FOUND);
        }
        return children;
    }

    @Override
    public List<Caretaker> getAllCaretakersFromChildcareCenter(int centerId) {
        List<Caretaker> caretakers = Repositories.getH2Repo().getAllCaretakersFromChildcareCenter(centerId);
        if (caretakers.isEmpty()) {
            throw new NoSuchElementException(MSG_CENTER_NOT_FOUND);
        }
        return caretakers;
    }

    @Override
    public List<String> getEventTypes() {
        List<String> eventTypes = Repositories.getH2Repo().getEventTypes();
        if (eventTypes.isEmpty()) {
            throw new NoSuchElementException("Event Types not found");
        }
        return eventTypes;
    }

    @Override
    public Parent getParent(int parentId) {
        Parent parent = Repositories.getH2Repo().getParent(parentId);
        if (null == parent)
            throw new NoSuchElementException(String.format(MSG_PARENT_ID_UNKNOWN, parentId));

        return parent;
    }

    @Override
    public ChildcareCenter getChildcareCenter(int centerId) {
        ChildcareCenter childcareCenter = Repositories.getH2Repo().getChildcareCenter(centerId);
        if (null == childcareCenter) {
            throw new NoSuchElementException(String.format(MSG_CENTER_ID_UNKNOWN, centerId));
        }
        return childcareCenter;
    }

    @Override
    public Caretaker getCaretakerFromChildcareCenter(int centerId, int caretakerId) {
        Caretaker caretaker = Repositories.getH2Repo().getCaretakerFromChildcareCenter(centerId, caretakerId);
        if (caretaker == null) {
            throw new NoSuchElementException(MSG_CARETAKER_NOT_FOUND);
        }
        return caretaker;
    }

    @Override
    public List<Parent> getParentsFromChildCareCenter(int childcareCenterId) {
        List<Parent> parents = new ArrayList<>();
        for (Child child : Repositories.getH2Repo().getAllChildrenFromChildcareCenter(childcareCenterId)){
            for (Parent parent : Repositories.getH2Repo().getParentsFromChild(child.getChildID())) {
                if (!parents.contains(parent)) {
                    parents.add(parent);
                }
            }
        }
        return parents;
    }

}