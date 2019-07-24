package com.forleven.common.exception;

import java.util.Arrays;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
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

    @Override
    public String toString() {
        return "HttpException(message=" + getMessage() + ", args=" + Arrays.deepToString(this.getArgs()) + ")";
    }

}
