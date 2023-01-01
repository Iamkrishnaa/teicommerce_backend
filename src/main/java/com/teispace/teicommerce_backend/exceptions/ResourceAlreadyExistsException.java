package com.teispace.teicommerce_backend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyExistsException extends RuntimeException {
    private final String message;

    public ResourceAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}