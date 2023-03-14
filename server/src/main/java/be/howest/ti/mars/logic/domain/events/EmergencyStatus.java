package be.howest.ti.mars.logic.domain.events;

import be.howest.ti.mars.logic.exceptions.RepositoryException;

import java.util.Objects;

public enum EmergencyStatus {

    ONGOING("ONGOING"),
    RESOLVED("RESOLVED");

    private final String type;

    EmergencyStatus(String type) {
        this.type = type;
    }

    public static EmergencyStatus fromString(String type) {
        for(EmergencyStatus emergencyStatus : EmergencyStatus.values()){
            if (Objects.equals(emergencyStatus.type, type)) {
                return emergencyStatus;
            }
        }
        throw new RepositoryException("Cannot find EmergencyStatus");
    }
}
