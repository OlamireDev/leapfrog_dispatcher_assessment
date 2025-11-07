package com.olamiredev.leapfrog_dispatcher.data.exception;

public class BoxAlreadyExistsException extends RuntimeException {

    public BoxAlreadyExistsException(String txRef) {
        super(String.format("Box already exists for txRef: %s", txRef));
    }

}
