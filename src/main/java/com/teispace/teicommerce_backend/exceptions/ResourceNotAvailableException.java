package com.teispace.teicommerce_backend.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotAvailableException extends RuntimeException {
    private final String message;

    public ResourceNotAvailableException(String message) {
        super(message);
        this.message = message;
    }
}