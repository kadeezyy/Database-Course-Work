package com.example.musicplatform.exception;

import org.jooq.exception.DataAccessException;

public class NotUniqueObjectException extends DataAccessException {
    public NotUniqueObjectException(String message) {
        super(message);
    }
}
