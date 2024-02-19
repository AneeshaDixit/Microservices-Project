package com.nagarro.customer_service.handler;

import com.nagarro.customer_service.exception.CustomException;
import com.nagarro.customer_service.customerdto.ExceptionMessageDto;
import com.nagarro.customer_service.exception.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
private static final String NO_RECORD_FOUND = null;
private static final String MISSING_REQUEST_PARAMETER_EXCEPTION = null;
//private static final String TIMESTAMP_PATTERN;
//
//private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
//private final String localDatTime=LocalDateTime.now().format(dateTimeFormatter);
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleApplicationException(
            CustomException exception) {

        ExceptionMessageDto response = new ExceptionMessageDto(
                exception.getHttpStatus().value(),
                exception.getMessage()
        );
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
//
    @ExceptionHandler(ConnectionTimeoutException.class)
    public ResponseEntity<?> handleConnectionTimeoutException(
            ConnectionTimeoutException exception) {

        ExceptionMessageDto response = new ExceptionMessageDto(
                HttpStatus.GATEWAY_TIMEOUT.value(),
                exception.getMessage()
                // LocalDateTime.now().format(dateTimeFormatter)
        );
        return new ResponseEntity<>(response, HttpStatus.GATEWAY_TIMEOUT);
    }
//
    @ExceptionHandler(FailedToFetchException.class)
    public ResponseEntity<?> handleFailedToFetchException() {

        ExceptionMessageDto response = new ExceptionMessageDto(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                PAGE_NOT_FOUND_LOG_CATEGORY
               // LocalDateTime.now().format(dateTimeFormatter)
        );
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> handleInvalidParameterException(
            InvalidParameterException exception) {

        ExceptionMessageDto response = new ExceptionMessageDto(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
              //  LocalDateTime.now().format(dateTimeFormatter)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoRecordFoundException.class)
    public ResponseEntity<?> handleNoRecordFoundException() {

        ExceptionMessageDto response = new ExceptionMessageDto(
                HttpStatus.NO_CONTENT.value(),
                NO_RECORD_FOUND
               // LocalDateTime.now().format(dateTimeFormatter)
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(UnsupportedValueException.class)
    public ResponseEntity<?> handleUnsupportedValueException(
            UnsupportedValueException exception) {

        ExceptionMessageDto response = new ExceptionMessageDto(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
             //   LocalDateTime.now().format(dateTimeFormatter)
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
//
//
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionMessageDto response = new ExceptionMessageDto(
                ex.getStatusCode().value(),
                ex.getParameterName() + MISSING_REQUEST_PARAMETER_EXCEPTION
               // LocalDateTime.now().format(dateTimeFormatter)
        );
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

//}
}