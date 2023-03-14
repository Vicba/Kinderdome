package be.howest.ti.mars.logic.domain;

public class Caretaker {

    public static final int NO_ID_ASSIGNED = -1;

    private final int caretakerID;
    private final String name;

    private final ChildcareCenter childcareCenter;

    private final double salary;

    public Caretaker(int caretakerID, String name, ChildcareCenter childcareCenter, double salary) {
        this.caretakerID = caretakerID;
        this.name = name;
        this.childcareCenter = childcareCenter;
        this.salary = salary;
    }

    public Caretaker(String name, ChildcareCenter childcareCenter, double salary) {
        this(NO_ID_ASSIGNED, name, childcareCenter, salary);
    }

    public int getCaretakerID() {
        return caretakerID;
    }

    public String getName() {
        return name;
    }

    public ChildcareCenter getChildcareCenter() {
        return childcareCenter;
    }

    public double getSalary() {
        return salary;
    }

}
