package com.trixpert.beebbeeb.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("UNAUTHORIZED");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
