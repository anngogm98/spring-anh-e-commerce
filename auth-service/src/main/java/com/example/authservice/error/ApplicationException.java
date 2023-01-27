package com.example.authservice.error;


import com.example.authservice.error.dto.ErrorDto;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Generic exception that is intended to be the super class of all custom exceptions.
 */
@Data
@Accessors(chain = true)
public class ApplicationException extends RuntimeException {

    private transient ErrorDto errorDto;

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }


    public ApplicationException(String message, ErrorDto errorDto) {
        super(message);
        this.errorDto = errorDto;
    }

    public String getErrorCode() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return "Application Exception{} " + super.toString();
    }
}
