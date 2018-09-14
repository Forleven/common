package com.forleven.common.exception;

public class ConflictException extends HttpException {

    public ConflictException() {
    }

    public ConflictException(String codeError) {
        super(codeError);
    }

    public ConflictException(String codeError,
                             String... args) {
        super(codeError, args);
    }
}
