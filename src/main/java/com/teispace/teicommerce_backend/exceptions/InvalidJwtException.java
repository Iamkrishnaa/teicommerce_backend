package com.teispace.teicommerce_backend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidJwtException extends RuntimeException {
    private String message;

    public InvalidJwtException(String message) {
        super(message);
        this.message = message;
    }
}
