package com.example.sto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceRequestNotFoundException extends RuntimeException {

    public ServiceRequestNotFoundException(String message) {
        super(message);
    }

    public ServiceRequestNotFoundException(Long id) {
        super("Service request not found with id: " + id);
    }

    public ServiceRequestNotFoundException(String requestNumber, String field) {
        super("Service request not found with " + field + ": " + requestNumber);
    }
}
