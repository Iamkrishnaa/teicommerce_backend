package com.teispace.teicommerce_backend.dtos.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ExceptionResponse {
    private final Date timeStamp;
    private final HttpStatus status;
    private final List<?> errors;
    private final String message;
    private String path;
}