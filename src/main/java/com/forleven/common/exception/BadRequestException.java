package com.forleven.common.exception;

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