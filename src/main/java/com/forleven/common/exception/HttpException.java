package com.forleven.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HttpException extends RuntimeException {

    private final String[] args;

    public HttpException() {
        this.args = new String[]{};
    }

    public HttpException(String codeError) {
        super(codeError);
        this.args = new String[]{};
    }

    public HttpException(String codeError,
                  String... args) {
        super(codeError);
        this.args = args;
    }

}
