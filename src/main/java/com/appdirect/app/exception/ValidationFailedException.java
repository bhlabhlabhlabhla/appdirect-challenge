package com.appdirect.app.exception;

/**
 * Custom Validation Failed Exception which can be thrown and handled on any level in case of validation failure of any type.
 */
public class ValidationFailedException extends RuntimeException {

    public ValidationFailedException(String message) {
        super(message);
    }
}
