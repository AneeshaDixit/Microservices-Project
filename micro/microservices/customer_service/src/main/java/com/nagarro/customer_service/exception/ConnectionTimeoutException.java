package com.nagarro.customer_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConnectionTimeoutException extends RuntimeException {
    private final String message;
}