package com.example.sto.exception;

import com.example.sto.domain.enums.RequestStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStatusTransitionException extends RuntimeException {



    public InvalidStatusTransitionException(RequestStatus fromStatus, RequestStatus toStatus) {
        super("Invalid status transition from " + fromStatus + " to " + toStatus);

    }

    public InvalidStatusTransitionException(String message) {
        super(message);

    }


}