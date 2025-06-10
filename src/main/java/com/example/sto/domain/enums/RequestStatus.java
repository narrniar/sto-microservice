package com.example.sto.domain.enums;

public enum RequestStatus {
    CREATED,
    ACCEPTED,
    IN_PROGRESS,
    REPAIR_IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    public boolean canTransitionTo(RequestStatus targetStatus) {
        return switch (this) {
            case CREATED -> targetStatus == ACCEPTED || targetStatus == CANCELLED;
            case ACCEPTED -> targetStatus == IN_PROGRESS || targetStatus == CANCELLED;
            case IN_PROGRESS -> targetStatus == REPAIR_IN_PROGRESS || targetStatus == CANCELLED;
            case REPAIR_IN_PROGRESS -> targetStatus == COMPLETED || targetStatus == CANCELLED;
            case COMPLETED, CANCELLED -> false;
        };
    }
}