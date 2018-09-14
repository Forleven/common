package com.forleven.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class HttpException extends RuntimeException {

    private final String[] args;

    HttpException() {
        this.args = new String[]{};
    }

    HttpException(String codeError) {
        super(codeError);
        this.args = new String[]{};
    }

    HttpException(String codeError,
                  String... args) {
        super(codeError);
        this.args = args;
    }

}
