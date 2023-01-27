package com.example.authservice.error;


public class UserAlreadyExistsException extends ApplicationException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
