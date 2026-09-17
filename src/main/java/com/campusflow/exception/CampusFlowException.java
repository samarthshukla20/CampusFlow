package com.campusflow.exception;

public class CampusFlowException extends RuntimeException {

    public CampusFlowException(String message) {
        super(message);
    }

    public CampusFlowException(String message, Throwable cause) {
        super(message, cause);
    }
}