package be.howest.ti.mars.logic.controller;

import be.howest.ti.mars.logic.domain.*;
import be.howest.ti.mars.logic.domain.Child;
import be.howest.ti.mars.logic.domain.ChildcareCenter;
import be.howest.ti.mars.logic.domain.Parent;


import java.util.List;

public interface MarsController {

    List<Child> getAllChildren();
    Child getChild(int childId);
    void addChildToChildcareCenter(int childId, int childCareCenterId);
    void removeChildFromChildcareCenter(int childId, int childCareCenterId);
    Parent getParent(int parentId);
    ChildcareCenter getChildcareCenter(int centerId);
    List<ChildcareCenter> getAllChildcareCenters();
    List<Event> getEventsFromChildAndCurrentDay(int childId);
    List<Child> getAllChildrenFromChildcareCenter(int centerId);
    List<Caretaker> getAllCaretakersFromChildcareCenter(int centerId);
    List<String> getEventTypes();
    Caretaker getCaretakerFromChildcareCenter(int centerId, int caretakerId);

    List<Parent> getParentsFromChildCareCenter(int caretakerId);

}
