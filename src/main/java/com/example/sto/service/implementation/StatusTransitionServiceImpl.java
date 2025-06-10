package com.example.sto.service.implementation;

import com.example.sto.domain.enums.RequestStatus;
import com.example.sto.exception.InvalidStatusTransitionException;
import com.example.sto.service.StatusTransitionService;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Service
public class StatusTransitionServiceImpl implements StatusTransitionService {

    private final Map<RequestStatus, Set<RequestStatus>> transitions = Map.of(
            RequestStatus.CREATED, EnumSet.of(RequestStatus.ACCEPTED, RequestStatus.CANCELLED),
            RequestStatus.ACCEPTED, EnumSet.of(RequestStatus.IN_PROGRESS, RequestStatus.CANCELLED),
            RequestStatus.IN_PROGRESS, EnumSet.of(RequestStatus.REPAIR_IN_PROGRESS, RequestStatus.CANCELLED),
            RequestStatus.REPAIR_IN_PROGRESS, EnumSet.of(RequestStatus.COMPLETED, RequestStatus.CANCELLED),
            RequestStatus.COMPLETED, EnumSet.noneOf(RequestStatus.class),
            RequestStatus.CANCELLED, EnumSet.noneOf(RequestStatus.class)
    );

    @Override
    public Set<RequestStatus> getAvailableTransitions(RequestStatus currentStatus) {
        return transitions.getOrDefault(currentStatus, EnumSet.noneOf(RequestStatus.class));
    }

    @Override
    public boolean canTransition(RequestStatus from, RequestStatus to) {
        return getAvailableTransitions(from).contains(to);
    }

    @Override
    public void validateTransition(RequestStatus from, RequestStatus to) {
        if (!canTransition(from, to)) {
            throw new InvalidStatusTransitionException(
                    String.format("Invalid transition from %s to %s", from, to));
        }
    }
}
