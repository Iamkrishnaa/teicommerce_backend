package com.teispace.teicommerce_backend.exceptions;


import com.teispace.teicommerce_backend.dtos.exceptions.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(
            UsernameNotFoundException ex,
            HttpServletRequest request
    ) {
        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.BAD_REQUEST,
                        List.of("Username not found"),
                        ex.getMessage(),
                        request.getServletPath()

                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException ex,
            HttpServletRequest request
    ) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.BAD_REQUEST,
                        details,
                        ex.getMessage(),
                        request.getServletPath()

                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpServletRequest request) {

        List<String> details = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

        details.add(builder.toString());

        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                details,
                "Unsupported Media Type",
                request.getServletPath()
        );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getParameterName() + " parameter is missing");

        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.BAD_REQUEST,
                        details,
                        "Missing Parameters",
                        request.getServletPath()
                );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidJwtException(InvalidJwtException ex, HttpServletRequest request) {
        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.UNAUTHORIZED,
                        List.of(ex.getMessage()),
                        ex.getMessage(),
                        request.getServletPath()

                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> details;
        details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.BAD_REQUEST,
                        details,
                        details.get(0),
                        request.getServletPath()
                );

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.BAD_REQUEST,
                        details,
                        "Invalid Login Details",
                        request.getServletPath()
                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.BAD_REQUEST,
                        details,
                        "Invalid JSON Request",
                        request.getServletPath()

                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.METHOD_NOT_ALLOWED,
                        details,
                        ex.getMessage(),
                        request.getServletPath()

                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.NOT_FOUND,
                        details,
                        ex.getMessage(),
                        request.getServletPath()

                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ExceptionResponse response =
                new ExceptionResponse(
                        new Date(),
                        HttpStatus.BAD_REQUEST,
                        details,
                        "Something went wrong",
                        request.getServletPath()
                );
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(
            Exception ex, HttpServletRequest request
    ) {

        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ex.printStackTrace();
        ExceptionResponse response = new ExceptionResponse(
                new Date(),
                HttpStatus.BAD_REQUEST,
                details,
                "Something Went Wrong",
                request.getServletPath()
        );
        return new ResponseEntity<>(response, response.getStatus());
    }
}