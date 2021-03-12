package com.trixpert.beebbeeb.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestError {
    private int status;
    private String message;
}
