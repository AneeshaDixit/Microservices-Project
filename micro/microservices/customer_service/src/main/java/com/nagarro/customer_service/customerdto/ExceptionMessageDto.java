package com.nagarro.customer_service.customerdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionMessageDto {

    private final int code;
    private final String message;
}
