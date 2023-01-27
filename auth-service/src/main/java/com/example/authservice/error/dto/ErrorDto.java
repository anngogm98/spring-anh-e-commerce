package com.example.authservice.error.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.time.Instant;


/**
 * Contains the common details that should be returned in case of an error during a web service request.
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = ErrorDto.TYPE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ErrorDto.class, name = ErrorDto.SIMPLE_TYPE)
})
@Data
@NoArgsConstructor
@Slf4j
public class ErrorDto {
    public static final String SIMPLE_TYPE = "simple";
    public static final String TYPE = "type";
    public static final String HTTP_STATUS_STR = "httpStatus={}";

    private String requestUid = MDC.get("X-B3-TraceId");
    private long timestamp = Instant.now().toEpochMilli();
    private int httpStatus;
    private String message;
    private String errorCode = "GeneralException";

    public ErrorDto(HttpStatus httpStatus, Exception e) {
        setHttpStatus(httpStatus.value());
        setMessage(e.getMessage());
        log.info(HTTP_STATUS_STR, httpStatus.value());
    }

    public ErrorDto(HttpStatus httpStatus, String message) {
        setHttpStatus(httpStatus.value());
        setMessage(message);
        log.info(HTTP_STATUS_STR, httpStatus.value());
    }

    public ErrorDto(HttpStatus httpStatus, Exception e, String errorCode) {
        setHttpStatus(httpStatus.value());
        setMessage(e.getMessage());
        setErrorCode(errorCode);
        log.info(HTTP_STATUS_STR, httpStatus.value());
    }
}
