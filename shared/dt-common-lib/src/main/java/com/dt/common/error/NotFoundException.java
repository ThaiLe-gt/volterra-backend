package com.dt.common.error;

public class NotFoundException extends DomainException {
    public NotFoundException(String message) {
        super(message);
    }
}
