package com.forleven.common.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ForbiddenException extends HttpException {
    public ForbiddenException() {
    }

    public ForbiddenException(String codeError) {
        super(codeError);
    }

    public ForbiddenException(String codeError,
                             String... args) {
        super(codeError, args);
    }
}
