package com.forleven.common.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
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
