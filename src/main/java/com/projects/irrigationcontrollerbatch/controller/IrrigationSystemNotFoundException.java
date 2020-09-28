package com.projects.irrigationcontrollerbatch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ryan G. Marcoux
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class IrrigationSystemNotFoundException extends RuntimeException {
    public IrrigationSystemNotFoundException(String message) {
        super(message);
    }
}
