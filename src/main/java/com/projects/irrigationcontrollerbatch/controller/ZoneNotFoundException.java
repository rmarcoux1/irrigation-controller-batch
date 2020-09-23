package com.projects.irrigationcontrollerbatch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ryan G. Marcoux
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ZoneNotFoundException extends RuntimeException {
    public ZoneNotFoundException(String message) {
        super(message);
    }
}
