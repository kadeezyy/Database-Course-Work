package com.example.musicplatform.exception;

import org.jooq.exception.DataAccessException;

public class NotFoundException extends DataAccessException {
    public NotFoundException(String message) {
        super(message);
    }
}
