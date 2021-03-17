package com.trixpert.beebbeeb.exception.handler;

import com.trixpert.beebbeeb.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        logger.warn("Returning HTTP 400 Bad Request", e);
    }


    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e, WebRequest request) {
        HttpStatus status = BAD_REQUEST;
        return new ResponseEntity<>(new RestError(status.value(), e.getMessage()), status);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e, WebRequest request) {
        HttpStatus status = NOT_FOUND;
        return new ResponseEntity<>(new RestError(status.value(), e.getMessage()), status);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<Object> handleConflictException(ConflictException e, WebRequest request) {
        log.error("Data Conflict Error", e);
        HttpStatus status = CONFLICT;
        return new ResponseEntity<>(new RestError(status.value(), e.getMessage()), status);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        log.error("Data Conflict Error", e);
        HttpStatus status = CONFLICT;
        return new ResponseEntity<>(new RestError(status.value(), "Data integrity exception"), status);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException e, WebRequest request) {
        log.error("Validation Error: " + e.getMessage());
        HttpStatus status = UNPROCESSABLE_ENTITY;
        return new ResponseEntity<>(new RestError(status.value(), e.getMessage()), status);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e, WebRequest request) {
        HttpStatus status = UNAUTHORIZED;
        return new ResponseEntity<>(new RestError(status.value(), e.getMessage()), status);
    }


    @ExceptionHandler({InternalServerException.class})
    public ResponseEntity<Object> handleInternalError(InternalServerException e, WebRequest request) {
        if (e.isLogMessageInAdvice()) {
            log.error("Internal Server Error ", e);
        }
        HttpStatus status = INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new RestError(status.value(), e.getMessage()), status);
    }


}
