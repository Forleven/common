package com.forleven.common.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends HttpException {

    public BadRequestException() {
    }

    public BadRequestException(String codeError) {
        super(codeError);
    }

    public BadRequestException(String codeError,
                               String... args) {
        super(codeError, args);
    }
}