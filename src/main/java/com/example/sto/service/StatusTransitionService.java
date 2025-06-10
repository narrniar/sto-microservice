package com.example.sto.service;

import com.example.sto.domain.enums.RequestStatus;

import java.util.Set;

public interface StatusTransitionService {
    Set<RequestStatus> getAvailableTransitions(RequestStatus currentStatus);
    boolean canTransition(RequestStatus from, RequestStatus to);
    void validateTransition(RequestStatus from, RequestStatus to);
}
