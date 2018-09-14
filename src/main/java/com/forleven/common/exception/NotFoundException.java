package com.forleven.common.exception;

public class NotFoundException extends HttpException {
    public NotFoundException() {
    }

    public NotFoundException(String codeError) {
        super(codeError);
    }

    public NotFoundException(String codeError,
                             String... args) {
        super(codeError, args);
    }
}
