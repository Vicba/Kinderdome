package be.howest.ti.mars.logic.domain;


public class ChildcareCenter {
    public static final int NO_ID_ASSIGNED = -1;
    private final int childcareCenterID;
    private final String childcareCenterName;
    private final int childLimit;

    public ChildcareCenter(int childcareCentreID, String childcareCenterName, int childLimit) {
        this.childcareCenterID = childcareCentreID;
        this.childcareCenterName = childcareCenterName;
        this.childLimit = childLimit;
    }

    public ChildcareCenter(String childcareCenterName, int childLimit){
        this(NO_ID_ASSIGNED, childcareCenterName, childLimit);
    }

    public int getChildcareCenterID() {
        return childcareCenterID;
    }
    public String getChildcareCenterName() {
        return childcareCenterName;
    }

    public int getChildLimit() {
        return childLimit;
    }
}
