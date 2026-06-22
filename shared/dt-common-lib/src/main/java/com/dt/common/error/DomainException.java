package com.dt.common.error;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
