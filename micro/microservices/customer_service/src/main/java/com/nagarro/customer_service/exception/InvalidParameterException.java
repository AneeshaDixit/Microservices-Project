package com.nagarro.customer_service.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidParameterException extends RuntimeException {
    private final String message;
}